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

        // Lookup the RecyclerView in activity layout
        val wishlistRv = findViewById<RecyclerView>(R.id.wishlistRv)
        // Fetch the initial list of wishlist items
        items = WishlistFetcher.getItems().toMutableList()
        // Create adapter passing in the list of wishlist items
        adapter = ItemAdapter(items)
        // Attach the adapter to the RecyclerView to populate items
        wishlistRv.adapter = adapter
        // Set layout manager to position the items
        wishlistRv.layoutManager = LinearLayoutManager(this)

        // Initialize GestureDetector
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                // Find the long-pressed item position
                val child = wishlistRv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = wishlistRv.getChildAdapterPosition(child)
                    // Remove the item at the long-pressed position
                    WishlistFetcher.removeItem(position)
                    // Update the RecyclerView by fetching the updated items
                    items.clear()
                    items.addAll(WishlistFetcher.getItems())
                    adapter.notifyDataSetChanged()
                    // Show a Toast message indicating item deletion
                    Toast.makeText(this@MainActivity, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Set a touch listener for the RecyclerView items
        wishlistRv.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Pass touch events to GestureDetector
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                // Handle touch events if needed
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                // Handle request disallowing intercept touch events if needed
            }
        })

        // Lookup the input fields and submit button
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val linkEditText = findViewById<EditText>(R.id.linkEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Set a click listener for the submit button
        submitButton.setOnClickListener {
            // Get input values from the EditText fields
            val name = nameEditText.text.toString()
            val priceText = priceEditText.text.toString()
            val link = linkEditText.text.toString()

            // Validate input
            if (name.isNotEmpty() && priceText.isNotEmpty() && link.isNotEmpty()) {
                try {
                    // Convert the price to a Double
                    val price = priceText.toDouble()

                    // Check if the price is a valid positive value
                    if (price >= 0) {
                        // Add a new item using the addItem function from WishlistFetcher
                        WishlistFetcher.addItem(name, price, link)

                        // Update the RecyclerView by fetching the updated items
                        items.clear()
                        items.addAll(WishlistFetcher.getItems())
                        adapter.notifyDataSetChanged()

                        // Clear the input fields
                        nameEditText.text.clear()
                        priceEditText.text.clear()
                        linkEditText.text.clear()
                    } else {
                        // Show a Toast error for invalid price
                        Toast.makeText(this, "Price must be a valid positive number", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    // Show a Toast error for invalid price format
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show a Toast error for incomplete input
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
