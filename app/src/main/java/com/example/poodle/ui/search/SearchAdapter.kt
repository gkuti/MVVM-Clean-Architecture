package com.example.poodle.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.Breed
import com.example.poodle.R
import com.example.poodle.databinding.BreedItemBinding
import com.example.poodle.ui.utils.SearchDiffUtil

class SearchAdapter(
    private val listener: Listener,
    private val breeds: ArrayList<Breed> = ArrayList()
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BreedItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.bind(breeds[position])
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

    fun updateList(breeds: List<Breed>) {
        val diffResult = DiffUtil.calculateDiff(SearchDiffUtil(this.breeds, breeds))
        this.breeds.clear()
        this.breeds.addAll(breeds)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SearchViewHolder(private val binding: BreedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.breedName.text = breed.name
            binding.temperament.text = breed.temperament
            binding.breedImage.load(
                binding.root.context.getString(R.string.image_url, breed.referenceImageId)
            ) {
                error(
                    R.drawable.paw
                )
            }
            binding.root.setOnClickListener {
                listener.onClick(breeds[absoluteAdapterPosition])
            }
        }
    }

    interface Listener {
        fun onClick(breed: Breed)
    }
}
