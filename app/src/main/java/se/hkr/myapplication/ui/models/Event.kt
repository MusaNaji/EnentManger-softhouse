package se.hkr.myapplication.ui.models

data class Event(
    var eventName: String? = null,
    var branchName: String? = null,
    var eventLocation: String? = null,
    val start: String? = null,
    val end: String? = null,
    val downloadUrl: String? = null,
    val eventDescription: String? = null,
    val map_link: String? = null
)
