package com.example.a6706220090_assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
class Menu (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val harga: Float,
    val kategori: String,
)
