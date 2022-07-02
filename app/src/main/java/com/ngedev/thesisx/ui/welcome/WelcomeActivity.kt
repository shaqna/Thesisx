package com.ngedev.thesisx.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngedev.thesisx.databinding.ActivityWelcomeBinding
import com.ngedev.thesisx.ui.auth.AuthActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener {
                Intent(this@WelcomeActivity, AuthActivity::class.java).apply {
                    putExtra(AuthActivity.FRAGMENT, AuthActivity.LOGIN_FRAGMENT)
                    startActivity(this)
                    finishAffinity()
                }
            }
            btnRegister.setOnClickListener {
                Intent(this@WelcomeActivity, AuthActivity::class.java).apply {
                    putExtra(AuthActivity.FRAGMENT, AuthActivity.REGISTER_FRAGMENT)
                    startActivity(this)
                    finishAffinity()
                }

            }
        }
    }
}