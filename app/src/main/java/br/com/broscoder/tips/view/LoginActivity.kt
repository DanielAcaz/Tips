package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.extensions.validation
import com.facebook.*
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount




class LoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        tilPasswordLogin.isPasswordVisibilityToggleEnabled = true

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btGoogleLogin.setOnClickListener {
            signIn(it)
        }

        callbackManager = CallbackManager.Factory.create()
        btFacebookLogin.setReadPermissions("email", "public_profile")
        btFacebookLogin.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        mAuth.signOut()
                        Log.w("FACEBOOK_AUTH", "onSuccess")
                        handleFacebookAccessToken(loginResult.accessToken)

                    }

                    override fun onCancel() {
                        Log.w("FACEBOOK_AUTH", "onCancel")
                    }

                    override fun onError(exception: FacebookException) {
                        Log.w("FACEBOOK_AUTH", "onError")
                    }
                })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        verifyUser(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException){
                Log.w("GOOGLE_AUTH", "Google sign in failed", e)
            }
        }
    }

    private fun verifyUser(user: FirebaseUser?): Boolean{
        return user == null;
    }

    fun login(view: View){
        if(tilUsernameLogin.validation()) {
            val email = tilUsernameLogin.editText?.text.toString()
            val password = tilPasswordLogin.editText?.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful){
                            intent = Intent(this, MapsActivity::class.java)
                            startActivity(intent)
                        } else {
                            if (it.exception is FirebaseAuthInvalidUserException) {
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

    private fun handleFacebookAccessToken(accessToken: AccessToken){
        Log.w("FACEBOOK_AUTH", "handleAccessToken: ${accessToken}")
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth.signInWithCredential(credential)
        if(mAuth.currentUser != null){
            intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(view: View){
        Log.w("GOOGLE_AUTH", "Google sign in failed")
        mAuth.signOut()
        intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, 101)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.w("GOOGLE_AUTH", "Google sign in failed")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        intent = Intent(this, MapsActivity::class.java)
                        startActivity(intent)
                    } else {
                        if (it.exception is FirebaseAuthInvalidUserException) {
                            tilUsernameLogin.error = resources.getString(R.string.emailNotValid)
                        } else if (it.exception is FirebaseAuthInvalidCredentialsException) {
                            tilPasswordLogin.error = resources.getString(R.string.passwordNotValid)
                        } else {
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                        Log.w("GOOGLE_AUTH", "createUserWithGoogle:failure", it.exception)
                    }
                }
    }
}
