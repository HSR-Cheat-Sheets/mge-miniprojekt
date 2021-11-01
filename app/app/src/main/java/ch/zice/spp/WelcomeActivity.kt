package ch.zice.spp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val buttonRegister = findViewById<Button>(R.id.button_register)
        buttonRegister.setOnClickListener(){
            goToRegisterActivity()
        }

        val buttonLogin = findViewById<Button>(R.id.button_login)
        buttonLogin.setOnClickListener(){
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun goToRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java).apply{}
        startActivity(intent)
    }
}

