package com.example.loginfinale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.loginfinale.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddBookActivity : AppCompatActivity() {

    private val db = FirebaseDatabase.getInstance().getReference("books")
    private val dbUsers = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextImageUrl: EditText
    private lateinit var buttonAddBook: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        init()
    }

    private fun init() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        buttonAddBook = findViewById(R.id.buttonAddBook)
        buttonAddBook.setOnClickListener {
            addNewBook()
        }
    }

    private fun addNewBook() {

        val id = db.push().key
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val imageUrl = editTextImageUrl.text.toString()

        val book = Book(title, description, imageUrl, id)

        db.child(id.toString()).setValue(book).addOnSuccessListener {
            finish()
        }

    }

}



