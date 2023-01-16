package se.hkr.myapplication.ui.main_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.hkr.myapplication.ui.models.Event

class EventViewModel : ViewModel() {
    private val repo: EventRepo
    private val _allevents = MutableLiveData<List<Event>>()
    val allEvents: LiveData<List<Event>> = _allevents

    init {
        repo = EventRepo().getInstance()!!
        repo.getEventData(_allevents)
    }
}