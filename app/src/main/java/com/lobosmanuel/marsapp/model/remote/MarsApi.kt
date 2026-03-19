package com.lobosmanuel.marsapp.model.remote

import retrofit2.Response
import retrofit2.http.GET


interface MarsApi {

    @GET ("realestate")
    suspend fun  fetchMarsDataCoroutines(): Response<List<MarsRealState>>
}