package com.ngedev.thesisx.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.search.SearchUseCase
import com.ngedev.thesisx.utils.CoroutinesTestRule
import com.ngedev.thesisx.utils.DataDummy
import com.ngedev.thesisx.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: SearchUseCase

    @Before
    fun setupViewModel() {
        viewModel = SearchViewModel(useCase)
    }


    @Test
    fun `verify successful search thesis book by title`() {

        val query = "test"
        val flowData = flowOf(Resource.Success(DataDummy.generateDummyListThesisResponse()))
        val expectedResponse = DataDummy.generateDummyListThesisResponse()

        Mockito.`when`(useCase.searchThesis(query)).thenReturn(flowData)
        val actualResponse = viewModel.searchThesisByTitle(query).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)


        Mockito.verify(useCase).searchThesis(query)
    }
}