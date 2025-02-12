package com.thatta.counterclassicxmlviewsmvvm.presentation.views.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thatta.counterclassicxmlviewsmvvm.R
import com.thatta.counterclassicxmlviewsmvvm.databinding.FlagItemBinding
import com.thatta.counterclassicxmlviewsmvvm.presentation.views.viewUtils.FlagDiffCallback

class FlagsAdapter(
    private val flagsList: MutableList<Int>
) : RecyclerView.Adapter<FlagsAdapter.FlagsViewHolder>() {

    inner class FlagsViewHolder(private val itemBinding: FlagItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(flag: Int) {
            itemBinding.tvFlagItemNumber.text =
                itemBinding.root.context.getString(R.string.value, flag.toString())
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
    fun updateFlags(newFlags: List<Int>, recyclerView: RecyclerView) {
        val diffCallback = FlagDiffCallback(flagsList, newFlags)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        flagsList.clear()
        flagsList.addAll(newFlags)
        diffResult.dispatchUpdatesTo(this)
    }
}