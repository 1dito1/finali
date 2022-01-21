package com.example.loginfinale.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfinale.BookActivity
import com.example.loginfinale.R
import com.example.loginfinale.adapter.BooksAdapter
import com.example.loginfinale.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment: Fragment (R.layout.fragment_cart), BooksAdapter.OnItemClickListener {

    private val db = FirebaseDatabase.getInstance().getReference("users")
    private val dbBooks = FirebaseDatabase.getInstance().getReference("books")
    private val auth = FirebaseAuth.getInstance()
    private lateinit var arrayListCartBooks: ArrayList<Book>

    private lateinit var recyclerViewCartBooks: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        loadCartBooks()

    }

    private fun init(view: View) {
        recyclerViewCartBooks = view.findViewById(R.id.recyclerViewCartBooks)
        arrayListCartBooks = arrayListOf()

        recyclerViewCartBooks.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun loadCartBooks() {

        db.child(auth.currentUser!!.uid).child("cart").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Log.d("SHOW", "TEST")
                    for (snap in snapshot.children) {
                        Log.d("SHOW", snap.toString())
                        val currentItem = snap.value.toString()
                        dbBooks.child(currentItem).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val current = snapshot.getValue(Book::class.java)?: return
                                arrayListCartBooks.add(current)
                                recyclerViewCartBooks.adapter = BooksAdapter(context!!.applicationContext, arrayListCartBooks, this@CartFragment)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        db.child(auth.currentUser!!.uid).child("cart").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val list: ArrayList<String> = arrayListOf()

                    for (snap in snapshot.children) {
                        val currentItem = snap.value.toString()
                        list.add(currentItem)
                    }

                    dbBooks.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (bookSnap in snapshot.children) {

                                arrayListCartBooks.clear()

                                if (list.contains(bookSnap.key)) {
                                    val currentBook = bookSnap.getValue(Book::class.java)?: return
                                    arrayListCartBooks.add(currentBook)
                                }
                            }

                            recyclerViewCartBooks.adapter = BooksAdapter(requireContext(), arrayListCartBooks, this@CartFragment)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCartClick(position: Int) {
        Log.d("SHOW", "")
    }

    override fun onItemClick(position: Int) {
        val currentItem = arrayListCartBooks[position]

        val id = currentItem.id.toString()
        val title = currentItem.title.toString()
        val description = currentItem.description.toString()
        val imageUrl = currentItem.imageUrl.toString()

        val intent = Intent(activity, BookActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("description", description)
        intent.putExtra("imageUrl", imageUrl)
        startActivity(intent)
    }

}






