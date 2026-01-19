package com.nexdecade.composebase.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Generic PagingSource for APIs using limit & offset style paging
 * @param T The type of item (e.g., Pokemon)
 * @param com.nexdecade.composebase.R The type of API response (needs a `results: List<T>` field)
 * @param apiCall Lambda to call your API
 */


class BasePagingSource<T : Any>(
    private val pageSize: Int = 20,
    private val apiCall: suspend (limit: Int, offset: Int) -> List<T>
) : PagingSource<Int, T>() {
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 0
        return try {
            val items = apiCall(pageSize, page * pageSize)
            LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}

