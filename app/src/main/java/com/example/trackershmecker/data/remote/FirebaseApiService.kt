package com.example.trackershmecker.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface FirebaseApiService {

    @GET("day_entries.json")
    suspend fun getAllEntries(): Map<String, DayEntryDto>?

    @PUT("day_entries.json")
    suspend fun putAllEntries(@Body entries: Map<String, DayEntryDto>): Map<String, DayEntryDto>

    @PUT("day_entries/{date}.json")
    suspend fun putEntry(@Path("date") date: String, @Body entry: DayEntryDto): DayEntryDto
}
