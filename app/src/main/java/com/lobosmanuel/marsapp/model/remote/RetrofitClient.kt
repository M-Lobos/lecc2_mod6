package com.lobosmanuel.marsapp.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente de Retrofit encargado de centralizar la configuración de red.
 * Se define como 'class' con un 'companion object' para permitir el acceso
 * global a la instancia de la API sin necesidad de instanciar la clase.
 */
class RetrofitClient {

    companion object {
        /**
         * URL base de la API de la NASA.
         * Debe terminar siempre en una barra diagonal (/).
         */
        private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

        /**
         * Configura y construye la instancia de Retrofit.
         * @return Una implementación de la interfaz [MarsApi].
         */
        fun getRetrofit(): MarsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Convierte automáticamente el JSON de la NASA a objetos Kotlin usando GSON
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Crea la implementación de los métodos definidos en la interfaz MarsApi
            return retrofit.create(MarsApi::class.java)
        }
    }
}