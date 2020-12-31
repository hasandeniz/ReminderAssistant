package com.hasandeniz.reminderassistant.data

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {


    val readMondayData: LiveData<List<Item>> = itemDao.readMonday()
    val readTuesdayData: LiveData<List<Item>> = itemDao.readTuesday()
    val readWednesdayData: LiveData<List<Item>> = itemDao.readWednesday()
    val readThursdayData: LiveData<List<Item>> = itemDao.readThursday()
    val readFridayData: LiveData<List<Item>> = itemDao.readFriday()
    val readSaturdayData: LiveData<List<Item>> = itemDao.readSaturday()
    val readSundayData: LiveData<List<Item>> = itemDao.readSunday()
    val getIdData: LiveData<List<Int>> = itemDao.getId()

    suspend fun addItem(item: Item){
        itemDao.addItem(item)
    }
    suspend fun deleteItem(item: Item){
        itemDao.deleteItem(item)
    }
    suspend fun updateItem(item: Item){
        itemDao.updateItem(item)
    }


}