package com.ngedev.thesisx.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngedev.thesisx.BuildConfig
import com.ngedev.thesisx.MainActivity
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivitySplashBinding
import com.ngedev.thesisx.domain.di.splashModule
import com.ngedev.thesisx.ui.welcome.WelcomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadKoinModules(splashModule)

        binding.versionName.text = "version ${BuildConfig.VERSION_NAME}"

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeUID()
        }, 1000L)
//        viewModel.clearLoanWhenIsDeleted().also {
//
//        }
    }

    private fun observeUID() {
        if(isUserAlreadyHere()) {

            startActivity(Intent(this@SplashActivity, MainActivity::class.java)).also {
                finish()
            }
        } else {
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java)).also {
                finish()
            }
        }
    }


    private fun isUserAlreadyHere(): Boolean {
        val auth = Firebase.auth
        val uid = auth.currentUser?.uid
        if (uid != null) {
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(splashModule)
    }
}