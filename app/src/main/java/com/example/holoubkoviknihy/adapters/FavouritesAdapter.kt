package com.example.holoubkoviknihy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.android.trackmysleepquality.database.FavouriteBooks
import com.example.holoubkoviknihy.R

class FavouritesAdapter: RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

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
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
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

        fun bind(item: FavouriteBooks) {
            elTitle.text = item.name
            elCategory.text = item.category
            elAuthor.text = item.author
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.book_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}