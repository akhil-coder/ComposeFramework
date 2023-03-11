package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.network.HeroService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val cache: HeroCache,
    private val service: HeroService
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heroes: List<Hero> = try { // catch network exceptions
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
                listOf()
            }

            // cache the network data
            cache.insert(heroes)
            // emit data from cache
            val cachedHeroes = cache.selectAll()

            emit(DataState.Data(cachedHeroes))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
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