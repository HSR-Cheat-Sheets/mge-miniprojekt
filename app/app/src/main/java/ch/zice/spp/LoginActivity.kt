package ch.zice.spp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.zice.spp.utils.firestore.FirestoreClass
import ch.zice.spp.utils.models.User
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val email = findViewById<TextView>(R.id.editTextLoginEmail)
        val password = findViewById<TextView>(R.id.editTextLoginPassword)

        val buttonRegister = findViewById<Button>(R.id.button_register_activity_login)
        buttonRegister.setOnClickListener(){
            goToRegisterActivity()
        }

        val buttonLogin = findViewById<Button>(R.id.button_login_activity_login)

        buttonLogin.setOnClickListener(){
            val emailString: String = email.text.toString()
            val passwordString: String = password.text.toString()
            if(validateLoginDetails(emailString, passwordString)){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(
                                this,
                                "Login succeeded",
                                Toast.LENGTH_SHORT
                            ).show()
                            FirestoreClass().getUserDetails(this@LoginActivity)

                        } else {
                            Toast.makeText(
                                this,
                                "Login failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Generic error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    private fun goToRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java).apply{})
    }

    private fun validateLoginDetails(email: String, password: String): Boolean{

        return when{
            TextUtils.isEmpty(email.trim { it <= ' '}) -> {
                Toast.makeText(
                    this,
                    "please enter email",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            TextUtils.isEmpty(password.trim { it <= ' '}) -> {
                Toast.makeText(
                    this,
                    "please enter password",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            else -> {
                true
            }
        }

    }

    fun userLoggedInSuccess(user: User){
        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


}
