package com.example.kerkar.login_and_register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kerkar.R
import com.example.kerkar.User_data_class
import com.example.kerkar.firedb_login_register_class
import com.example.kerkar.main_activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register_activity.*

private lateinit var auth: FirebaseAuth
private val TAG = "Register activity"

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)



        create_account_button.setOnClickListener{
            register_email()
        }


        already_hav_account_textview.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register_email(){
        auth = Firebase.auth
        val mail = EmaileditTextText.text.toString()
        val password = PasswordeditTextText.text.toString()
        val college = college_editTextText.text.toString()

        if(mail.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "e-mai/password is empty", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "e-mai/password is empty")
            return
        }
        Log.d(TAG, "Email is: $mail")
        Log.d(TAG, "Password is: $password")

        auth.createUserWithEmailAndPassword(mail, password)

                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    //else if successful
                    Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")

                    val user_data = User_data_class(mail, password, it.result?.user?.uid!!, college)

                    val firedb = firedb_login_register_class(this)
                    firedb.add_user_college(user_data.u_id, user_data.college)


                    val intent = Intent(this, main_activity::class.java)
                    startActivity(intent)


                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to create user: ${it.message}")
                    Toast.makeText(this,"Failed to create user: ${it.message}", Toast.LENGTH_LONG).show()

                }

    }
}