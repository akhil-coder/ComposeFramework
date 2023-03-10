// Generated by Dagger (https://dagger.dev).
package com.example.ui_herolist.ui;

import androidx.lifecycle.SavedStateHandle;
import com.example.core.util.Logger;
import com.example.hero_interactors.FilterHeroes;
import com.example.hero_interactors.GetHeroes;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class HeroListViewModel_Factory implements Factory<HeroListViewModel> {
  private final Provider<GetHeroes> getHeroesProvider;

  private final Provider<FilterHeroes> filterHeroesProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<Logger> loggerProvider;

  public HeroListViewModel_Factory(Provider<GetHeroes> getHeroesProvider,
      Provider<FilterHeroes> filterHeroesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider, Provider<Logger> loggerProvider) {
    this.getHeroesProvider = getHeroesProvider;
    this.filterHeroesProvider = filterHeroesProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.loggerProvider = loggerProvider;
  }

  @Override
  public HeroListViewModel get() {
    return newInstance(getHeroesProvider.get(), filterHeroesProvider.get(), savedStateHandleProvider.get(), loggerProvider.get());
  }

  public static HeroListViewModel_Factory create(Provider<GetHeroes> getHeroesProvider,
      Provider<FilterHeroes> filterHeroesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider, Provider<Logger> loggerProvider) {
    return new HeroListViewModel_Factory(getHeroesProvider, filterHeroesProvider, savedStateHandleProvider, loggerProvider);
  }

  public static HeroListViewModel newInstance(GetHeroes getHeroes, FilterHeroes filterHeroes,
      SavedStateHandle savedStateHandle, Logger logger) {
    return new HeroListViewModel(getHeroes, filterHeroes, savedStateHandle, logger);
  }
}
