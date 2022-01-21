package com.example.loginfinale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BookActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        titleTextView = findViewById(R.id.title)
        descriptionTextView = findViewById(R.id.description)
        image = findViewById(R.id.imageView)

        titleTextView.text = intent.getStringExtra("title")
        descriptionTextView.text = intent.getStringExtra("description")
        Glide.with(this)
            .load(intent.getStringExtra("imageUrl"))
            .into(image)

    }
}