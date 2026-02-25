package `is`.hi.hbv601g.icelandicweatherapp

import android.app.Application
import androidx.room.Room
import `is`.hi.hbv601g.icelandicweatherapp.data.AppDatabase

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,"userdb"
        ).build()
    }

}