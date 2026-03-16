package com.lobosmanuel.marsapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lobosmanuel.marsapp.model.remote.MarsRealState

/**
 * Interface DAO (Data Access Object).
 * Provee los métodos que la aplicación utilizará para interactuar con la
 * base de datos SQLite mediante Room.
 */
@Dao
interface MarsDao {

    /**
     * Inserta un único terreno.
     * @onConflict REPLACE: Si el ID ya existe, reemplaza los datos antiguos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTerrain(terrain: MarsRealState)

    /**
     * Inserción masiva de terrenos.
     * Útil para guardar la lista completa proveniente de la API en una sola operación.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTerrains(terrain: List<MarsRealState>)

    /**
     * Actualiza los valores de un terreno específico basándose en su PrimaryKey.
     */
    @Update
    suspend fun updateTerrains(terrain: MarsRealState)

    /**
     * Elimina un terreno específico de la base de datos.
     */
    @Delete
    suspend fun deleteTerrains(terrain: MarsRealState)

    /**
     * Borrado total de la tabla.
     * Se utiliza para limpiar el caché local o resetear los datos.
     */
    @Query("DELETE FROM mars_table")
    suspend fun deletAll()

    /**
     * Recupera todos los terrenos almacenados.
     * @return LiveData: Permite observar cambios en tiempo real desde la UI.
     * Los resultados se ordenan por ID de forma descendente.
     */
    @Query("SELECT * FROM mars_table ORDER BY id DESC")
    fun getAllTerrains(): LiveData<List<MarsRealState>>

    /**
     * Filtra terrenos por tipo (ej. "rent" o "buy").
     * @param type El tipo de terreno a buscar.
     * LIMIT 1: Retorna solo el primer resultado que coincida.
     */
    @Query("SELECT * FROM mars_table WHERE type = :type LIMIT 1")
    fun getTerrainByType(type: String): LiveData<MarsRealState>

    /**
     * Busca un terreno específico por su identificador único.
     * @param id El ID único de la parcela.
     */
    @Query("SELECT * FROM mars_table WHERE id = :id")
    fun getTerrainById(id: String): LiveData<MarsRealState>
}