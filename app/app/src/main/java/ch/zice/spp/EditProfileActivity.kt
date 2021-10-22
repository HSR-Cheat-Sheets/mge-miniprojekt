package ch.zice.spp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.firestore.FirestoreClass
import ch.zice.spp.utils.models.User
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val saveButton = findViewById<Button>(R.id.button_edit_profile_save)

        val firstnameView = findViewById<EditText>(R.id.editProfileFirstname)
        val lastnameView = findViewById<EditText>(R.id.editProfileLastname)
        val emailView = findViewById<EditText>(R.id.editProfileEmail)
        val mobileView = findViewById<EditText>(R.id.editProfileMobile)
        val passwordView = findViewById<EditText>(R.id.editProfilePassword)
        val passwordConfirmView = findViewById<EditText>(R.id.editProfilePasswordConfirm)

        val profilePicture = findViewById<ImageView>(R.id.imageView_edit_profile_picture)
        profilePicture.setOnClickListener(){ changeProfilePicture() }

        val sharedPrefs = this.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val firstname = sharedPrefs.getString("firstname", "")!!
        val lastname = sharedPrefs.getString("lastname", "")!!
        val email = sharedPrefs.getString("email", "")!!
        val mobile = sharedPrefs.getLong("mobile", 0)

//        Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show()

        firstnameView.setText(firstname)
        lastnameView.setText(lastname)
        emailView.setText(email)
        mobileView.setText(mobile.toString())




        saveButton.setOnClickListener(){

            val tmpUsr = HashMap<String, Any>()
            tmpUsr["firstName"] = firstnameView.text.toString()
            tmpUsr["lastName"] = lastnameView.text.toString()
            tmpUsr["email"] = emailView.text.toString()
            tmpUsr["mobile"] = mobileView.text.toString()
            tmpUsr["password"] = passwordView.text.toString()
            tmpUsr["passwordConfirm"] = passwordConfirmView.text.toString()


            if (validateData(tmpUsr)){
                if(tmpUsr["mobile"].toString() == ""){
                    tmpUsr.remove("mobile")
                }
                saveData(tmpUsr)
            } else {
                Toast.makeText(this, "Data validation error.", Toast.LENGTH_LONG).show()
            }


        }

    }


    private fun saveData(userHashMap: HashMap<String, Any>){
//        Toast.makeText(this, "Saving data...", Toast.LENGTH_LONG).show()
        userHashMap.remove("password")
        userHashMap.remove("passwordConfirm")
        userHashMap["mobile"] = Integer.parseInt(userHashMap.get("mobile").toString())
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    private fun validateData(userHashMap: HashMap<String, Any>): Boolean {
        // TODO: Implement validation

        return when {
            TextUtils.isEmpty(userHashMap["firstName"].toString()) -> {
                Toast.makeText(this, "Firstname not valid!", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(userHashMap["lastName"].toString()) -> {
                Toast.makeText(this, "Lastname not valid!", Toast.LENGTH_SHORT).show()
                false
            } else -> {
                true
            }
        }


    }

    private fun changeProfilePicture(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Storage Permission already granted.", Toast.LENGTH_SHORT).show()
            Constants.showImageChooser(this)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_PERMISSION_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    try{
                        val selectedImageFileUri = data.data!!
                        Toast.makeText(this, "Image choosen successfully.", Toast.LENGTH_SHORT).show()
                    } catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(this, "Error while choosing profile image.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}