package com.example.saira_000.firebase_login_example

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        butnSignUp.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (editTextEmail.text.toString().isNullOrEmpty() || editTextPassword.text.toString().isNullOrEmpty())
                textViewResponse.text = "Email Address or Password is not provided"
            else {
                auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            textViewResponse.text = "Sign Up successful. Email address and password are created"
                            val user = auth.currentUser
                        } else
                            textViewResponse.text = "Sign Up Failed"
                    }
            }
        }
        textViewSignInLink.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}