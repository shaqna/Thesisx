package com.ngedev.thesisx.ui.cupboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.CupBoard
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase
import com.ngedev.thesisx.domain.usecase.cupboard.CupBoardUseCase
import com.ngedev.thesisx.ui.auth.register.RegisterViewModel
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
class CupBoardViewModelTest {

    private lateinit var viewModel: CupBoardViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: CupBoardUseCase


    @Before
    fun setUpViewModel() {
        viewModel = CupBoardViewModel(useCase)

    }

    @Test
    fun `verify get key success`(){
        val expectedResponse = DataDummy.generateDummyCupBoardResponse()
        val flowData: Flow<Resource<CupBoard>> = flowOf(Resource.Success(expectedResponse))

        Mockito.`when`(useCase.getNewLockerKey()).thenReturn(flowData)

        val actualResponse = viewModel.getKey().getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getNewLockerKey()
    }
}