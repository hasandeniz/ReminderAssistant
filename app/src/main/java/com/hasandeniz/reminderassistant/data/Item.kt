package com.hasandeniz.reminderassistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseName:String,
    val className:String,
    val startTime:String,
    val finishTime:String,
    val day:String,

)
