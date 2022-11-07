package com.ngedev.thesisx.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase
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
class RegisterViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()


    private var email = "fake@email.com"
    private var password = "fake"

    private lateinit var viewModel: RegisterViewModel

    @Mock
    private lateinit var useCase: AuthUseCase

    @Mock
    private lateinit var fakeUser: User

    @Before
    fun setUpViewModel() {
        viewModel = RegisterViewModel(useCase)
    }

    @Test
    fun `verify register success`() {
        val flowData: Flow<Resource<Unit>> = flowOf(Resource.Success(null))
        val expectedResponse = DataDummy.generateDummyRegisterResponse()

        Mockito.`when`(useCase.signUp(fakeUser, email, password)).thenReturn(flowData)
        val actualResponse = viewModel.signUp(email, password, fakeUser).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).signUp(fakeUser, email, password)
    }
}