package se.hkr.myapplication.ui.main_event

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import se.hkr.myapplication.R

class EventDetailActivity : AppCompatActivity() {

    private lateinit var map_links: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val eventName = intent.getStringExtra("Event Name")
        val eventLocation = intent.getStringExtra("Event Location")
        val startDate = intent.getStringExtra("Event date")
        val description = intent.getStringExtra("Description")
        val map_link = intent.getStringExtra("Map")

        findViewById<TextView>(R.id.name_tv).text = eventName
        findViewById<TextView>(R.id.location_tv).text = eventLocation
        findViewById<TextView>(R.id.start_tv).text = startDate
        findViewById<TextView>(R.id.description_tv).text = description
        findViewById<TextView>(R.id.map_tv).text = map_link

        //post the image on imageview of details activity.
        val imageView = findViewById<ImageView>(R.id.imageView)
        val link = intent.getStringExtra("image")
        Picasso.get().load(link).fit().centerCrop().into(imageView)
        map_links = findViewById(R.id.map_tv)
        map_links.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(description.toString()))
            startActivity(urlIntent)

        }

    }

}


