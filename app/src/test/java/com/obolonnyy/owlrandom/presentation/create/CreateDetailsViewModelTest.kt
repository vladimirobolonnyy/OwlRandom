package com.obolonnyy.owlrandom.presentation.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MockMainRepo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class CreateDetailsViewModelTest {

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
    fun onItemChanged() {

    }

    @Test
    fun delete() {
    }

    @Test
    fun onCleared() {
    }
}