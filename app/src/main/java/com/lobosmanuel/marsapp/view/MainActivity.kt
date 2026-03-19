package com.lobosmanuel.marsapp.view


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.lobosmanuel.marsapp.R
import com.lobosmanuel.marsapp.databinding.ActivityMainBinding
import com.lobosmanuel.marsapp.viewmodel.MarsViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var  viewModel: MarsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // inicializar el viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MarsViewModel::class.java]




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val firstFragment = FirstFragment()

        if(savedInstanceState == null){

            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, firstFragment)
                .commitNow()
        }




        viewModel.liveDataFromInternet.observe(this){

                lista->

            Log.d("Mars_Api","Datos Recibidos :${lista.size}")
            lista.forEach {

                Log.d("MARS_ITEM",it.toString())
            }
        }




    }






}