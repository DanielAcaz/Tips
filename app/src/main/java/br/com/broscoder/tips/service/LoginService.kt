package br.com.broscoder.tips.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.view.LoginActivity
import br.com.broscoder.tips.view.MapsActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginService {


    companion object {

        lateinit var context: Context
        lateinit var mAuth: FirebaseAuth
        lateinit var mGoogleSignInClient: GoogleSignInClient
        lateinit var callbackManager: CallbackManager

        fun configLogin(context: Context, mAuth: FirebaseAuth) {
            this.mAuth = mAuth
            this.context = context

            //GOOGLE CONFIG
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.resources.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            mGoogleSignInClient = GoogleSignIn.getClient(context, gso)


            //FACEBOOK CONFIG
            callbackManager = CallbackManager.Factory.create()

        }

        fun beforeLogin(): Intent {
            return mGoogleSignInClient.signInIntent
        }

        fun onLogin(requestCode: Int, resultCode: Int, data: Intent?) : Exception? {

            //FACEBOOK LOGIN
            callbackManager?.onActivityResult(requestCode, resultCode, data)

            //GOOGLE LOGIN
            if (requestCode == context.resources.getInteger(R.integer.request_code)) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    return firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.w("GOOGLE_AUTH", "Google sign in failed", e)
                    return null
                }
            }
            return null
        }

        //FACEBOOK METHOD
        private fun handleFacebookAccessToken(accessToken: AccessToken): Exception? {
            Log.w("FACEBOOK_AUTH", "handleAccessToken: ${accessToken}")
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.w("FACEBOOK_AUTH", "AUTHENTICATION SUCCESS")
                        }
                    }
            return null
        }

        // GOOGLE METHOD
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount) : Exception? {
            Log.w("GOOGLE_AUTH", "firebaseAuthWithGoogle METHOD")
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            var result: FirebaseAuthException? = null
            mAuth.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.w("GOOGLE_AUTH", "AUTHENTICATION SUCCESS")
                } else if (it.exception is FirebaseAuthInvalidUserException) {
                    result = FirebaseAuthInvalidUserException(context.resources.getString(R.string.emailNotValid),
                            "FirebaseAuthInvalidUserException")
                } else if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    result = FirebaseAuthInvalidCredentialsException(context.resources.getString(R.string.passwordNotValid),
                            "FirebaseAuthInvalidCredentialsException")
                } else {
                    result = FirebaseAuthException("Something has error", "FirebaseAuthException")
                }
            }
        return result
        }

        fun beforeLoginForFacebook() {
            LoginManager.getInstance().logInWithReadPermissions(context as Activity, Arrays.asList("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager,
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

    }

}