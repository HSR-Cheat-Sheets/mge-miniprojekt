package ch.zice.spp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)


        val buttonRegister = findViewById<Button>(R.id.button_register_activity_login)
        buttonRegister.setOnClickListener(){
            goToRegisterActivity()
        }

    }


    private fun goToRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java).apply{}
        startActivity(intent)
    }




}