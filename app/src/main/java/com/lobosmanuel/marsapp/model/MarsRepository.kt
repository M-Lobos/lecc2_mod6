package com.lobosmanuel.marsapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lobosmanuel.marsapp.model.local.MarsDao
import com.lobosmanuel.marsapp.model.remote.MarsRealState
import com.lobosmanuel.marsapp.model.remote.RetrofitClient

class MarsRepository(private  val marsDao: MarsDao) {
    // llama al metodo de conexion
    private val retrofitClient = RetrofitClient.getRetrofit()

    // HACE REFERENCIA AL POJO Y LA RESPUESTA VAMOS A RECIBIR
    val dataFromInternet = MutableLiveData<List<MarsRealState>>()

    suspend fun fechDataFromInternetCoroutines() {
        try {
            val response = retrofitClient.fetchMarsDataCoroutines()

            when (response.code()) {
                in 200..299 -> response.body()?.let { lista ->
                    marsDao.insertAllTerrains(lista)
                    dataFromInternet.postValue(lista)
                }

                in 300..301 -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
            }

        } catch (t: Throwable) {
            Log.e("REPO", "Error ${t.message}")
        }
    }
    fun getMarsById(id: String): LiveData<MarsRealState> {
        return marsDao.getTerrainById(id)
    }
    val listAllTask: LiveData<List<MarsRealState>> = marsDao.getAllTerrains()
    // insert terreno
    suspend fun insertTerrain(terrain: MarsRealState) {
        marsDao.inserTerrain(terrain)
    }
    // actualizar terrenos
    suspend fun updateTerrains(terrain: MarsRealState) {
        marsDao.updateTerrains(terrain)
    }
    // eliminar terrenos
    suspend fun deletAll() {
        marsDao.deletAll()
    }
}