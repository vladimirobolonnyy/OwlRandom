package com.obolonnyy.owlrandom.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.base.BaseViewModel
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.warning

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
            RandomTypes.ONE -> {
                val newItems = random.shuffleFirstN(1, adapterItems)
                DetailsViewState.Loaded(state.group, newItems).apply {
                    changeColor(0, R.color.color2)
                    post()
                }
            }
            RandomTypes.TWO -> {
                val newItems = random.shuffleFirstN(2, adapterItems)
                DetailsViewState.Loaded(state.group, newItems).apply {
                    changeColor(0, R.color.color2)
                    changeColor(1, R.color.color1)
                    post()
                }
            }
            RandomTypes.THREE -> {
                val newItems = random.shuffleFirstN(3, adapterItems)
                DetailsViewState.Loaded(state.group, newItems).apply {
                    changeColor(0, R.color.color2)
                    changeColor(1, R.color.color1)
                    changeColor(2, R.color.color3)
                    post()
                }
            }
            RandomTypes.DIVIDE_TWO -> TODO()
            RandomTypes.DIVIDE_THEE -> TODO()
        }
    }

    private fun loadData() {
        launchIO {
            DetailsViewState.Empty.post()
            repo.getGroup(groupId)?.let { group ->
                val adapterItems = group.items.toAdapterItems()
                DetailsViewState.Loaded(group, adapterItems).post()
            } ?: DetailsViewState.Empty.post()
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
