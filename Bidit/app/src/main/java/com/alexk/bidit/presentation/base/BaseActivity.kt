package com.alexk.bidit.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val navControllerId: Int
) : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<B>(this, layoutId)
        val navHostFragment = supportFragmentManager.findFragmentById(navControllerId) as NavHostFragment
        navController = navHostFragment.navController
    }

    //현재 프래그먼트 찾기
    fun getCurrentFragment(): Fragment? {
        val currentFragmentContainer = supportFragmentManager.findFragmentById(navControllerId)
        val currentFragmentClassName =
            (navController.currentDestination as FragmentNavigator.Destination).className
        return currentFragmentContainer?.childFragmentManager?.fragments?.filterNotNull()?.find {
            it.javaClass.name == currentFragmentClassName
        }
    }
}