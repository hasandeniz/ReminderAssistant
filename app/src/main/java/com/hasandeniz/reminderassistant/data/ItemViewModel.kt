package com.hasandeniz.reminderassistant.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ItemViewModel(application: Application): AndroidViewModel(application) {

    val readMondayData: LiveData<List<Item>>
    val readTuesdayData: LiveData<List<Item>>
    val readWednesdayData: LiveData<List<Item>>
    val readThursdayData: LiveData<List<Item>>
    val readFridayData: LiveData<List<Item>>
    val readSaturdayData: LiveData<List<Item>>
    val readSundayData: LiveData<List<Item>>
    val getIdData: LiveData<List<Int>>
    private val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        readMondayData = repository.readMondayData
        readTuesdayData = repository.readTuesdayData
        readWednesdayData = repository.readWednesdayData
        readThursdayData = repository.readThursdayData
        readFridayData = repository.readFridayData
        readSaturdayData = repository.readSaturdayData
        readSundayData = repository.readSundayData
        getIdData = repository.getIdData
    }

    fun addItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }
    fun updateItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(item)
        }
    }

    }
