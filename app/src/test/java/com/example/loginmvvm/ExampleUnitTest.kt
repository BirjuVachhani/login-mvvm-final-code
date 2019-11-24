package com.example.loginmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.loginmvvm.login.di.networkModule
import com.example.loginmvvm.login.state.LoginScreenState
import com.example.loginmvvm.login.viewmodel.LoginActivityVM
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest : KoinTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    val pattern: Pattern = Mockito.mock(Pattern::class.java)

    val matcher: Matcher = Mockito.mock(Matcher::class.java)

    lateinit var viewModel: LoginActivityVM

    /*@ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule(testDispatcher)*/
    val testModule = module {
        single { pattern }
    }

    @Before
    fun before() {
        startKoin {
            modules(listOf(networkModule, testModule))
        }
        Mockito.doReturn(matcher).`when`(pattern).matcher(Mockito.anyString())
        viewModel = LoginActivityVM(coroutinesTestRule.testDispatchers)
    }

    @Test
    fun `email validation test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.doReturn(false).`when`(matcher).matches()
        viewModel.state().observeForever {}

        viewModel.login("", "")
        assert(viewModel.state().value is LoginScreenState.EmailValidationError)

        viewModel.login("admin@example", "")
        assert(viewModel.state().value is LoginScreenState.EmailValidationError)
    }

    @Test
    fun `password validation test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.doReturn(true).`when`(matcher).matches()

        viewModel.state().observeForever {}

        viewModel.login("admin@gmail.com", "")
        assert(viewModel.state().value is LoginScreenState.PasswordValidationError)

        viewModel.login("admin@gmail.com", "1248")
        assert(viewModel.state().value is LoginScreenState.PasswordValidationError)
    }

    @Test
    fun `successful login api test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.doReturn(true).`when`(matcher).matches()
        viewModel.state().observeForever {}
        viewModel.login(
            "admin@gmail.com",
            "123                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    45678"
        )
        Thread.sleep(2000)
        assert(viewModel.state().value is LoginScreenState.LoginSuccess)
    }

    @Test
    fun `Incorrect login api test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.doReturn(true).`when`(matcher).matches()

        viewModel.state().observeForever {}

        viewModel.login("admin@gmail.com2", "123456782")
        Thread.sleep(2000)
        assert(viewModel.state().value is LoginScreenState.LoginFailure)
    }

    @After
    fun after() {
        stopKoin()
    }
}