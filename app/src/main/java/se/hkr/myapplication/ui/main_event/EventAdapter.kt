package se.hkr.myapplication.ui.main_event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import se.hkr.myapplication.R
import se.hkr.myapplication.ui.models.Event


class EventAdapter(
    private var eventList: ArrayList<Event>,
    var clickListener: OnEventItemClickListener
) : RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.event_item,
            parent, false
        )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initialize(eventList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(eventList: List<Event>) {
        this.eventList.clear()
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.eventNameTv)
        val eventLocation: TextView = itemView.findViewById(R.id.eventLocationTv)
        val start: TextView = itemView.findViewById(R.id.start)
        val end: TextView = itemView.findViewById(R.id.end)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val eventDescription: TextView? = null
        val map_link: TextView? = null
        var link = ""

        fun initialize(item: Event, action: OnEventItemClickListener) {

            eventName.text = item.eventName
            eventLocation.text = item.eventLocation
            start.text = item.start
            end.text = item.end
            map_link?.text ?: item.map_link
            eventDescription?.text ?: item.eventDescription
            link = item.downloadUrl.toString()
            Picasso.get().load(link).fit().centerCrop().into(imageView)

            itemView.setOnClickListener {
                action.onEventItemClick(item, adapterPosition)
            }

        }

    }
}
