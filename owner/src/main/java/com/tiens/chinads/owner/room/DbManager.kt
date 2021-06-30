package com.tiens.chinads.owner.room

import androidx.room.Room
import com.tiens.comonlibrary.application.BaseApplication

object DbManager {
    fun getDb() = Room.databaseBuilder(BaseApplication.getAppContext(),AppDatabase::class.java, "VShare").build()
}