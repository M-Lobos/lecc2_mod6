package com.lobosmanuel.marsapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule // REGLA CRÍTICA
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lobosmanuel.marsapp.model.local.MarsDatabase
import com.lobosmanuel.marsapp.model.remote.MarsRealState
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * Clase de prueba instrumentada para validar el comportamiento de la base de datos [MarsDatabase].
 * Utiliza [InstantTaskExecutorRule] para sincronizar las emisiones de LiveData.
 */
@RunWith(AndroidJUnit4::class)
class MarsDataBaseTest {

    /**
     * Esta regla obliga a que todas las tareas de arquitectura de Android (como LiveData)
     * se ejecuten instantáneamente en el mismo hilo del test, evitando valores nulos por asincronía.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MarsDatabase

    /**
     * Inicializa una base de datos en memoria antes de cada test.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            MarsDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    /**
     * Cierra la base de datos para liberar memoria.
     */
    @After
    fun closeDb() {
        db.close()
    }

    /**
     * Prueba la inserción de un terreno y verifica la respuesta del LiveData.
     */
    @Test
    fun insertTerrainTest() = runBlocking {
        // 1. Preparar el dato
        val terrain = MarsRealState(
            id = "1",
            price = 40000,
            type = "rent",
            img_Src = "url"
        )

        // 2. Insertar en la BD
        db.marsDao().inserTerrain(terrain)

        // 3. Obtener el LiveData y activarlo con observeForever
        val liveData = db.marsDao().getAllTerrains()
        liveData.observeForever { }

        // 4. Verificar el resultado (Gracias a la Rule, ahora no será null)
        val resultList = liveData.value

        assertEquals(1, resultList?.size)
        assertEquals("1", resultList?.get(0)?.id)
    }
}