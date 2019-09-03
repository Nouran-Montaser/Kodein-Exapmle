package com.example.kodeinapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import androidx.lifecycle.Observer
import com.example.kodeinapplication.R
import com.example.kodeinapplication.helper.AppViewModelFactory
import org.kodein.di.android.closestKodein

class MainActivity :  AppCompatActivity() ,KodeinAware{

    override val kodein by closestKodein()
    private var viewModel: MainViewModel?=null
    val viewModelFactory: AppViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = viewModelFactory.create(MainViewModel::class.java)

        viewModel?.items?.observe(this, Observer { items ->
            Log.i("test items : ", items.toString())
        })
    }
}