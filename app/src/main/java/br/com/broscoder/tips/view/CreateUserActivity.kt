package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.extensions.validation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_login.*

class CreateUserActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        mAuth = FirebaseAuth.getInstance()
    }

    fun signUp(view: View) {

        if (tilPasswordCreate.validation()
                && tilEmailCreate.validation()
                && tilUsernameCreate.validation()) {

        val email = tilEmailCreate.editText!!.text.toString()
        val passowrd = tilPasswordCreate.editText!!.text.toString()
        mAuth.createUserWithEmailAndPassword(email, passowrd).addOnCompleteListener {
            if(it.isSuccessful) {
                val user = mAuth.currentUser
                if (user != null) {
                    user.sendEmailVerification()
                    if(user.isEmailVerified) {

                    } else {
                        mAuth.signOut()
                        }
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    }
                } else {
                Log.w("CREATE_USER", "Create user failed")
                }
            }
        }

    }
}
