package com.lobosmanuel.marsapp

import com.lobosmanuel.marsapp.model.remote.MarsRealState
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Clase de prueba unitaria para validar el modelo de datos [MarsRealState].
 * * Se encarga de verificar que el constructor asigne correctamente los valores,
 * que las reglas de negocio básicas (como precios positivos) se cumplan y
 * que la comparación entre objetos funcione según lo esperado.
 */
class MarsRealStateTest {

    /**
     * Instancia principal de [MarsRealState] que se reinicia antes de cada test.
     */
    private lateinit var  marsTerrain : MarsRealState

    /**
     * Configuración inicial.
     * Se crea un objeto base con valores conocidos para realizar las aserciones.
     */
    @Before
    fun setUp(){
        // Creamos la instancia para test
        marsTerrain = MarsRealState(
            id = "1",
            price = 50000,
            type = "rent",
            img_Src = "url"
        )
    }

    /**
     * Limpieza de recursos.
     * En este caso no se requieren acciones específicas al ser objetos en memoria.
     */
    @After
    fun tearDown(){
        // No hay recursos que liberar en este caso
    }

    /**
     * Verifica que el constructor asigne los valores a las propiedades correspondientes.
     */
    @Test
    fun testMarsRealStateConstructor(){
        assertEquals("1", marsTerrain.id)
        assertEquals(50000, marsTerrain.price)
        assertEquals("rent", marsTerrain.type)
        assertEquals("url", marsTerrain.img_Src)
    }

    /**
     * Valida que la instancia creada no sea nula.
     */
    @Test
    fun TestMarsRealStateNotNull(){
        assertNotNull(marsTerrain)
    }

    /**
     * Prueba de regla de negocio: El precio de una propiedad en Marte debe ser positivo.
     */
    @Test
    fun testMarsRealStatePricePositive(){
        assertTrue(marsTerrain.price > 0)
    }

    /**
     * Compara dos objetos distintos de [MarsRealState] para asegurar que se diferencian correctamente.
     */
    @Test
    fun testDifferenteMarsRealStateObjects(){
        val otherTerrain = MarsRealState(
            id = "2",
            price = 50000,
            type = "buy",
            img_Src = "other_url"
        )
        // Verificación de IDs
        assertNotNull(marsTerrain.id, otherTerrain.id)

        // ESTA LÍNEA ES LA QUE HACE FALLAR EL TEST
        assertNotEquals(marsTerrain.price, otherTerrain.price)
    }
}