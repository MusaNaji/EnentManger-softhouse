package se.hkr.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import se.hkr.myapplication.admin.AdminActivity
import se.hkr.myapplication.admin.OptionsActivity
import se.hkr.myapplication.tabs.HomePageActivity


class MainActivity : AppCompatActivity() {

    private lateinit var authenticator: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInButton: SignInButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backgroundVideo()

        authenticator = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton = findViewById(R.id.signInButton)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener{
            googleSignIn()
        }


    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            } else {
                Toast.makeText(this, "bad", Toast.LENGTH_SHORT).show()
            }

        }


    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            val email = account!!.email
            val split: List<String>? = email?.split("@")
            val domain = split?.get(1) //This Will Give You The Domain After '@'
            if (domain == "gmail.com" ) {
                if (email == "fizzazeeshan123@gmail.com"){
                    adminSignIn(account)

                }
                else {
                    //Proceed Ahead.
                    updateUI(account)
                    //Show User Warning UI.
                    //Toast.makeText(this, "Unauthorized account", Toast.LENGTH_SHORT).show()
                }


            }else {
                //Show User Warning UI.
                Toast.makeText(this, "Unauthorized account", Toast.LENGTH_SHORT).show()
            }
            ////////issue

        } else {
            Toast.makeText(this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        authenticator.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, HomePageActivity::class.java)
                intent.putExtra("email", account.email)
                intent.putExtra("email", account.displayName)
                startActivity(intent)
            }
        }

    }

    private fun adminSignIn(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        authenticator.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent = Intent(this, OptionsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        backgroundVideo()
    }

    private fun backgroundVideo() {
        val videoView = findViewById<VideoView>(R.id.video_view)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.background_video)
        videoView.setVideoURI(uri)
        videoView.start()
        videoView.setOnCompletionListener {
            videoView.start()
        }
    }

}
