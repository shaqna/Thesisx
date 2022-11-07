package com.ngedev.thesisx.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngedev.thesisx.databinding.ActivityAdditionalSettingsBinding
import com.ngedev.thesisx.ui.auth.login.ResetPasswordActivity
import com.ngedev.thesisx.utils.ExtraName.EMAIL
import com.ngedev.thesisx.utils.ExtraName.USERNAME

class AdditionalSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdditionalSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdditionalSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.changeUsernameLayout.setOnClickListener {
            startActivity(
                Intent(this@AdditionalSettingsActivity, EditUsernameActivity::class.java).putExtra(
                    USERNAME, intent.getStringExtra(
                        USERNAME
                    )
                )
            )
        }
        binding.resetPasswordLayout.setOnClickListener {
            startActivity(
                Intent(this@AdditionalSettingsActivity, ResetPasswordActivity::class.java).putExtra(
                    EMAIL, intent.getStringExtra(
                        EMAIL
                    )
                )
            )
        }
    }
}