package de.fxnn.brainfapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentFileDao {

    @Query("SELECT * FROM RecentFile")
    fun getAll(): List<RecentFile>

    @Query("SELECT * FROM RecentFile WHERE fileName LIKE :fileName")
    fun findByFileName(fileName: String): List<RecentFile>

    @Insert
    fun insert(entity: RecentFile)

    @Update
    fun update(entity: RecentFile)

}