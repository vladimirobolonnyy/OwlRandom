package com.obolonnyy.owlrandom.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.R
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.utils.warning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val groupId: Long,
    private val random: Randomizer = Randomizer(),
    private val repo: MainRepository = MainRepositoryImpl()
) : com.orra.core_presentation.base.BaseViewModel<DetailsViewEvent>() {

    private val state: DetailsViewState.Loaded?
        get() = _viewState.value as? DetailsViewState.Loaded
    private val _viewState = MutableLiveData<DetailsViewState>(DetailsViewState.Empty)
    val viewState: LiveData<DetailsViewState> = _viewState

    init {
        loadData()
    }

    fun onRandomTypePicked(index: Int) {
        val type = RandomTypes.get(index)
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        when (type) {
            RandomTypes.RANDOMIZE_ALL -> {
                val newItems = random.shuffle(adapterItems)
                DetailsViewState.Loaded(state.group, newItems).set()
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
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        val newItems = random.shuffleFirstN(colorNumber, adapterItems)
        DetailsViewState.Loaded(state.group, newItems).apply {
            for (i in 0 until colorNumber) {
                changeColor(i, colorList[i])
            }
        }.set()
    }

    private fun divide(colorNumber: Int) {
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        val listNewItems = random.divideByNTeams(colorNumber, adapterItems)
        val newItems = mutableListOf<DetailsAdapterItem>()
        listNewItems.forEachIndexed { i, t ->
            newItems.addAll(t.map { it.copy(bgColor = colorList[i]) })
        }
        DetailsViewState.Loaded(state.group, newItems).set()
    }

    private fun loadData() = launchIO {
        repo.getFlowGroup(groupId)
            .distinctUntilChanged()
            .catch { e ->
                warning("DetailsViewModel got error $e")
                _viewEvents.postValue(DetailsViewEvent.NavigateBack)
            }
            .collect { group ->
                if (group == null) {
                    _viewEvents.postValue(DetailsViewEvent.NavigateBack)
                } else {
                    val adapterItems = group.items.toAdapterItems()
                    withContext(Dispatchers.Main) {
                        val newState = DetailsViewState.Loaded(group, adapterItems)
                        mergeStates(state, newState).set()
                    }
                }
            }
    }

    private fun mergeStates(
        oldState: DetailsViewState?,
        newState: DetailsViewState.Loaded
    ): DetailsViewState {
        if (oldState !is DetailsViewState.Loaded) return newState
        if (oldState.adapterItems.size == newState.adapterItems.size) {
            if (oldState.adapterItems.map { it.text } == newState.adapterItems.map { it.text }) {
                return oldState
            }
        }
        return newState
    }

    private fun List<String>.toAdapterItems(): MutableList<DetailsAdapterItem> {
        return this
            .mapIndexed { i, s -> DetailsAdapterItem(i, s) }
            .toMutableList()
    }

    private fun DetailsViewState.set() {
        _viewState.value = this
    }

    fun onRollClicked() {
        _viewEvents.postValue(
            DetailsViewEvent.ShowPickDialog(items = RandomTypes.values().map { it.text })
        )
    }

    fun onEditClicked() {
        _viewEvents.postValue(DetailsViewEvent.NavigateToEdit(groupId))
    }
}
