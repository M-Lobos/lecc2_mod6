package com.lobosmanuel.marsapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lobosmanuel.marsapp.model.remote.MarsRealState

/**
 * Clase principal de la base de datos Room.
 * Actúa como el punto de acceso principal para la conexión persistente con los datos.
 * @Database: Define las entidades que pertenecen a la DB y la versión del esquema.
 */
@Database(entities = [MarsRealState::class], version = 1, exportSchema = false)
abstract class MarsDatabase : RoomDatabase() {

    /**
     * Define el acceso al DAO. Room generará la implementación automáticamente.
     */
    abstract fun marsDao(): MarsDao

    companion object {
        /**
         * @Volatile: Garantiza que el valor de INSTANCE sea siempre actual para todos los hilos
         * de ejecución, evitando errores de lectura en caché.
         */
        @Volatile
        private var INSTANCE: MarsDatabase? = null

        /**
         * Obtiene la instancia única (Singleton) de la base de datos.
         * Se usa el operador Elvis (?:) y un bloque synchronized para asegurar que
         * solo se cree una instancia en toda la vida de la aplicación.
         */
        fun getDataBase(context: Context): MarsDatabase {
            // Si la instancia ya existe, se retorna inmediatamente
            return INSTANCE ?: synchronized(this) {
                // Se construye la base de datos usando el context de la aplicación
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarsDatabase::class.java,
                    "mars_database" // Nombre del archivo SQLite generado
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}