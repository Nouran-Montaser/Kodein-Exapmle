package com.example.kodeinapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kodeinapplication.data.MainRepo
import com.example.kodeinapplication.data.Item

class MainViewModel(repository : MainRepo) : ViewModel() {

    private var repository: MainRepo? = null
    var items: MutableLiveData<List<Item>> = MutableLiveData()

    init {
        this.repository = repository
        items = repository.getData()
    }
}