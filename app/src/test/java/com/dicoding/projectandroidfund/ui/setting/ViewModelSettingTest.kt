package com.dicoding.projectandroidfund.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dicoding.projectandroidfund.data.local.SettingPref
import com.dicoding.projectandroidfund.ui.setting.ViewModelSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class ViewModelSettingTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModelSetting: ViewModelSetting

    @Mock
    private lateinit var themeObserver: Observer<Boolean>

    @Mock
    private lateinit var settingPref: SettingPref

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModelSetting = ViewModelSetting(settingPref)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    suspend fun `test save theme`() {
        val isDarkTheme = true

        viewModelSetting.getTheme().observeForever(themeObserver)
        viewModelSetting.saveTheme(isDarkTheme)

        verify(settingPref).saveThemeSetting(isDarkTheme)
        verify(themeObserver).onChanged(isDarkTheme)
    }
}
