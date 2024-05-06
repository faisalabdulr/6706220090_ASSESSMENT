package com.example.a6706220090_assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a6706220090_assesment2.model.Menu

@Database(entities = [Menu::class], version = 1, exportSchema = false)
abstract class MenuDb : RoomDatabase() {

    abstract  val dao: MenuDao

    companion object{
        @Volatile
        private var INSTANCE: MenuDb? = null

        fun getInstace(context: Context): MenuDb {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MenuDb::class.java,
                        "menu.db"
                    ).build()
                    INSTANCE = instance

                }
                return instance
            }

        }
    }
}