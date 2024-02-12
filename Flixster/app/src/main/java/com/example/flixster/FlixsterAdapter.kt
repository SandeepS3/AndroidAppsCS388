package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlixsterAdapter(
    private val books: List<Flixster>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<FlixsterAdapter.FlixsterViewHolder>() {

    inner class FlixsterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mItem: Flixster? = null
        val mBookTitle: TextView = itemView.findViewById(R.id.title)
        val mBookDescription: TextView = itemView.findViewById(R.id.description)
        val mBookImage: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlixsterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_flixster, parent, false)
        return FlixsterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlixsterViewHolder, position: Int) {
        val book = books[position]

        holder.mItem = book
        holder.mBookTitle.text = book.title
        holder.mBookDescription.text = book.description

        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500/"+book.bookImageUrl)
            .centerInside()
            .into(holder.mBookImage)

        holder.itemView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}
