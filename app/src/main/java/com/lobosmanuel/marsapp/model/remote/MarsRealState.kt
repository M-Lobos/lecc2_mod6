package com.lobosmanuel.marsapp.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * CLASE DE MODELO DUAL (DTO + Entity)
 * * Esta clase se encuentra en el paquete 'remote' porque define la estructura
 * de los datos que llegan desde la API de la NASA.
 *
 * Sin embargo, al incluir anotaciones de Room, también actúa como el esquema de
 * la base de datos local
 *
 * 1. Como DTO (Data Transfer Object): Usa @SerializedName para mapear el JSON de Retrofit.
 *
 * 2. Como Entity: Usa @Entity para que Room cree una tabla persistente en el dispositivo.
 */
@Entity(tableName = "mars_table")
data class MarsRealState (

    /**
     * ID único de la propiedad.
     * @PrimaryKey: Indica a Room que este campo es la llave primaria.
     * @SerializedName("id"): Vincula el campo 'id' del JSON con esta variable.
     */
    @SerializedName("id")
    @PrimaryKey
    val id : String,

    @SerializedName("price")
    val price : Long,

    @SerializedName("type")
    val type: String,

    /**
     * URL de la imagen del terreno.
     * @SerializedName("img_src"): Crucial aquí porque el JSON usa guiones bajos (snake_case),
     * lo cual difiere del estándar de nombres de variables en Kotlin (camelCase).
     */
    @SerializedName("img_src")
    val img_Src : String
)