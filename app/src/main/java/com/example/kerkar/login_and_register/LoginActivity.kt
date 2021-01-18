package com.example.kerkar.login_and_register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerkar.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


private const val RC_SIGN_IN = 9001
private lateinit var auth: FirebaseAuth
private val TAG = "Loginactivity"

class LoginActivity: AppCompatActivity() {
    val firedb =firedb_login_register_class(this)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        use_google_sign_in_button.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()


        }

        login_button.setOnClickListener{
            login()
        }



        create_account_textview.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login() {
        val mail = EmaileditTextText.text.toString()
        val password = PasswordeditTextText.text.toString()

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Log.d(TAG, "signInWithEmail:success")

                        val uid = Firebase.auth.currentUser!!.uid
                        Log.d(TAG,  "uid:${uid}")

                        firedb_login_register_class(this).check_university_data()

//                        val intent = Intent(this, main_activity::class.java)
//                        startActivity(intent)

                    }else{
                        Log.d(TAG, "signInWithEmail:failure")
                        Toast.makeText(this, "ログインに失敗しました", Toast.LENGTH_LONG).show()
                    }
                }
    }

}

