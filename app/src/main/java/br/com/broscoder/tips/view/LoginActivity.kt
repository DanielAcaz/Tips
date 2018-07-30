package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.extensions.validation
import br.com.broscoder.tips.service.LoginService
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import java.util.Arrays.asList


class LoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        tilPasswordLogin.isPasswordVisibilityToggleEnabled = true

        LoginService.configLogin(this, mAuth)

        btFacebook.setOnClickListener {
            LoginService.beforeLoginForFacebook()
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val status = LoginService.onLogin(requestCode, resultCode, data)
        if (status == null ){
            intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        } else if (status is FirebaseAuthInvalidUserException) {
                tilUsernameLogin.error = resources.getString(R.string.emailNotValid)
            } else if (status is FirebaseAuthInvalidCredentialsException) {
                tilPasswordLogin.error = resources.getString(R.string.passwordNotValid)
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }

    }

    fun login(view: View){
        if(tilUsernameLogin.validation()) {
            val email = tilUsernameLogin.editText?.text.toString()
            val password = tilPasswordLogin.editText?.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful){
                            val user = mAuth.currentUser
                            if(user!!.isEmailVerified) {
                                intent = Intent(this, MapsActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            if (it.exception is FirebaseAuthInvalidUserException?) {
                                tilUsernameLogin.error = resources.getString(R.string.emailNotValid)
                            } else if (it.exception is FirebaseAuthInvalidCredentialsException) {
                                tilPasswordLogin.error = resources.getString(R.string.passwordNotValid)
                            } else {
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                            Log.w("AUTH", "createUserWithEmail:failure", it.exception)
                        }
                    }
        }
    }

    fun signIn(view: View){
        Log.w("GOOGLE_AUTH", "signIn METHOD")
        intent = LoginService.beforeLogin()
        startActivityForResult(intent, resources.getInteger(R.integer.request_code), null)
    }

    fun createUser(view: View) {
        intent = Intent(this, CreateUserActivity::class.java)
        startActivity(intent)
    }
}
