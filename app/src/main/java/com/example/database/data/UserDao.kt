package com.example.database.data
import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
//EN ACA COLOCAREMOS EL SELECT EL INSERT UPDATE Y DELETE
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Insert//esto es el insert en base de datos
    suspend fun insertarUsuario(user: User)

    @Delete
    suspend fun eliminarUser(user: User)

    @Update
    suspend fun actualizarUser(user: User)

}