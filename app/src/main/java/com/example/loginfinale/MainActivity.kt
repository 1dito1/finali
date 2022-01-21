package com.example.loginfinale

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var signUp: TextView
    private lateinit var logIn: TextView
    private lateinit var signUpLayout: LinearLayout
    private lateinit var logInLayout: LinearLayout
    private lateinit var eMail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var confirmpass: TextInputEditText
    private lateinit var eMails: TextInputEditText
    private lateinit var passwords: TextInputEditText
    private lateinit var signIn: Button
    private lateinit var signUpButton: Button
    private lateinit var username: TextInputEditText

    private val db = FirebaseDatabase.getInstance().getReference("users")

    private val auth = FirebaseAuth.getInstance()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        setContentView(R.layout.activity_main)
        init()
        registerListeners()
        loginListeners()
    }


    private fun init() {
        signUp = findViewById(R.id.signUp)
        signIn = findViewById(R.id.signIn)
        logIn = findViewById(R.id.logIn)
        signUpLayout = findViewById(R.id.signUpLayout)
        logInLayout = findViewById(R.id.logInLayout)
        eMail = findViewById(R.id.eMail)
        password = findViewById(R.id.password)
        confirmpass = findViewById(R.id.passwords01)
        eMails = findViewById(R.id.eMails)
        passwords = findViewById(R.id.passwordss)
        signUpButton = findViewById(R.id.signUpButton)
        username = findViewById(R.id.username)

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun registerListeners(){
        signUp.setOnClickListener {
            signUp.background =  resources.getDrawable(R.drawable.switch_trcks , null)
            signUp.setTextColor(resources.getColor(R.color.textColor , null))
            logIn.background = null
            signUpLayout.visibility = View.VISIBLE
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor, null))
        }

        signUpButton.setOnClickListener {
            val email = eMails.text.toString()
            val password = passwords.text.toString()
            val confirmPass = confirmpass.text.toString()
            if (email.isEmpty() && password.isEmpty() && confirmPass.isEmpty() && password != confirmPass && password.length < 8 || !email.contains("@")){
                Toast.makeText(this, "შეიყვანეთ პარამეტრები სწორად", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){

                        val user = username.text.toString()

                        db.child(auth.currentUser!!.uid).child("username").setValue(user)

                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "თქვენ ვერ დარეგისტრირდით", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun loginListeners(){
        logIn.setOnClickListener {
            signUp.background = null
            signUp.setTextColor(resources.getColor(R.color.pinkColor , null))
            logIn.background =  resources.getDrawable(R.drawable.switch_trcks , null)
            signUpLayout.visibility = View.GONE
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.pinkColor, null))
        }

        signIn.setOnClickListener {
            val email = eMail.text.toString()
            val password = password.text.toString()
            if (email.isEmpty() && password.isEmpty()  && password.length < 8 || !email.contains("@")){
                Toast.makeText(this, "შეიყვანეთ პარამეტრები სწორად", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this , "თქვენ წარმატებით შეხვედით აქაუნთზე" , Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}


