// api/AirtableAPI.kt

package com.example.wavetrackercompose.api

import com.example.wavetrackercompose.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AirtableAPI {
//    @GET("v0/{baseId}/{tableName}")
//    suspend fun getRecords(
//        @Path("baseId") baseId: String,
//        @Path("tableName") tableName: String,
//        @Header("Authorization") authorization: String
//    ): Response<ResponseModel>

    @GET("v0/{baseId}/{tableName}")
    suspend fun getSpots(
        @Path("baseId") baseId: String,
        @Path("tableName") tableName: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseModel>
}