package se.hkr.myapplication.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import se.hkr.myapplication.R
import se.hkr.myapplication.tabs.HomePageActivity
import se.hkr.myapplication.ui.home.HomeFragment


class OptionsActivity : AppCompatActivity() {


    private lateinit var viewEvents: Button
    private lateinit var makeEvents: Button


    @SuppressLint("MissingInflatedId", "SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        viewEvents = findViewById(R.id.view_event)
        makeEvents = findViewById(R.id.make_event)


        viewEvents.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        makeEvents.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

    }

    }

