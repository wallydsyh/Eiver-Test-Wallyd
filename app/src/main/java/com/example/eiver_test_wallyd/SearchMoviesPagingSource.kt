package com.example.eiver_test_wallyd

import androidx.paging.PagingSource
import com.example.eiver_test_wallyd.Constant.ApiKey
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.repository.MovieRepository

class SearchMoviesPagingSource(
    private val repository: MovieRepository,
    private val query: String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1
        return try {
            val data = repository.searchMovie(query, position, ApiKey.API_KEY)
            data.run {
                LoadResult.Page(data = this.results,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == this.total) null else position + 1)
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}