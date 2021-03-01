package com.codeaudition.watcher.ui.map

import android.location.Location
import com.codeaudition.watcher.data.FirebaseLocation
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FirebaseEventListenerHelper(private val firebaseDatabaseListener: FirebaseDatabaseListener) :
    ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val newLocation = snapshot.getValue(FirebaseLocation::class.java)
        firebaseDatabaseListener.onLocationAdded(newLocation!!)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        TODO("Not yet implemented")
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }


}
