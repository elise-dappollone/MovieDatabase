package com.edapp.moviedatabase.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.MovieDetail
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemDetailViewModelTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = ItemDetailViewModel(Dispatchers.Unconfined)
    private val mockRepository = mock<MovieRepository>()

    private val movieDetail = MovieDetail(
        id = "1001",
        revenue = 100000000,
        budget = 200000000,
        runtime = 300)

    @Before
    fun setup() {
        viewModel.repository = mockRepository
    }

    @Test
    fun testOnCreateSuccess() {
        val movieId = 1001
        runBlockingTest {
            doReturn(movieDetail).whenever(mockRepository).getMovieDetailsForId(movieId)
            viewModel.onCreate(movieId)
        }
        assertEquals(100000000, viewModel.movieDetail.value?.revenue)
        assertEquals(200000000, viewModel.movieDetail.value?.budget)
        assertEquals(300, viewModel.movieDetail.value?.runtime)
    }

    @Test
    fun testOnCreateCallFailure() {
        val movieId = 1001
        runBlockingTest {
           doReturn(null).whenever(mockRepository).getMovieDetailsForId(movieId)
            viewModel.onCreate(movieId)
        }
        assertEquals(0, viewModel.movieDetail.value?.revenue)
        assertEquals(0, viewModel.movieDetail.value?.budget)
        assertEquals(0, viewModel.movieDetail.value?.runtime)
    }
}