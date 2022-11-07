package com.ngedev.thesisx.ui.detail.thesis_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.detail.thesis_detail.DetailUseCase
import com.ngedev.thesisx.utils.CoroutinesTestRule
import com.ngedev.thesisx.utils.DataDummy
import com.ngedev.thesisx.utils.getOrAwaitValue
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: DetailUseCase


    @Before
    fun setupViewModel() {
        viewModel = DetailViewModel(useCase)
    }


    @Test
    fun `verify get thesis by ID success`() {
        val fakeId = "1"
        val expectedResponse = DataDummy.generateDummyThesisResponse()
        val flowData: Flow<Resource<Thesis>> = flowOf(Resource.Success(expectedResponse))

        Mockito.`when`(useCase.getThesisById(fakeId)).thenReturn(flowData)

        val actualResponse = viewModel.getThesisById(fakeId).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getThesisById(fakeId)

    }

    @Test
    fun `verify add thesis to favorite list success`() {
        val fakeId = "1"
        val expectedResponse: Unit? = null
        val flowData = flowOf(Resource.Success(expectedResponse))

        Mockito.`when`(useCase.addFavoriteThesis(fakeId)).thenReturn(flowData)

        val actualResponse = viewModel.addFavoriteThesis(fakeId).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).addFavoriteThesis(fakeId)
    }

    @Test
    fun `verify delete thesis from favorite list success`() {
        val fakeId = "1"
        val expectedResponse: Unit? = null
        val flowData = flowOf(Resource.Success(expectedResponse))


        Mockito.`when`(useCase.deleteFavoriteThesis(fakeId)).thenReturn(flowData)

        val actualResponse = viewModel.deleteFavoriteThesis(fakeId).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).deleteFavoriteThesis(fakeId)
    }


}