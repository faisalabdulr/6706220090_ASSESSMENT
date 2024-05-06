package com.example.a6706220090_assesment2.navigation

import com.example.a6706220090_assesment2.ui.Screen.KEY_ID_MENU

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_MENU}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
}