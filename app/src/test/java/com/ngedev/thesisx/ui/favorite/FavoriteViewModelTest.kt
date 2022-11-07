package com.ngedev.thesisx.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.favorite.BookmarkUseCase
import com.ngedev.thesisx.utils.CoroutinesTestRule
import com.ngedev.thesisx.utils.DataDummy
import com.ngedev.thesisx.utils.getOrAwaitValue
import io.mockk.impl.annotations.MockK
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
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()


    @Mock
    private lateinit var useCase: BookmarkUseCase

    @Before
    fun setupViewModel(){
        viewModel = FavoriteViewModel(useCase)
    }

    @Test
    fun `verify get all favorite book success`() {
        val fakeListId = listOf<String>("1", "2")
        val expectedResponse = DataDummy.generateDummyListThesisResponse()
        val flowData = flowOf(Resource.Success(expectedResponse))

        Mockito.`when`(useCase.getAllBookmarked(fakeListId)).thenReturn(flowData)

        val actualResponse = viewModel.getAllBookmarkedThesis(fakeListId).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getAllBookmarked(fakeListId)
    }
}