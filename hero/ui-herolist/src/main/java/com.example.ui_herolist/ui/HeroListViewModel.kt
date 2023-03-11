package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.DataState
import com.example.core.domain.UIComponent
import com.example.core.util.Logger
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter
import com.example.hero_interactors.FilterHeroes
import com.example.hero_interactors.GetHeroes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel
@Inject constructor(
    private val getHeroes: GetHeroes,
    private val filterHeroes: FilterHeroes,
    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeroes)
    }

    fun onTriggerEvent(event: HeroListEvents) {
        when (event) {
            is HeroListEvents.GetHeroes -> {
                getHeroes()
            }
            is HeroListEvents.FilterHeroes -> {
                filterHeroes()
            }
            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }

            is HeroListEvents.UpdateHeroFilter -> {
                updateHeroFilter(event.heroFilter)
            }

            is HeroListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }

            is HeroListEvents.UpdateAttributeFilter -> {
                updateAttributeFilter(event.attribute)
            }
        }
    }

    private fun updateAttributeFilter(attribute: HeroAttribute) {
        state.value = state.value.copy(primaryAttribute = attribute)
        filterHeroes()
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHeroes()
    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
    }

    private fun filterHeroes() {
        val filteredList = filterHeroes.execute(
            current = state.value.heroes,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            attributeFilter = state.value.primaryAttribute
        )
        state.value = state.value.copy(filteredHeroes = filteredList)
    }

    private fun getHeroes() {
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }

                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data ?: listOf())
                    filterHeroes()
                }

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }
}
