package com.hasandeniz.reminderassistant.data

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {

    val readAllData: LiveData<List<Item>> = itemDao.readAllData()
    val readMondayData: LiveData<List<Item>> = itemDao.readMonday()
    val readTuesdayData: LiveData<List<Item>> = itemDao.readTuesday()
    val readWednesdayData: LiveData<List<Item>> = itemDao.readWednesday()
    val readThursdayData: LiveData<List<Item>> = itemDao.readThursday()
    val readFridayData: LiveData<List<Item>> = itemDao.readFriday()
    val readSaturdayData: LiveData<List<Item>> = itemDao.readSaturday()
    val readSundayData: LiveData<List<Item>> = itemDao.readSunday()


    suspend fun addItem(item: Item){
        itemDao.addItem(item)
    }
}