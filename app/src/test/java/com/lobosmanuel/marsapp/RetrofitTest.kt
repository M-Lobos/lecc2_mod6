package com.lobosmanuel.marsapp

import com.lobosmanuel.marsapp.model.remote.MarsApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

/**
 * Clase de prueba unitaria para verificar la configuración y el comportamiento inicial
 * de la infraestructura de red de la aplicación MarsApp.
 * * Esta clase utiliza [MockWebServer] para simular un entorno de servidor local,
 * permitiendo validar que [Retrofit] esté correctamente configurado sin realizar
 * peticiones reales a la API externa de la NASA.
 */
class RetrofitTest {

    /** * Servidor web simulado que intercepta y responde a las peticiones HTTP durante los tests.
     */
    private lateinit var  mockWebServer: MockWebServer

    /** * Instancia de Retrofit configurada dinámicamente para apuntar al servidor de pruebas.
     */
    private lateinit var retrofit: Retrofit

    /**
     * Configuración inicial antes de cada caso de prueba.
     * Inicializa y arranca el [MockWebServer], y construye la instancia de [Retrofit]
     * utilizando la URL base generada por el servidor local.
     */
    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Prueba unitaria que valida la correcta asignación de la URL base en Retrofit.
     * * El test verifica que la URL configurada en el cliente coincida con la URL
     * proporcionada por el [MockWebServer] activo.
     * * @see MarsApi Interfaz de los servicios de la API de Marte.
     */
    @Test
    fun testRetrofitBaseUrl(){
        // Crear la API con la instancia de Retrofit de prueba
        val marsApi = retrofit.create(MarsApi::class.java)

        // Verificar que el baseUrl sea el correcto
        val actualBaseUrl = retrofit.baseUrl().toString()
        println("DEBUG: La URL que obtuve es: $actualBaseUrl")

        val expectedBaseUrl = mockWebServer.url("/").toString()

        assertEquals(expectedBaseUrl, actualBaseUrl)
    }

    /**
     * Limpieza de recursos después de cada caso de prueba.
     * Apaga el [MockWebServer] para garantizar que los puertos locales queden libres.
     */
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}