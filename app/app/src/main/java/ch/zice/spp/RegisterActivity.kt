package ch.zice.spp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Login button
        val buttonLogin = findViewById<Button>(R.id.button_login_activity_register)
        buttonLogin.setOnClickListener(){
            goToLoginActivity()
        }

        // Login button
        val buttonRegister = findViewById<Button>(R.id.button_register_activity_register)
        buttonRegister.setOnClickListener(){
            register()
        }


    }


    private fun goToLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun register(){

        val firstname = findViewById<EditText>(R.id.editTextFirstname).text.toString()
        val lastname = findViewById<EditText>(R.id.editTextLastname).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        val passwordConfirm = findViewById<EditText>(R.id.editTextPasswordConfirm).text.toString()




        when{
            TextUtils.isEmpty(firstname.trim{it <= ' '}) -> {
                Toast.makeText(
                    this,
                    "Please enter valid firstname",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(lastname.trim{it <= ' '}) -> {
                Toast.makeText(
                    this,
                    "Please enter valid lastname",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(email.trim{it <= ' '}) -> {
//                android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
                Toast.makeText(
                    this,
                    "Please enter valid email",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(password.trim{it <= ' '}) -> {
                Toast.makeText(
                    this,
                    "Please enter valid password",
                    Toast.LENGTH_SHORT
                ).show()
            }

            !TextUtils.equals(password.trim(), passwordConfirm.trim()) -> {
                Toast.makeText(
                    this,
                    "Password do not match",
                    Toast.LENGTH_SHORT
                ).show()
            } else -> {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password)
                    .addOnCompleteListener(
                        OnCompleteListener <AuthResult>{ task ->
                            if(task.isSuccessful){
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this,
                                    "User registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )

            }



        }
    }
}
