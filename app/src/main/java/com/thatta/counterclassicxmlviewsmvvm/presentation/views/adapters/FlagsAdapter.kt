package com.thatta.counterclassicxmlviewsmvvm.presentation.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thatta.counterclassicxmlviewsmvvm.databinding.FlagItemBinding

class FlagsAdapter(
    private val flagsList: MutableList<Int>
) : RecyclerView.Adapter<FlagsAdapter.FlagsViewHolder>() {
    inner class FlagsViewHolder(private val itemBinding: FlagItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(flag: Int) {
            itemBinding.tvFlagItemNumber.text = flag.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagsViewHolder {
        val itemBinding = FlagItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FlagsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = flagsList.size

    override fun onBindViewHolder(holder: FlagsViewHolder, position: Int) {
       val flagItem = flagsList[position]
        holder.bind(flagItem)
    }

    // Method to update the list of flags
    @SuppressLint("NotifyDataSetChanged")
    fun updateFlags(newFlags: List<Int>, recyclerView: RecyclerView) {
        flagsList.clear()
        flagsList.addAll(newFlags)
        notifyDataSetChanged()
        recyclerView.scrollToPosition(- flagsList.size - 1)
    }
}