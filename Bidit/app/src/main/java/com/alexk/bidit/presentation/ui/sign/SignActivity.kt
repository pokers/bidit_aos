package com.alexk.bidit.presentation.ui.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.alexk.bidit.R
import com.alexk.bidit.databinding.ActivityMainBinding
import com.alexk.bidit.presentation.base.BaseActivity
import com.alexk.bidit.presentation.ui.sign.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

class SignActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_sign, R.id.nav_sign_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is LoginFragment) {
            finishAffinity()
            return
        }

        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}