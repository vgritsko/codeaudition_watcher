package com.codeaudition.watcher.ui.map

import com.codeaudition.watcher.data.FirebaseLocation

interface FirebaseDatabaseListener {
    fun onLocationAdded (location: FirebaseLocation)
}