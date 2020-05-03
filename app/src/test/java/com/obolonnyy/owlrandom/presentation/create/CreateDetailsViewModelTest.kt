package com.obolonnyy.owlrandom.presentation.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MockMainRepo
import com.obolonnyy.owlrandom.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CreateDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repo: MainRepository
    private lateinit var viewModel: CreateDetailsViewModel

    @Before
    fun before() {
        repo = MockMainRepo()
        viewModel = CreateDetailsViewModel(1, repo)

    }

    @Test
    fun onTitleChanged() {
        val newTitle = "new title"
        runBlocking { viewModel.viewState.getOrAwaitValue() }
        viewModel.onTitleChanged(newTitle)
        val state = viewModel.viewState.getOrAwaitValue()
        print (state.toString())
        assert(state is CreateDetailsViewState.Loaded)
        assert((state as CreateDetailsViewState.Loaded).title == newTitle)
    }

    @Test
    fun onItemChanged() {
        (0..100).toList().forEach {
            onTitleChanged()
        }
    }

    @Test
    fun delete() {
    }

    @Test
    fun onCleared() {
    }
}