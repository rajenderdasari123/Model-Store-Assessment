package com.example.modelstoreproject

import android.app.Application
import com.example.modelstoreproject.di.DaggerRetroComponent
import com.example.modelstoreproject.di.RetroComponent
import com.example.modelstoreproject.di.RetroModule

class MyApplication : Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()
        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }
}