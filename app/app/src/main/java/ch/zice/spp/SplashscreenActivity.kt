package ch.zice.spp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide(); // hide the title bar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        @Suppress("DEPRECATION")
       Handler().postDelayed(
           {
               startActivity(Intent(this, WelcomeActivity::class.java))
               finish()
           }, 1000
       )


    }
}