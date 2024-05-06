package com.example.a6706220090_assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Menu")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val makanan: String,
    val menu: String,
    val tanggal: String
)