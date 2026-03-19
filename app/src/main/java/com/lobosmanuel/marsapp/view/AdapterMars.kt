package com.lobosmanuel.marsapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lobosmanuel.marsapp.databinding.MarsItemBinding
import com.lobosmanuel.marsapp.model.remote.MarsRealState


class AdapterMars  : RecyclerView.Adapter<AdapterMars.MarsVH>() {


    private var listMarsItem = listOf<MarsRealState>()

    // Para seleccinar tenemos una variables que tiene un listado de nuestra clase
    val selectedTerrain = MutableLiveData<MarsRealState>()

    // funcion para seleccionar
    fun seletedItem(): LiveData<MarsRealState> = selectedTerrain



    fun setList(list: List<MarsRealState>){
        listMarsItem = list
        // notifica al rv
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsVH {
        return MarsVH(MarsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsVH, position: Int) {
        val terrain = listMarsItem[position]
        holder.bin(terrain)
    }

    override fun getItemCount(): Int = listMarsItem.size

    inner class MarsVH(private val binding: MarsItemBinding):
        RecyclerView.ViewHolder(binding.root),
        android.view.View.OnClickListener{

        fun bin(mars : MarsRealState){

            Glide.with(binding.imageView2).load(mars.img_Src).centerCrop().into(binding.imageView2)
            // activar el click

            itemView.setOnClickListener(this)

        }
        override fun onClick(v : View?){

            // para seleccionar
            selectedTerrain.value = listMarsItem[adapterPosition]
        }


    }
}

