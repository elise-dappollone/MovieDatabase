package com.edapp.moviedatabase.viewmodels

import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.Movie
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemListViewModelTest : TestCase() {

    private val mockRepository = mock<MovieRepository>()
    private val viewModel = ItemListViewModel(Dispatchers.Unconfined)

    private val movieOne = Movie(
            id = "000001",
            title = "MovieOne",
            overview = "MovieOne description",
            poster_path = "testPosterPathOne.jpg",
            backdrop_path = "testBackdropPathOne.jpg",
            release_date = "2021-01-01")

    private val movieTwo = Movie(
            id = "000002",
            title = "MovieTwo",
            overview = "MovieTwo description",
            poster_path = "testPosterPathTwo.jpg",
            backdrop_path = "testBackdropPathTwo.jpg",
            release_date = "2020-01-01")

    private val movieThree = Movie(
            id = "000003",
            title = "MovieThree",
            overview = "MovieThree description",
            poster_path = "testPosterPathThree.jpg",
            backdrop_path = "testBackdropPathThree.jpg",
            release_date = "2019-01-01")

    private val movieFour = Movie(
            id = "000004",
            title = "MovieFour",
            overview = "MovieFour description",
            poster_path = "testPosterPathFour.jpg",
            backdrop_path = "testBackdropPathFour.jpg",
            release_date = "2018-01-01")

    @Before
    fun setup() {
        viewModel.repository = mockRepository
    }

    @Test
    fun testOnCreateSuccess() {
        runBlockingTest {
            doReturn(listOf(movieOne, movieTwo, movieThree)).whenever(mockRepository).getPopularMoviesList(1)
            viewModel.onCreate()
        }
        assertEquals(movieOne, viewModel.movieList[0])
        assertEquals(movieTwo, viewModel.movieList[1])
        assertEquals(movieThree, viewModel.movieList[2])
    }

    @Test
    fun testOnCreateCallFailure() {
        runBlockingTest {
            doReturn(null).whenever(mockRepository).getPopularMoviesList(1)
            viewModel.onCreate()
        }
        assertTrue(viewModel.movieList.isEmpty())
    }

    @Test
    fun testLoadMoreClicked() {
        runBlockingTest {
            doReturn(listOf(movieThree, movieFour)).whenever(mockRepository).getPopularMoviesList(2)
            viewModel.loadMoreClicked()
        }
        assertEquals(movieThree, viewModel.movieList[0])
        assertEquals(movieFour, viewModel.movieList[1])
    }

    @Test
    fun testLoadMoreClickedCallFailure() {
        runBlockingTest {
            doReturn(null).whenever(mockRepository).getPopularMoviesList(2)
            viewModel.onCreate()
        }
        assertTrue(viewModel.movieList.isEmpty())
    }
}