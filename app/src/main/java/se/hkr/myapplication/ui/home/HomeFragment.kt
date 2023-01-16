package se.hkr.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.hkr.myapplication.*
import se.hkr.myapplication.ui.main_event.EventAdapter
import se.hkr.myapplication.ui.main_event.EventDetailActivity
import se.hkr.myapplication.ui.main_event.EventViewModel
import se.hkr.myapplication.ui.main_event.OnEventItemClickListener
import se.hkr.myapplication.ui.models.Event


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var eventRecyclerView: RecyclerView
private lateinit var viewModel: EventViewModel
lateinit var adapter: EventAdapter


class HomeFragment : Fragment(), OnEventItemClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var eventList: ArrayList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventList = ArrayList()

        eventRecyclerView = view.findViewById(R.id.eventsView)
        eventRecyclerView.layoutManager = LinearLayoutManager(context)
        eventRecyclerView.setHasFixedSize(true)
        adapter = EventAdapter(eventList, this)
        eventRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[EventViewModel::class.java]
        viewModel.allEvents.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

    override fun onEventItemClick(item: Event, position: Int) {
        activity?.let {
            val intent = Intent(it, EventDetailActivity::class.java)
            intent.putExtra("Event Name", item.eventName)
            intent.putExtra("Event Location", item.eventLocation)
            intent.putExtra("Event date", item.start)
            intent.putExtra("Description", item.eventDescription)
            intent.putExtra("Map", item.map_link)
            intent.putExtra("image", item.downloadUrl)

            it.startActivity(intent)

        }
    }
}