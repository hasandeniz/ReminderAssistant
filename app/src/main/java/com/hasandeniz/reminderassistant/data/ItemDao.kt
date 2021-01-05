package com.hasandeniz.reminderassistant.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.android.material.datepicker.SingleDateSelector


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: Item)

    @Query("SELECT * FROM item_table" )
    fun readAll(): LiveData<List<Item>>

    @Query("SELECT id FROM item_table ORDER BY id DESC LIMIT 1")
    fun getId(): LiveData<List<Int>>

    @Query("SELECT * FROM item_table WHERE day = 'Monday' ORDER BY startTime ASC")
    fun readMonday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Tuesday' ORDER BY startTime ASC")
    fun readTuesday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Wednesday' ORDER BY startTime ASC")
    fun readWednesday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Thursday' ORDER BY startTime ASC")
    fun readThursday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Friday' ORDER BY startTime ASC")
    fun readFriday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Saturday' ORDER BY startTime ASC")
    fun readSaturday(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE day = 'Sunday' ORDER BY startTime ASC")
    fun readSunday(): LiveData<List<Item>>

    @Delete
    suspend fun deleteItem(item:Item)

    @Update
    suspend fun updateItem(item: Item)

}