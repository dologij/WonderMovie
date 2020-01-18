package com.brunix.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}