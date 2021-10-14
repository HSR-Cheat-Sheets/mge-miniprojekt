package ch.zice.spp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        // Rergister button
        val buttonRegister = findViewById<Button>(R.id.button_register_activity_login)
        buttonRegister.setOnClickListener(){
            goToRegisterActivity()
        }

        // Login button
        val buttonLogin = findViewById<Button>(R.id.button_login_activity_login)
        buttonLogin.setOnClickListener(){
            goToMainActivity()
        }

    }


    private fun goToRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java).apply{})
    }

}
