package com.ngedev.thesisx.ui.favorite.loan

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.loan.BorrowUseCase
import com.ngedev.thesisx.ui.loan.LoanViewModel
import com.ngedev.thesisx.utils.CoroutinesTestRule
import com.ngedev.thesisx.utils.DataDummy
import com.ngedev.thesisx.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
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
class LoanViewModelTest {

    private lateinit var viewModel: LoanViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: BorrowUseCase

    @Before
    fun setupViewModel() {
        viewModel = LoanViewModel(useCase)
    }

    @Test
    fun `verify successful get all user loan`() {
        val fakeIds = listOf("1", "2")
        val flowData = flowOf(Resource.Success(DataDummy.generateDummyLoanListResponse()))
        val expectedResponse = DataDummy.generateDummyLoanListResponse()

        Mockito.`when`(useCase.getAllBorrowing(fakeIds)).thenReturn(flowData)
        val actualResponse = viewModel.getAllUserThesisBorrow(fakeIds).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getAllBorrowing(fakeIds)
    }

    @Test
    fun `verify successful delete loan from user loan list`() {
        val fakeId = "1"
        val flowData = flowOf(Resource.Success(null))
        val expectedResponse: Unit? = null

        Mockito.`when`(useCase.deleteLoan(fakeId)).thenReturn(flowData)
        val actualResponse = viewModel.deleteLoan(fakeId).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).deleteLoan(fakeId)
    }

}