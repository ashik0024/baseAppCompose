package com.nexdecade.composebase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


open class BasePagingRepository<T : Any>(
    private val pageSize: Int = 20
) {
    
    fun getPager(apiCall: suspend (limit: Int, offset: Int) -> List<T>): Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = { BasePagingSource(pageSize, apiCall) }
        ).flow
    }
}
