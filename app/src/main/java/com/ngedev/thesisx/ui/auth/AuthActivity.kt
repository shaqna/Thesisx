package com.ngedev.thesisx.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityAuthBinding
import com.ngedev.thesisx.ui.auth.login.LoginFragment
import com.ngedev.thesisx.ui.auth.register.RegisterFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    companion object {
        const val FRAGMENT = "fragment"
        const val REGISTER_FRAGMENT = "register_fragment"
        const val LOGIN_FRAGMENT = "login_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {

            when(intent.getStringExtra(FRAGMENT)) {
                REGISTER_FRAGMENT -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<RegisterFragment>(R.id.fragment_container_view)
                    }
                }
                LOGIN_FRAGMENT -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<LoginFragment>(R.id.fragment_container_view)
                    }
                }

            }

        }
        //setFragment()
    }

    private fun setFragment() {
        val mFragmentManager = supportFragmentManager
        val mRegisterFragment = RegisterFragment()
        val fragment = mFragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)

        if (fragment !is RegisterFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view, mRegisterFragment, RegisterFragment::class.java.simpleName)
                .commit()
        }
    }
}