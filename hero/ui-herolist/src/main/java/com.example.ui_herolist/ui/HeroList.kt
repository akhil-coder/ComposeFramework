package com.example.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import com.example.components.DefaultScreenUI
import com.example.core.domain.UIComponentState
import com.example.ui_herolist.components.HeroListFilter
import com.example.ui_herolist.components.HeroListToolbar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit
) {
    DefaultScreenUI(
        progressBarState = state.progressBarState
    ) {
        val name = remember { mutableStateOf("") }
        Column {
            HeroListToolbar(heroName = state.heroName,
                onHeroNameChanged = { heroName -> events(HeroListEvents.UpdateHeroName(heroName)) },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeroes)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                })
            LazyColumn {
                items(state.filteredHeroes) { hero ->
                    HeroListItem(
                        hero = hero, onSelectHero = { heroId ->
                            navigateToDetailScreen(heroId)
                        }, imageLoader = imageLoader
                    )
                }
            }
            if (state.filterDialogState is UIComponentState.Show) {
                HeroListFilter(
                    heroFilter = state.heroFilter,
                    onUpdateHeroFilter = { heroFilter ->
                        events(HeroListEvents.UpdateHeroFilter(heroFilter))
                    },
                    attributeFilter = state.primaryAttribute,
                    onUpdateAttributeFilter = { heroAttribute ->
                        events(HeroListEvents.UpdateAttributeFilter(heroAttribute))
                    },
                    onCloseDialog = {
                        events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                    }
                )
            }
        }
    }
}