package se.hkr.myapplication.ui.models

data class Events(
    var eventId: String? = null,
    var eventName: String? = null,
    var branchName: String? = null,
    var eventLocation: String? = null,
    val eventDescription: String? = null,
    val downloadUrl: String?,
    val start: String? = null,
    val end: String? = null,
    val map_link: String? = null

    )
