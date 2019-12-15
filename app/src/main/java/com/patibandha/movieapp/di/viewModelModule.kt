package com.patibandha.movieapp.di

import com.patibandha.movieapp.presentation.home.MovieListingViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MovieListingViewModel(get()) }
}