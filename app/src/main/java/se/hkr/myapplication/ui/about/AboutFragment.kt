package se.hkr.myapplication.ui.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import se.hkr.myapplication.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button2.setOnClickListener {
            try {
                val uri = Uri.parse("https://www.linkedin.com/company/softhouse")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }

        binding.button3.setOnClickListener {
            try {
                val uri = Uri.parse("https://www.softhouse.se/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }

        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}