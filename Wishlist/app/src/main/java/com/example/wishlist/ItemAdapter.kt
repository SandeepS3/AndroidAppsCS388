package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Wishlist>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTv)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTv)
        val linkTextView: TextView = itemView.findViewById(R.id.linkTv)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = items[position]

                    // Open the link in a new tab
                    openLinkInNewTab(itemView.context, clickedItem.link)
                }
            }
        }

        private fun openLinkInNewTab(context: Context, url: String) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                ContextCompat.startActivity(context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Invalid URL: $url", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price.toString()
        holder.linkTextView.text = currentItem.link
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
