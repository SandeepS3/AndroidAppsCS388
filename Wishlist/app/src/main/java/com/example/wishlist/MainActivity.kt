package com.example.wishlist

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.WishlistFetcher

class MainActivity : AppCompatActivity() {

    lateinit var items: MutableList<Wishlist>
    lateinit var adapter: ItemAdapter
    lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wishlistRv = findViewById<RecyclerView>(R.id.wishlistRv)
        items = WishlistFetcher.getItems().toMutableList()
        adapter = ItemAdapter(items)
        wishlistRv.adapter = adapter
        wishlistRv.layoutManager = LinearLayoutManager(this)

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val child = wishlistRv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = wishlistRv.getChildAdapterPosition(child)
                    WishlistFetcher.removeItem(position)
                    items.clear()
                    items.addAll(WishlistFetcher.getItems())
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            }
        })


        wishlistRv.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val linkEditText = findViewById<EditText>(R.id.linkEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val priceText = priceEditText.text.toString()
            val link = linkEditText.text.toString()

            if (name.isNotEmpty() && priceText.isNotEmpty() && link.isNotEmpty()) {
                try {
                    val price = priceText.toDouble()

                    if (price >= 0) {
                        WishlistFetcher.addItem(name, price, link)

                        items.clear()
                        items.addAll(WishlistFetcher.getItems())
                        adapter.notifyDataSetChanged()

                        nameEditText.text.clear()
                        priceEditText.text.clear()
                        linkEditText.text.clear()
                    } else {
                        Toast.makeText(this, "Price must be a valid positive number", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
