package com.example.kodeinapplication.helper

import android.app.Application
import com.example.kodeinapplication.data.MainRepo
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MyApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("http://scripting.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        bind<AppViewModelFactory>() with singleton { AppViewModelFactory(kodein) }
        bind<MainRepo>() with singleton {
            MainRepo(
                kodein
            )
        }
    }


    fun AppModule(mApplication: MyApplication) = Kodein.Module {

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("http://scripting.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        bind<AppViewModelFactory>() with singleton { AppViewModelFactory(kodein) }
    }
}