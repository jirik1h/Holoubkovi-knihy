package com.example.holoubkoviknihy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.BooksDatabase
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.android.trackmysleepquality.database.FavouriteBooks
import com.example.holoubkoviknihy.R

class FavouritesAdapter(private var database: BooksDatabaseDao): RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    var data =  listOf<FavouriteBooks>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, database)
    }

    class ViewHolder private constructor(itemView: View, dat: BooksDatabaseDao) : RecyclerView.ViewHolder(itemView){
        var elTitle: TextView
        var elCategory: TextView
        var elAuthor: TextView
        var container: LinearLayout
        var addButton: Button
        var insertedText: TextView
        var removeButton: Button

        var database: BooksDatabaseDao

        init {
            elTitle = itemView.findViewById(R.id.name)
            elAuthor = itemView.findViewById(R.id.author)
            elCategory = itemView.findViewById(R.id.category)
            container = itemView.findViewById(R.id.container)
            addButton = itemView.findViewById(R.id.button_add_favourite)
            insertedText = itemView.findViewById(R.id.inserted_text)
            removeButton = itemView.findViewById(R.id.button_remove_favourite)
            database = dat

        }

        fun bind(item: FavouriteBooks) {
            elTitle.text = item.name
            elCategory.text = item.category
            elAuthor.text = item.author

            addButton.visibility = View.GONE
            removeButton.visibility = View.VISIBLE

            removeButton.setOnClickListener { removeFormDb(item.id) }
        }

        private fun removeFormDb(id: Long){
            Thread {
                database.clearRow(id)
            }.start()
        }

        companion object {
            fun from(parent: ViewGroup, database: BooksDatabaseDao): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.book_item, parent, false)

                return ViewHolder(view, database)
            }
        }
    }
}