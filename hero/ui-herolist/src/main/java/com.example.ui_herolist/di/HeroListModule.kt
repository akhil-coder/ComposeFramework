package com.example.ui_herolist.di

import com.example.core.util.Logger
import com.example.hero_interactors.FilterHeroes
import com.example.hero_interactors.GetHeroes
import com.example.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideLogger(
     ): Logger {
        return Logger(
            tag = "HeroList",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeroes(
        interactors: HeroInteractors
    ): GetHeroes {
        return interactors.getHeroes
    }

    @Provides
    @Singleton
    fun provideFilterHeroes(
        interactors: HeroInteractors
    ): FilterHeroes {
        return interactors.filterHeroes
    }
}