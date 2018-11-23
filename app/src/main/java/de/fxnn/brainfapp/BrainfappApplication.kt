package de.fxnn.brainfapp

import android.app.Activity
import android.app.Application
import androidx.room.Room

class BrainfappApplication: Application() {

    var database: BrainfappDatabase? = null

    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(this, BrainfappDatabase::class.java, BrainfappDatabase.name)
            .allowMainThreadQueries() // TODO: remove, and use LiveData, RxJava or similar
            .build()
    }

    companion object {
        fun database(activity: Activity) = (activity.application as BrainfappApplication).database!!
    }

}