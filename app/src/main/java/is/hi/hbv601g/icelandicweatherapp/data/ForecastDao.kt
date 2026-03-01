package `is`.hi.hbv601g.icelandicweatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *DAO for accessing forecast data stored locally
 */
@Dao
interface ForecastDao {

    /**
     * get all stored forcasts
     */
    @Query("SELECT * FROM forecasts ORDER BY time ASC")
    suspend fun getAllForecasts(): List<ForecastDto>

    /**
     * insert forcast entries
     * replace existing data when refreshing
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecast: List<ForecastDto>)

    /**
     * clear all stored
     */
    @Query("DELETE FROM forecasts")
    suspend fun clearForecasts()
}