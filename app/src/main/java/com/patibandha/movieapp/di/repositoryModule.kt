package com.patibandha.movieapp.di

import com.patibandha.movieapp.data.contract.HomeRepo
import com.patibandha.movieapp.data.repository.HomeRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single { HomeRepository(get(), get()) as HomeRepo }
}