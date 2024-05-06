package com.example.a6706220090_assesment2.ui.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6706220090_assesment2.database.MenuDao
import com.example.a6706220090_assesment2.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: MenuDao) : ViewModel(){
    fun insert(nama: String, harga: Float, kategori: String){
        val menu = Menu(
            nama = nama,
            harga = harga,
            kategori = kategori
        )

        viewModelScope.launch(Dispatchers.IO) { dao.insert(menu) }
    }

    suspend fun getMenu(id: Long): Menu? {
        return dao.getMenuById(id)
    }

    fun update(id: Long, nama: String, harga: Float, kategori: String){
        val menu = Menu(
            id = id,
            nama = nama,
            harga = harga,
            kategori = kategori
        )

        viewModelScope.launch(Dispatchers.IO) { dao.update(menu) }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) { dao.deleteById(id) }
    }
}
