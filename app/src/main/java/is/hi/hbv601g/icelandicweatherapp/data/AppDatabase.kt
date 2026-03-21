package `is`.hi.hbv601g.icelandicweatherapp.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room database for the application
 *
 * Stores all local persistent data used by the app
 *
 * Entities define the databse tables
 */
@Database(
    entities = [
        UserDto::class,
        AlertDto::class,
        ForecastDto::class],
    version = 4) // schema version, must increase when schema changes
abstract class AppDatabase : RoomDatabase() {

    // provides access to UserDao
    abstract fun getUserDao(): UserDao
    // provides access to AlertDao
    abstract fun getAlertDao(): AlertDao
    // Provides access to ForecastDao
    abstract fun getForecastDao(): ForecastDao

    companion object {
        /**
         * Volatile ensures the Instance value is always up to date
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Rturns the instance of the database
         *
         * ensures that only one database instance exist
         *
         * uses synchronized block to prevent threads from creating multible database instances
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                //build the Room database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext, // to avoid memory leaks
                    AppDatabase::class.java, // the class
                    "userdb" // file name
                )
                    .fallbackToDestructiveMigration()
                    .build()

                // store so it can be reused
                INSTANCE = instance
                instance
            }
        }
    }

}
