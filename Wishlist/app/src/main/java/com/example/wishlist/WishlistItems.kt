package com.example.wishlist

class WishlistFetcher {
    companion object {
        val names: MutableList<String> = mutableListOf()
        val prices: MutableList<Number> = mutableListOf()
        val links: MutableList<String> = mutableListOf()

        fun addItem(name: String, price: Number, link: String) {
            names.add(name)
            prices.add(price)
            links.add(link)
        }

        fun getItems(): MutableList<Wishlist> {
            val items: MutableList<Wishlist> = mutableListOf()
            for (i in names.indices) {
                val item = Wishlist(names[i], prices[i], links[i])
                items.add(item)
            }
            return items
        }

        fun removeItem(position: Int) {
            if (position in 0 until names.size) {
                names.removeAt(position)
                prices.removeAt(position)
                links.removeAt(position)
            }
        }
    }
}
