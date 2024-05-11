package com.app.chat.retrofitwithroomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertdata(product: List<Product>)

    @Query("SELECT * FROM product")
    suspend fun readalldata():List<Product>
}