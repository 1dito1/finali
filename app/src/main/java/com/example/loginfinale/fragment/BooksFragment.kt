package com.example.loginfinale.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfinale.AddBookActivity
import com.example.loginfinale.BookActivity
import com.example.loginfinale.R
import com.example.loginfinale.adapter.BooksAdapter
import com.example.loginfinale.model.Book
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BooksFragment: Fragment(R.layout.fragment_books), BooksAdapter.OnItemClickListener {

    private lateinit var recyclerViewBooks: RecyclerView
    private val db = FirebaseDatabase.getInstance().getReference("books")
    private val dbUsers = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()
    private lateinit var arrayListBooks: ArrayList<Book>

    private lateinit var addNewBookButton: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        loadBooks()

    }

    private fun init(view: View) {

        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks)
        recyclerViewBooks.layoutManager = LinearLayoutManager(requireContext())
        arrayListBooks = arrayListOf()

        addNewBookButton = view.findViewById(R.id.addNewBook)
        addNewBookButton.setOnClickListener {
            startActivity(Intent(activity, AddBookActivity::class.java))
        }

    }

    private fun loadBooks() {
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    arrayListBooks.clear()

                    for (snap in snapshot.children) {
                        val currentBook = snap.getValue(Book::class.java)?: return
                        arrayListBooks.add(currentBook)
                    }

                    recyclerViewBooks.adapter = BooksAdapter(requireContext(), arrayListBooks, this@BooksFragment)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCartClick(position: Int) {
        val currentItem = arrayListBooks[position]

        val id = currentItem.id
        val key = dbUsers.child(auth.currentUser!!.uid).child("cart").push().key
        dbUsers.child(auth.currentUser!!.uid).child("cart").child(key.toString()).setValue(id)
    }

    override fun onItemClick(position: Int) {
        val currentItem = arrayListBooks[position]

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