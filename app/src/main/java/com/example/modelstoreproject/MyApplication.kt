package com.example.modelstoreproject

import android.app.Application
import com.example.modelstoreproject.di.DaggerRetroComponent
import com.example.modelstoreproject.di.RetroComponent
import com.example.modelstoreproject.di.RetroModule

/**
 * The class for initializing components required at Model store Application Level.
 */
class MyApplication : Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()
        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    /**
     * getters for retrocomponent
     */
    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }
}