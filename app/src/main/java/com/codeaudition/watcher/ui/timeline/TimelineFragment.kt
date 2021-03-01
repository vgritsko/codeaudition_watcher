package com.codeaudition.watcher.ui.timeline


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeaudition.watcher.data.FirebaseLocation
import com.codeaudition.watcher.databinding.FragmentTimelineBinding
import com.codeaudition.watcher.ui.base.AppBaseFragment
import com.codeaudition.watcher.ui.timelinepackage.FireabaseAdapter
import com.codeaudition.watcher.utils.extensions.DateExtensions
import com.codeaudition.watcher.utils.extensions.currentDate
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.*


class TimelineFragment : AppBaseFragment<FragmentTimelineBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FireabaseAdapter

    private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }


    private fun setupUI() {
        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val date = Date().currentDate(DateExtensions.appDateFormat)
        val options = initOptions(date)
        adapter = FireabaseAdapter(options)
        recyclerView.adapter = adapter


        val today = Calendar.getInstance()
        datePicker = binding!!.datePicker
        datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dateOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dateOfMonth)
            val selectedDate = calendar.time
            updateQuery(DateExtensions.appDateFormat.format(selectedDate))
        }
    }

    private fun updateQuery(date: String) {
        val options: FirebaseRecyclerOptions<FirebaseLocation> =
            initOptions(date)
        adapter.updateOptions(options)
    }

    private fun initOptions(date: String): FirebaseRecyclerOptions<FirebaseLocation> {
        val query: Query = FirebaseDatabase.getInstance()
            .reference
            .child("locations/$date")

        val options: FirebaseRecyclerOptions<FirebaseLocation> =
            FirebaseRecyclerOptions.Builder<FirebaseLocation>()
                .setQuery(query, FirebaseLocation::class.java)
                .build()
        return options
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimelineBinding = FragmentTimelineBinding.inflate(inflater, container, false)
}