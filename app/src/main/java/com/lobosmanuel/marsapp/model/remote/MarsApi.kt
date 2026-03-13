package com.lobosmanuel.marsapp.model.remote

import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz que define los puntos de entrada (endpoints) de la API de la NASA.
 * Retrofit utiliza esta interfaz para generar la implementación de las peticiones de red.
 */
interface MarsApi {
    /**
     * Obtiene la lista de propiedades de Marte desde el endpoint "realestate".
     *
     * @GET ("realestate"): Indica que se realizará una petición HTTP de tipo GET
     * al recurso especificado en la URL base.
     *
     * suspend: Palabra clave de Kotlin que indica que esta función es una corrutina.
     * Permite pausar la ejecución sin bloquear el hilo principal (UI thread)
     * hasta que la respuesta esté lista.
     *
     *  @return Response<List<MarsRealState>>: Un objeto que envuelve la lista de
     * resultados, permitiendo verificar si la respuesta fue exitosa (200 OK)
     * o si hubo errores del servidor (404, 500, etc.).
     */

    @GET("realestate")
    suspend fun fetchMarsDataCoroutines(): Response<List<MarsRealState>>
}