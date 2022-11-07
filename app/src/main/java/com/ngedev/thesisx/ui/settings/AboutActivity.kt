package com.ngedev.thesisx.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setAbout()
    }

    private fun setAbout() {
        binding.about.text = getString(R.string.desc_about)
    }
}