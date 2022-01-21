package com.example.loginfinale.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginfinale.R
import com.example.loginfinale.model.Book

class BooksAdapter(private val context: Context, private val books: ArrayList<Book>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView = view.findViewById(R.id.bookTitle)
        val description: TextView = view.findViewById(R.id.bookDescription)
        val image: ImageView = view.findViewById(R.id.bookImage)

        private val item: RelativeLayout = view.findViewById(R.id.bookItem)

        init {
            item.findViewById<ImageView>(R.id.addToCart).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCartClick(position)
                }
            }

            item.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = books[position]

        holder.title.text = currentBook.title
        holder.description.text = currentBook.description

        Glide.with(context)
            .load(currentBook.imageUrl)
            .into(holder.image)

    }

    override fun getItemCount() = books.size

    interface OnItemClickListener {
        fun onCartClick(position: Int)
        fun onItemClick(position: Int)
    }

}