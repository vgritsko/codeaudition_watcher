package com.codeaudition.watcher.ui.timelinepackage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codeaudition.watcher.data.FirebaseLocation
import com.codeaudition.watcher.databinding.ItemLocationBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FireabaseAdapter(options: FirebaseRecyclerOptions<FirebaseLocation>) :
    FirebaseRecyclerAdapter<FirebaseLocation, FireabaseAdapter.FirebaseLocatonViewholder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseLocatonViewholder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirebaseLocatonViewholder(binding)
    }

    override fun onBindViewHolder(
        holder: FirebaseLocatonViewholder,
        position: Int,
        model: FirebaseLocation
    ) {
        with(holder) {
            binding.latitudeValueTextView.setText(model.latitude.toString())
            binding.longtitudeValueTextView.setText(model.longitude.toString())
        }
    }

    inner class FirebaseLocatonViewholder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root)

}