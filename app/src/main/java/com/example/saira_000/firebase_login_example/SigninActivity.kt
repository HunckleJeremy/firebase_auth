package com.example.saira_000.firebase_login_example

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_signin)
        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.TwitterBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build(),)

// Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)

        }
        buttonSignIn.setOnClickListener {
            val inputMethodManager =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (editTextEmail.text.toString().isNullOrEmpty() || editTextPassword.text.toString().isNullOrEmpty())
                textViewResponse.text = "Email Address or Password is not provided"
            else {
                auth.signInWithEmailAndPassword(editTextEmail.text.toString(),editTextPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                textViewResponse.text =  "Sign In successfull. "
                                val user = auth.currentUser
                                updateUI(user, editTextEmail.text.toString() )
                            } else
                                textViewResponse.text = "Invalid Email or Password"
                        }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?, emailAdd: String) {
        if(currentUser !=null){
            val intent = Intent(this, SignInActivity ::class.java)
            intent.putExtra("emailAddress", emailAdd);
            startActivity(intent)
            finish()
        }
    }
    companion object{
        private val RC_SIGN_IN = 120
    }

}
