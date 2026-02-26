package `is`.hi.hbv601g.icelandicweatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Data Access Object
 *
 * Used by ROOM to generate the database at compile time
 */
@Dao
interface AlertDao {
    @Query("SELECT * From alerts")
    suspend fun getAllAlerts(): List<AlertDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<AlertDto>)

    @Query("DELETE from alerts")
    suspend fun clearAlerts()
}