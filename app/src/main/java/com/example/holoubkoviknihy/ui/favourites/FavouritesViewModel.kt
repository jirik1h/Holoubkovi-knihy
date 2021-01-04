package com.example.holoubkoviknihy.ui.favourites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.android.trackmysleepquality.database.FavouriteBooks


class FavouritesViewModel(
    dataSource: BooksDatabaseDao,
    application: Application
) : ViewModel() {

    val database = dataSource

    val books = database.getAllBooks()

}
