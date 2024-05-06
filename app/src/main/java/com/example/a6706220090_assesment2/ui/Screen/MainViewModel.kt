package com.example.a6706220090_assesment2.ui.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6706220090_assesment2.database.MenuDao
import com.example.a6706220090_assesment2.model.Menu
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

public class MainViewModel(dao: MenuDao) : ViewModel() {
    val data : StateFlow<List<Menu>> = dao.getMenu().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

}