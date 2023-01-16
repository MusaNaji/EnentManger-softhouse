package se.hkr.myapplication.admin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import se.hkr.myapplication.R
import se.hkr.myapplication.tabs.HomePageActivity
import se.hkr.myapplication.ui.models.Events
import java.util.*


class AdminActivity : AppCompatActivity() {

    private lateinit var authenticator: FirebaseAuth
    private lateinit var eventNameEdit: EditText
    private lateinit var eventLocationEdit: EditText
    private lateinit var spinnerBranch: Spinner
    private lateinit var description: EditText
    private lateinit var map: EditText
    private lateinit var firebase: DatabaseReference
    private lateinit var uploadImg: Button
    private lateinit var startDate: EditText
    private lateinit var endDate: EditText
    private val GALLERY_REQUEST_CODE = 71
    private var downloadUrl: String = ""


    @SuppressLint("MissingInflatedId", "SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        eventNameEdit = findViewById(R.id.editTextTextPersonName)
        eventLocationEdit = findViewById(R.id.editTextTextPersonName3)
        description = findViewById(R.id.editTextTextPersonName2)
        uploadImg = findViewById(R.id.button4)
        startDate = findViewById(R.id.idTVDate)
        endDate = findViewById(R.id.idTVDate2)
        map = findViewById(R.id.map_link)

        DateInputMask(startDate)
        DateInputMask(endDate)

        authenticator = FirebaseAuth.getInstance()

        //drop down menu
        spinnerBranch = findViewById(R.id.spinner)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.branches,
                R.layout.spinner_colored
            )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinnerBranch.adapter = adapter


        firebase = FirebaseDatabase.getInstance().reference
        findViewById<Button>(R.id.button2).setOnClickListener {
            saveEventData()
        }
        uploadImg.setOnClickListener {
            selectImageFromGallery()
        }

        //dropdown menu color
        spinnerBranch.background.setColorFilter(
            resources.getColor(R.color.light_blue),
            PorterDuff.Mode.SRC_ATOP
        )
        spinnerBranch.solidColor

    }

    //intent to pick photo from mobile gallery.
    private fun selectImageFromGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Please select..."
            ),
            GALLERY_REQUEST_CODE
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            // Get the Uri of data
            val file_uri = data.data
            if (file_uri != null) {
                uploadImageToFirebase(file_uri)
                Toast.makeText(this, "Image successfully uploaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //upload to firebase storage
    private fun uploadImageToFirebase(fileUri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"

        val database = FirebaseDatabase.getInstance()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")
        refStorage.putFile(fileUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    downloadUrl = it.toString()
                    Log.i("link", downloadUrl)
                }
            }

            .addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

            }
    }

    // save user input to firebase.
    private fun saveEventData() {
        val downloadUrl = downloadUrl
        val eventName = eventNameEdit.text.toString()
        val branchSelected = spinnerBranch.selectedItem.toString()
        val eventLocation = eventLocationEdit.text.toString()
        val start = startDate.text.toString()
        val end = endDate.text.toString()
        val map_link = map.text.toString()
        val eventDescription = description.text.toString()


        if (eventName.isEmpty()) {
            eventNameEdit.error = "Event name can not be empty"
        }
        if (eventLocation.isEmpty()) {
            eventLocationEdit.error = "Event location can not be empty"
        }
        if (eventDescription.isEmpty()) {
            description.error = "Event description can not be empty"
        }

        if (start.isEmpty()) {
            startDate.error = "Event start date can not be empty"
        }
        if (end.isEmpty()) {
            endDate.error = "Event end date can not be empty"

        }

        val eventId = firebase.push().key!!
        if (TextUtils.isEmpty(eventName) || TextUtils.isEmpty(eventLocation) ||
            TextUtils.isEmpty(eventDescription) ||
            TextUtils.isEmpty(start) || TextUtils.isEmpty(end) ||
            TextUtils.isEmpty(eventLocation) || TextUtils.isEmpty(
                eventDescription
            )
        ) {
            Toast.makeText(this, "Check error message", Toast.LENGTH_SHORT).show()
        } else {

            val events = Events(
                eventId,
                eventName,
                branchSelected,
                eventLocation,
                eventDescription,
                downloadUrl,
                start,
                end,
                map_link,
            )

            firebase.child(eventId).setValue(events)
                .addOnCompleteListener {
                    Toast.makeText(this, "Event successfully added", Toast.LENGTH_SHORT).show()

                    eventNameEdit.text.clear()
                    eventLocationEdit.text.clear()
                    description.text.clear()
                    startDate.text.clear()
                    endDate.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, OptionsActivity::class.java)
        startActivity(intent)
    }

}