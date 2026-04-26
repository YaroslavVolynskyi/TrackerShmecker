package com.example.trackershmecker.data.repository

import android.util.Log
import com.example.trackershmecker.data.local.DayEntryDao
import com.example.trackershmecker.data.remote.RemoteDataSource
import javax.inject.Inject

class FirebaseSyncRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dao: DayEntryDao,
) : SyncRepository {

    override suspend fun syncFromRemote() {
        try {
            val remoteEntries = remoteDataSource.fetchAllEntries()
            if (remoteEntries.isNotEmpty()) {
                dao.upsertAll(remoteEntries)
            }
            Log.d(TAG, "Synced ${remoteEntries.size} entries from remote")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync from remote", e)
        }
    }

    override suspend fun exportToRemote() {
        try {
            val localEntries = dao.getAll()
            remoteDataSource.uploadAllEntries(localEntries)
            Log.d(TAG, "Exported ${localEntries.size} entries to remote")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to export to remote", e)
        }
    }

    companion object {
        private const val TAG = "FirebaseSyncRepository"
    }
}
