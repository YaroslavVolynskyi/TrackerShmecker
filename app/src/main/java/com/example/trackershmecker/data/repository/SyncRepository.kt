package com.example.trackershmecker.data.repository

interface SyncRepository {
    suspend fun syncFromRemote()
    suspend fun exportToRemote()
}
