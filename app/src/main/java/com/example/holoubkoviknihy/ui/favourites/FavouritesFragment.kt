package com.example.holoubkoviknihy.ui.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.example.android.trackmysleepquality.database.BooksDatabase
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.holoubkoviknihy.R
import com.example.holoubkoviknihy.adapters.FavouritesAdapter
import com.example.holoubkoviknihy.adapters.RecyclerViewAdapter
import com.example.holoubkoviknihy.databinding.FragmentFavouritesBinding
import com.example.holoubkoviknihy.model.Book
import kotlinx.android.synthetic.main.book_item.view.*
import java.util.*

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel

    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var myBooks: ArrayList<Book>
    private lateinit var myRecycleAdapter: RecyclerViewAdapter
    private lateinit var requestQueue: RequestQueue
    private lateinit var database: BooksDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favourites, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = BooksDatabase.getInstance(application).booksDatabaseDao
        val viewModelFactory = FavouritesViewModelFactory(dataSource, application)

        val favouritesViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(FavouritesViewModel::class.java)

        binding.favouritesViewModel = favouritesViewModel

        val adapter = FavouritesAdapter(dataSource)
        binding.recyclerViewFav.adapter = adapter

        favouritesViewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root

    }
}