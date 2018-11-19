package de.fxnn.brainfapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RecentFile::class), version = 1)
abstract class BrainfappDatabase: RoomDatabase() {

    abstract fun recentFileDao(): RecentFileDao

    companion object {
        var name: String = "brainfapp"
    }

}