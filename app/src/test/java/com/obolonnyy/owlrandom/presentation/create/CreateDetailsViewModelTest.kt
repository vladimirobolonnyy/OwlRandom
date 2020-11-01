package com.obolonnyy.owlrandom.presentation.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obolonnyy.owlrandom.database.MainRepository
import com.obolonnyy.owlrandom.database.MockMainRepo
import com.obolonnyy.owlrandom.utils.observeOnce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class CreateDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var repo: MainRepository
    private lateinit var viewModel: EditDetailsViewModel

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        repo = MockMainRepo()
        viewModel = EditDetailsViewModel(1, repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetData() {
        runBlocking {
            // viewModel has empty value
            // we need to wait new one
            delay(100)
            viewModel.viewState.observeOnce {
                assert(it.groupId == 1L)
            }
        }
    }
}

