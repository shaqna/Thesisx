package com.ngedev.thesisx.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.settings.SettingsUseCase
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
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var useCase: SettingsUseCase


    @Before
    fun setupViewModel() {
        viewModel = SettingsViewModel(useCase)
    }

    @Test
    fun `verify successful get username and email`() {
        val flowData = flowOf(Resource.Success(DataDummy.generateDummyUser()))
        val expectedResponse = DataDummy.generateDummyUser()

        Mockito.`when`(useCase.getCurrentUser()).thenReturn(flowData)
        val actualResponse = viewModel.getUsernameAndEmail().getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).getCurrentUser()
    }

    @Test
    fun `verify successful change username`() {
        val flowData = flowOf(Resource.Success(null))
        val expectedResponse: Unit? = null
        val newUsername = "Shaq"

        Mockito.`when`(useCase.changeUsername(newUsername)).thenReturn(flowData)
        val actualResponse = viewModel.changeUsername(newUsername).getOrAwaitValue().data

        assertEquals(expectedResponse, actualResponse)

        Mockito.verify(useCase).changeUsername(newUsername)
    }


}