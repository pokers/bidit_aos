package com.alexk.bidit.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

class NavigationViewModel : ViewModel() {
    val popBackStack = MutableLiveData<Boolean>()
    val navDirectionAction = MutableLiveData<NavDirections>()
}