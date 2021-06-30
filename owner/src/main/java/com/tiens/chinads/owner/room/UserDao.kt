package com.tiens.chinads.owner.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAll(): List<User>

    @Insert
    fun insert(user: User)

    @Delete
    fun del(user: User)
}