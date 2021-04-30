package com.example.registeruser.di

import com.example.registeruser.viewmodel.InfoViewModel
import com.example.registeruser.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
val apiModule = module {
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        InfoViewModel(get())
    }
}