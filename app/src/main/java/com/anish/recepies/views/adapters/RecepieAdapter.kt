package com.anish.recepies.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anish.recepies.databinding.RowRecepieBinding
import com.anish.recepies.models.Recipe
import com.anish.recepies.viewmodels.MainViewModel
import com.anish.recepies.views.adapters.RecepieAdapter.MYViewHolder


class RecepieAdapter(vm: MainViewModel) : RecyclerView.Adapter<MYViewHolder>() {

    private var recepies: List<Recipe>? = null
    var vm = vm

    fun setrecepies(recepies: List<Recipe>?) {
        this.recepies = recepies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowRecepieBinding = RowRecepieBinding.inflate(layoutInflater, parent, false)
        return MYViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MYViewHolder, position: Int) {
        holder.bind(vm, position)
    }

    override fun getItemCount(): Int {
        return if (recepies == null) 0 else recepies!!.size
    }

    inner class MYViewHolder(binding: RowRecepieBinding) : ViewHolder(binding.getRoot()) {
        var binding = binding
        fun bind(viewmodel: MainViewModel, position: Int) {
            binding.vm = viewmodel
            binding.pos = position
            binding.executePendingBindings()
        }
    }
}