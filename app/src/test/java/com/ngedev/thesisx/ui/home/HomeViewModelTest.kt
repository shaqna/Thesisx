package com.ngedev.thesisx.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.home.HomeUseCase
import com.ngedev.thesisx.utils.Category
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
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: HomeUseCase

    @Before
    fun setupViewModel() {
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun `verify successful get all thesis book`() {
        val categoryAll = Category.ALL
        val flowData = flowOf(Resource.Success(DataDummy.generateDummyListThesisResponse()))
        val expectedResponse = DataDummy.generateDummyListThesisResponse()

        Mockito.`when`(useCase.getAllThesis()).thenReturn(flowData)
        val actualResponse = viewModel.getThesisByCategory(categoryAll).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getAllThesis()
    }

    @Test
    fun `verify successful get all thesis book by another category`() {
        val categoryControl = Category.CONTROL
        val flowData = flowOf(Resource.Success(DataDummy.generateDummyListThesisResponse()))
        val expectedResponse = DataDummy.generateDummyListThesisResponse()

        Mockito
            .`when`(useCase.getThesisByCategory(categoryControl))
            .thenReturn(flowData)

        val actualResponse = viewModel
            .getThesisByCategory(categoryControl)
            .getOrAwaitValue()
            .data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getThesisByCategory(categoryControl)
    }
}