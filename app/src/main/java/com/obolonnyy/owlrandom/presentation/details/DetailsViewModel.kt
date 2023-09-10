package com.obolonnyy.owlrandom.presentation.details

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MainRepositoryImpl
import com.obolonnyy.owlrandom.presentation.details.random.RandomTypes
import com.obolonnyy.owlrandom.presentation.details.random.Randomizer
import com.obolonnyy.owlrandom.utils.warning
import com.orra.core_presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val groupId: Long,
    private val random: Randomizer = Randomizer(),
    private val repo: MainRepository = MainRepositoryImpl()
) : BaseViewModel<DetailsViewEvent>() {

    private val state: DetailsViewState? get() = _viewState.value
    private val _viewState = MutableLiveData<DetailsViewState?>(null)
    val viewState: LiveData<DetailsViewState?> = _viewState

    private val colorList: List<Color> =
        listOf(
            Color(0xFFECD078),
            Color(0xFFD95B43),
            Color(0xFFBFCFFF),
            Color(0xFF5888FC),
            Color(0xFFFF5370),
            Color(0xFFC02942),
            Color(0xCC542437),
        )

    init {
        loadData()
    }

    fun onRandomTypePicked(type: RandomTypes) {
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        when (type) {
            RandomTypes.RANDOMIZE_ALL -> {
                val newItems = random.shuffle(adapterItems)
                DetailsViewState(state.group, newItems).set()
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

    private fun pick(colorNumber: Int) {
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        val newItems = random.shuffleFirstN(colorNumber, adapterItems)
        var newState = DetailsViewState(state.group, newItems)
        for (i in 0 until colorNumber) {
            newState = newState.changeColor(i, colorList[i])
        }
        newState.set()
    }

    private fun divide(colorNumber: Int) {
        val state = state ?: return
        val adapterItems = state.group.items.toAdapterItems()
        val listNewItems = random.divideByNTeams(colorNumber, adapterItems)
        val newItems = mutableListOf<DetailsAdapterItem>()
        listNewItems.forEachIndexed { i, t ->
            newItems.addAll(t.map { it.copy(bgColor = colorList[i]) })
        }
        DetailsViewState(state.group, newItems).set()
    }

    private fun loadData() = launchIO {
        repo.getFlowGroup(groupId)
            .distinctUntilChanged()
            .catch { e ->
                warning("DetailsViewModel got error $e")
                showErrorMessage()
            }
            .collect { group ->
                if (group != null) {
                    withContext(Dispatchers.Main) {
                        DetailsViewState(group, group.items.toAdapterItems()).set()
                    }
                }
            }
    }

    private fun List<String>.toAdapterItems(): MutableList<DetailsAdapterItem> {
        return this.mapIndexed { i, s -> DetailsAdapterItem(i, s, Color.Transparent) }
            .toMutableList()
    }

    private fun DetailsViewState.set() {
        _viewState.value = this
    }

    fun onRollClicked() {
        DetailsViewEvent.ShowPickDialog(items = RandomTypes.values().toList()).post()
    }

    fun onEditClicked() {
        _viewEvents.postValue(DetailsViewEvent.NavigateToEdit(groupId))
    }

}
