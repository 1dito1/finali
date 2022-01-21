package com.example.loginfinale.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide.init
import com.example.loginfinale.MainActivity
import com.example.loginfinale.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment: Fragment (R.layout.fragment_profile) {

    private lateinit var buttonLogOut: Button
    private lateinit var userName: TextView
    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseDatabase.getInstance().getReference("users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        buttonLogOut = view.findViewById(R.id.buttonLogOut)
        buttonLogOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }

        userName = view.findViewById(R.id.username)
        db.child(auth.currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName.text = snapshot.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

}