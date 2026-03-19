package com.lobosmanuel.marsapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marsapipm.View.SecondFragment
import com.lobosmanuel.marsapp.R
import com.lobosmanuel.marsapp.databinding.FragmentFirstBinding
import com.lobosmanuel.marsapp.viewmodel.MarsViewModel

import java.nio.BufferUnderflowException

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    // instancia del viewModel
    private val viewModel: MarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // instanciar el adapter

        val adapter = AdapterMars()
        _binding.rvTerrains.adapter = adapter
        _binding.rvTerrains.layoutManager = GridLayoutManager(context, 2)


        // visualización del RV
        viewModel.liveDataFromInternet.observe(viewLifecycleOwner, Observer {

                data ->

            data?.let {
                Log.d("FirsFragment", "Datos Recibidos desde Internet : $it")

                //  para que carguen los datos
                adapter.setList(it)
            }
        })


        // SELECCIÓN DE UN TERRENO

        adapter.selectedTerrain.observe(viewLifecycleOwner) {

                selected ->

            selected?.let {
                // Crear Bundle para pasar datos al segundo fragmento

                val bundle = Bundle().apply {

                    putString("id", it.id)
                    putString("imgSrc", it.img_Src)
                }
                // Crear instancia del fragmento

                val secondFragment = SecondFragment()
                secondFragment.arguments = bundle

                // reemplazar el fragmento

                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, secondFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // _binding = null
    }
}