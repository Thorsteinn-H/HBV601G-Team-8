package `is`.hi.hbv601g.icelandicweatherapp.data
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert


@Dao
interface UserDao {
    @Query("SELECT * FROM userdto")
    fun getAll(): List<UserDto>

    @Query("SELECT * FROM userdto WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserDto>

    @Query("SELECT * FROM userdto WHERE name LIKE :nameSearch")
    fun findByName(nameSearch: String): UserDto

    @Query("SELECT * FROM userdto WHERE email LIKE :emailSearch LIMIT 1")
    fun findByEmail(emailSearch: String): UserDto

    @Insert
    fun insertAll(vararg users: UserDto)

    @Delete
    fun delete(user: UserDto)
}