package se.hkr.myapplication.ui.main_event

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import se.hkr.myapplication.ui.models.Event

class EventRepo {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    @Volatile
    private var INSTANCE: EventRepo? = null

    fun getInstance(): EventRepo? {
        return INSTANCE ?: synchronized(this) {
            val instance = EventRepo()
            instance
        }
    }

    fun getEventData(eventList: MutableLiveData<List<Event>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _eventList: List<Event> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Event::class.java)!!
                    }
                    eventList.postValue(_eventList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}