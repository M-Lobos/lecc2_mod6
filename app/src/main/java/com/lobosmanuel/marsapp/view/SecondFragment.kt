package com.example.marsapipm.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lobosmanuel.marsapp.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private lateinit var _binding: FragmentSecondBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibir datos
        val id = arguments?.getString("id")
        val imgSrc = arguments?.getString("imgSrc")

        // Mostrar datos
        binding.textMarsId.text = id

        Glide.with(requireContext())
            .load(imgSrc)
            .centerCrop()
            .into(binding.imageMars)

        // BOTÓN VOLVER
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}