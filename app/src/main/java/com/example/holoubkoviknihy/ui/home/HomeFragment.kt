package com.example.holoubkoviknihy.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.android.trackmysleepquality.database.BooksDatabase
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.holoubkoviknihy.R
import com.example.holoubkoviknihy.adapters.RecyclerViewAdapter
import com.example.holoubkoviknihy.model.Book
import org.json.JSONException
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var myBooks: ArrayList<Book>
    private lateinit var myRecycleAdapter: RecyclerViewAdapter
    private lateinit var requestQueue: RequestQueue

    private val GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q="

    private lateinit var search_edit_text: EditText
    private lateinit var search_button: Button

    private lateinit var database: BooksDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        database = BooksDatabase.getInstance(application).booksDatabaseDao

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        search_edit_text = root.findViewById<EditText>(R.id.search_box)
        search_button = root.findViewById<Button>(R.id.search_buttton)

        booksRecyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        booksRecyclerView.setHasFixedSize(true)
        booksRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        myBooks = ArrayList<Book>()
        requestQueue = Volley.newRequestQueue(activity)


        search_button.setOnClickListener(View.OnClickListener {
            myBooks.clear()
            search()
        })

        return root
    }

    private fun search() {
        val search_query: String = search_edit_text.getText().toString()

        val final_query = search_query.replace(" ", "+")
        val uri = Uri.parse(GOOGLE_API_URL + final_query)
        val buider = uri.buildUpon()
        parseJson(buider.toString())

    }

    private fun parseJson(key: String) {
        val request = JsonObjectRequest(
            Request.Method.GET, key, null,
            { response ->
                var title = ""
                var author = ""
                var description = "No Description"
                var categories = "No categories Available "
                try {
                    val items = response.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val volumeInfo = item.getJSONObject("volumeInfo")
                        try {
                            title = volumeInfo.getString("title")
                            val authors = volumeInfo.getJSONArray("authors")
                            author = if (authors.length() == 1) {
                                authors.getString(0)
                            } else {
                                authors.getString(0) + "|" + authors.getString(1)
                            }
                            description = volumeInfo.getString("description")
                            categories = volumeInfo.getJSONArray("categories").getString(0)
                        } catch (e: Exception) {
                        }
                        myBooks.add(
                            Book(
                                title,
                                author,
                                description,
                                categories
                            )
                        )
                        myRecycleAdapter = context?.let { RecyclerViewAdapter(it, myBooks, database) }!!
                        booksRecyclerView.adapter = myRecycleAdapter
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("TAG", e.toString())
                }
            }) { error -> error.printStackTrace() }
        requestQueue.add(request)
    }
}