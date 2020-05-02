package com.obolonnyy.owlrandom.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.MyResult
import com.obolonnyy.owlrandom.utils.warning
import kotlinx.coroutines.flow.collect

class DetailsViewModel(
    private val groupId: Long,
    private val random: Randomizer = Randomizer(),
    private val repo: MainRepositoryImpl = MainRepositoryImpl()
) : BaseViewModel() {

    private var state: DetailsViewState = DetailsViewState.Empty
    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState> = _viewState

    init {
        loadData()
    }

    fun onRandomTypePicked(index: Int, text: CharSequence) {
        val type = RandomTypes.get(index)
        if (type == null) {
            warning("unknown type. index = ${index}, text:= $text")
            return
        }
        if (state !is DetailsViewState.Loaded) {
            warning("illegal state, $state")
            return
        }
        val state = (state as DetailsViewState.Loaded)
        val adapterItems = state.group.items.toAdapterItems()
        when (type) {
            RandomTypes.RANDOMIZE_ALL -> {
                val newItems = random.shuffle(adapterItems)
                DetailsViewState.Loaded(state.group, newItems).post()
            }
            RandomTypes.ONE -> pick(1)
            RandomTypes.TWO -> pick(2)
            RandomTypes.THREE -> pick(3)
            RandomTypes.FOUR -> pick(4)
            RandomTypes.FIVE -> pick(5)
            RandomTypes.DIVIDE_TWO -> divide(2)
            RandomTypes.DIVIDE_THEE -> divide(3)
            RandomTypes.DIVIDE_FOUR -> divide(4)
            RandomTypes.DIVIDE_FIVE -> divide(5)
        }
    }

    private val colorList: List<Int> =
        listOf(R.color.color2, R.color.color1, R.color.color3, R.color.blue, R.color.color5)

    private fun pick(colorNumber: Int) {
        if (state !is DetailsViewState.Loaded) {
            warning("illegal state, $state")
            return
        }
        val state = (state as DetailsViewState.Loaded)
        val adapterItems = state.group.items.toAdapterItems()
        val newItems = random.shuffleFirstN(colorNumber, adapterItems)
        DetailsViewState.Loaded(state.group, newItems).apply {
            for (i in 0 until colorNumber) {
                changeColor(i, colorList[i])
            }
            post()
        }
    }

    private fun divide(colorNumber: Int) {
        if (state !is DetailsViewState.Loaded) {
            warning("illegal state, $state")
            return
        }
        val state = (state as DetailsViewState.Loaded)
        val adapterItems = state.group.items.toAdapterItems()
        val listNewItems = random.divideByNTeams(colorNumber, adapterItems)
        val newItems = mutableListOf<DetailsAdapterItem>()
        listNewItems.forEachIndexed { i, t ->
            newItems.addAll(t.map { it.copy(bgColor = colorList[i]) })
        }
        DetailsViewState.Loaded(state.group, newItems).post()
    }

    private fun loadData() {
        launchIO {
            DetailsViewState.Empty.post()
            when (val result = repo.getFlowGroup(groupId)) {
                is MyResult.Success -> {
                    result.data.collect { group ->
                        val adapterItems = group.items.toAdapterItems()
                        DetailsViewState.Loaded(group, adapterItems).post()
                    }
                }
                is MyResult.Error -> DetailsViewState.Error.post()
            }
        }
    }

    private fun List<String>.toAdapterItems(): MutableList<DetailsAdapterItem> {
        return this
            .mapIndexed { i, s -> DetailsAdapterItem(i, s) }
            .toMutableList()
    }

    private fun DetailsViewState.post() {
        state = this
        _viewState.postValue(state)
    }
}
