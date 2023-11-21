package com.mutondo.weathermviapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutondo.weathermviapp.databinding.ItemFavoriteBinding
import com.mutondo.weathermviapp.domain.models.FavoriteLocation

class FavoriteAdapter(
    private val locations: List<FavoriteLocation>
): RecyclerView.Adapter<FavoriteAdapter.FavoriteLocationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteLocationViewHolder {
        return FavoriteLocationViewHolder(
            ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: FavoriteLocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    inner class FavoriteLocationViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: FavoriteLocation) {
            val loc = "${location.latitude}, ${location.longitude}"

            binding.name.text = location.name
            binding.location.text = loc
        }
    }
}