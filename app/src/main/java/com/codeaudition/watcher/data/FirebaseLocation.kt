package com.codeaudition.watcher.data


data class FirebaseLocation(
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this(
        0.0, 0.0
    )
}
