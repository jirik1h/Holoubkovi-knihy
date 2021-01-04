package com.example.holoubkoviknihy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.android.trackmysleepquality.database.BooksDatabaseDao
import com.example.android.trackmysleepquality.database.FavouriteBooks
import com.example.holoubkoviknihy.MainActivity
import com.example.holoubkoviknihy.R
import com.example.holoubkoviknihy.model.Book

class RecyclerViewAdapter(private val mContext: Context, private val mData: List<Book>, private val database: BooksDatabaseDao, private val showButton: Boolean = true) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private val options: RequestOptions


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(R.layout.book_item, parent, false)
        val viewHolder = MyViewHolder(view)

        viewHolder.container.setOnClickListener {
            val i = Intent(mContext, MainActivity::class.java)
            val pos = viewHolder.adapterPosition

            i.putExtra("book_title", mData[pos].title)
            i.putExtra("book_author", mData[pos].authors)
            i.putExtra("book_desc", mData[pos].description)
            i.putExtra("book_categories", mData[pos].categories)
            mContext.startActivity(i)
        }
        return viewHolder
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, i: Int) {
        val book = mData[i]
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.authors
        holder.tvCategory.text = book.categories

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
        return mData.size
    }

    class MyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvCategory: TextView
        var tvAuthor: TextView
        var container: LinearLayout
        var addButton: Button
        var insertedText: TextView

        lateinit var database: BooksDatabaseDao

        init {
            tvTitle = itemView.findViewById(R.id.name)
            tvAuthor = itemView.findViewById(R.id.author)
            tvCategory = itemView.findViewById(R.id.category)
            container = itemView.findViewById(R.id.container)
            addButton = itemView.findViewById(R.id.button_add_favourite)
            insertedText = itemView.findViewById(R.id.inserted_text)

        }
    }

    init {

        //Request option for Glide
        options = RequestOptions().centerCrop().placeholder(R.drawable.loading_shape)
            .error(R.drawable.loading_shape)
    }


}