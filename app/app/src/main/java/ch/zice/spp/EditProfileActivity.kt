package ch.zice.spp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ch.zice.spp.utils.Constants

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val save_button = findViewById<Button>(R.id.button_edit_profile_save)

        val firstnameView = findViewById<EditText>(R.id.editProfileFirstname)
        val lastnameView = findViewById<EditText>(R.id.editProfileLastname)
        val emailView = findViewById<EditText>(R.id.editProfileEmail)
        val mobileView = findViewById<EditText>(R.id.editProfileMobile)
        val passwordView = findViewById<EditText>(R.id.editProfilePassword)
        val passwordConfirmView = findViewById<EditText>(R.id.editProfilePasswordConfirm)


        val sharedPrefs = this.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val firstname = sharedPrefs.getString("firstname", "")!!
        val lastname = sharedPrefs.getString("lastname", "")!!
        val email = sharedPrefs.getString("email", "")!!
        val mobile = sharedPrefs.getString("mobile", "")!!

        firstnameView.setText(firstname)
        lastnameView.setText(lastname)
        emailView.setText(email)
        mobileView.setText(mobile)




        save_button.setOnClickListener(){
            // Validate Data
            // TODO:
            // Save Data
            saveData()
        }

    }


    private fun saveData(){

    }

}