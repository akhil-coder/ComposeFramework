package com.example.hero_interactors

import com.example.core.domain.FilterOrder
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter

class FilterHeroes {

    fun execute(
        current: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero> {
        var filteredList: MutableList<Hero> = current.filter {
            it.localizedName.lowercase().contains(heroName.lowercase())
        }.toMutableList()

        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending { it.localizedName }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy { it.localizedName }
                    }
                }
            }

            is HeroFilter.ProWins -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending {
                            getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                        }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy {
                            getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                        }
                    }
                }
            }
        }
        when (attributeFilter) {
            is HeroAttribute.Strength -> {
                filteredList = filteredList.filter {
                    it.primaryAttribute is HeroAttribute.Strength
                }.toMutableList()
            }

            is HeroAttribute.Agility -> {
                filteredList = filteredList.filter {
                    it.primaryAttribute is HeroAttribute.Agility
                }.toMutableList()
            }

            is HeroAttribute.Intelligence -> {
                filteredList = filteredList.filter {
                    it.primaryAttribute is HeroAttribute.Strength
                }.toMutableList()
            }

            is HeroAttribute.Unknown -> {
                //  Don't filter
            }

        }
        return filteredList
    }

    private fun getWinRate(proWins: Double, proPick: Double): Int {
        val winRate: Int =
            if (proPick <= 0) {
                0
            } else {
                (proWins.toDouble() / proPick.toDouble() * 100).toInt()
            }
        return winRate
    }
}