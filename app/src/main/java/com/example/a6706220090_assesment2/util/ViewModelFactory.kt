package com.example.a6706220090_assesment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a6706220090_assesment2.database.MenuDao
import com.example.a6706220090_assesment2.ui.Screen.DetailViewModel
import com.example.a6706220090_assesment2.ui.Screen.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory (
    private val dao: MenuDao
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel:: class.java)){
            return MainViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}