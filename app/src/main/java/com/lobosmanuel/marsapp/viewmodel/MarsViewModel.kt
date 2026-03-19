package com.lobosmanuel.marsapp.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lobosmanuel.marsapp.model.MarsRepository
import com.lobosmanuel.marsapp.model.local.MarsDatabase
import com.lobosmanuel.marsapp.model.remote.MarsRealState
import kotlinx.coroutines.launch



// ViewModel que extiende de AndroidViewModel para poder usar el contexto de la aplicación
class MarsViewModel (application: Application): AndroidViewModel(application) {


    ////////// PARTE 1////////////////////////////////
    // Repositorio que manejará los datos (Room + API)

    private val repository : MarsRepository


    //PARTE 2
    // LiveData que contendrá los datos obtenidos desde internet

    lateinit var liveDataFromInternet : LiveData<List<MarsRealState>>

    // LiveData que contiene todos los datos almacenados en la base de datos

    val allTerrain : LiveData<List<MarsRealState>>

    init {
        // Obtiene el DAO desde la base de datos Room

        val MarsDao = MarsDatabase.getDataBase(application).marsDao()
        // Se inicializa el repositorio pasando el DAO

        repository = MarsRepository(MarsDao)

        // función del repository

        viewModelScope.launch {
            repository.fechDataFromInternetCoroutines()
        }

        // Asigna el LiveData de la base de datos desde el repositorio

        allTerrain = repository.listAllTask

        // Asigna el LiveData que contiene los datos obtenidos de internet

        liveDataFromInternet = repository.dataFromInternet

        /*    liveDataFromInternet.observeForever { lista ->

                Log.d("Mars_Api","Datos Recibidos :${lista.size}")
                lista.forEach {

                    Log.d("MARS_ITEM",it.toString())
                }
            }*/
    }



    // Variable MutableLiveData para guardar el terreno de Marte seleccionado

    private var selectMarsTerrain : MutableLiveData<MarsRealState> = MutableLiveData()

    // Devuelve el elemento seleccionado como LiveData (solo lectura)

    fun selectedItem() : LiveData<MarsRealState> = selectMarsTerrain




    // funciones del Crud


    fun insertTerrain ( terrain : MarsRealState) = viewModelScope.launch{

        repository.insertTerrain(terrain)
    }


    // actualizar

    fun updateTerrain( terrain: MarsRealState) = viewModelScope.launch {
        repository.updateTerrains(terrain)
    }
    // obtener un elemento por id

    fun getTerrainById(id:String): LiveData<MarsRealState>{

        return repository.getMarsById(id)

    }



}