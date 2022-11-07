package com.ngedev.thesisx.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase
import com.ngedev.thesisx.utils.CoroutinesTestRule
import com.ngedev.thesisx.utils.DataDummy
import com.ngedev.thesisx.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: AuthUseCase


    private var email = "fake@email.com"
    private var password = "fake"

    @Before
    fun setUpViewModel() {
        viewModel = LoginViewModel(useCase)
    }

    @Test
    fun `verify login success`() {
        val flowData: Flow<Resource<Unit>> = flowOf(Resource.Success(null))
        val expectedResponse = DataDummy.generateDummyLoginResponse()

        Mockito.`when`(useCase.signIn(email, password)).thenReturn(flowData)

        val actualResponse = viewModel.signIn(email, password).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).signIn(email, password)
    }

    @Test
    fun `verify reset password success`(){
        val flowData: Flow<Resource<Unit>> = flowOf(Resource.Success(null))
        val expectedResponse = DataDummy.generateDummyResetPasswordResponse()

        Mockito.`when`(useCase.resetPassword(email)).thenReturn(flowData)
        val actualResponse = viewModel.resetPassword(email).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).resetPassword(email)
    }
}