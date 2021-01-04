package com.example.holoubkoviknihy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.android.trackmysleepquality.database.FavouriteBooks
import com.example.holoubkoviknihy.R
import com.example.holoubkoviknihy.model.Book
import java.util.ArrayList

class RecyclerViewAdapter(private val myContext: Context, private val myData: List<Book>, private val database: BooksDatabaseDao, private val showButton: Boolean = true, private val onlyList: Boolean = false) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(myContext)
        view = inflater.inflate(R.layout.book_item, parent, false)
        val viewHolder = MyViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, i: Int) {
        val book = myData[i]
        holder.elTitle.text = book.title
        holder.elAuthor.text = book.authors
        holder.elCategory.text = book.categories

        if(!showButton) {
            holder.addButton.visibility = View.INVISIBLE;
        }else{
            holder.addButton.setOnClickListener {
                insertToFavourites(book)
                it.visibility = View.GONE
                holder.insertedText.visibility = View.VISIBLE
            }
        }
    }

    private fun insertToFavourites(book: Book){

        Thread {
            var row = FavouriteBooks()
            row.author = book.authors
            row.category = book.categories
            row.description = book.description
            row.name = book.title
            database.insert(row)
        }.start()
    }

    override fun getItemCount(): Int {
        return myData.size
    }

    class MyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var elTitle: TextView
        var elCategory: TextView
        var elAuthor: TextView
        var container: LinearLayout
        var addButton: Button
        var insertedText: TextView

        lateinit var database: BooksDatabaseDao

        init {
            elTitle = itemView.findViewById(R.id.name)
            elAuthor = itemView.findViewById(R.id.author)
            elCategory = itemView.findViewById(R.id.category)
            container = itemView.findViewById(R.id.container)
            addButton = itemView.findViewById(R.id.button_add_favourite)
            insertedText = itemView.findViewById(R.id.inserted_text)

        }
    }
}