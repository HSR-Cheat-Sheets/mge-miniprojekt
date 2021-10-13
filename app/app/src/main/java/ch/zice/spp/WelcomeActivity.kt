package ch.zice.spp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }


    fun goToLoginActivity(view: View){
        val intent = Intent(this, LoginActivity::class.java).apply{
            putExtra("EXTRA_DATA", "data itself")
        }

        startActivity(intent)
    }

}