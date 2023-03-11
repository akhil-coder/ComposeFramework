package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_domain.Hero
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroFromCache(
    private val cache: HeroCache,
) {
    fun execute(
        id: Int
    ): Flow<DataState<Hero>> = flow {

        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedHero =
                cache.getHero(id) ?: throw Exception("That hero doesn't exist in the cache")
            delay(3000)
            emit(DataState.Data(cachedHero))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Hero>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}