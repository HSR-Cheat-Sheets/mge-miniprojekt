package ch.zice.spp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Login button
        val buttonLogin = findViewById<Button>(R.id.button_login_activity_register)
        buttonLogin.setOnClickListener(){
            goToLoginActivity()
        }
    }


    private fun goToLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply{}
        startActivity(intent)
    }
}