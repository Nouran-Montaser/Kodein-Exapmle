package com.example.kodeinapplication.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kodeinapplication.data.MainRepo
import com.example.kodeinapplication.view.MainViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class AppViewModelFactory(override val kodein: Kodein) : ViewModelProvider.NewInstanceFactory(),KodeinAware{

    private val repo: MainRepo by kodein.instance()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(repo) as T
            }
        }
        throw IllegalArgumentException("Unknown view model class: " + modelClass.name)
    }
}