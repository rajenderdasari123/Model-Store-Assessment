package com.example.modelstoreproject.di

import com.example.modelstoreproject.ModelViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {

    fun inject(modelViewModel: ModelViewModel)

}