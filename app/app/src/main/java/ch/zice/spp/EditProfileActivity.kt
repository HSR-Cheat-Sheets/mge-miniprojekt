package ch.zice.spp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.firestore.FirestoreClass
import com.bumptech.glide.Glide
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {

    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val saveButton = findViewById<Button>(R.id.button_add_profile_save)

        val firstnameView = findViewById<EditText>(R.id.editProfileFirstname)
        val lastnameView = findViewById<EditText>(R.id.editProfileLastname)
        val emailView = findViewById<EditText>(R.id.editProfileEmail)
        val mobileView = findViewById<EditText>(R.id.editProfileMobile)

        val profilePicture = findViewById<ImageView>(R.id.imageView_edit_profile_picture)
        profilePicture.setOnClickListener(){ changeProfilePicture() }

        val sharedPrefs = this.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val firstname = sharedPrefs.getString("firstname", "")!!
        val lastname = sharedPrefs.getString("lastname", "")!!
        val email = sharedPrefs.getString("email", "")!!
        val mobile = sharedPrefs.getLong("mobile", 0)

        firstnameView.setText(firstname)
        lastnameView.setText(lastname)
        emailView.setText(email)
        emailView.isEnabled = false
        mobileView.setText(mobile.toString())

        saveButton.setOnClickListener(){

            val userObject = HashMap<String, Any>()
            userObject["firstName"] = firstnameView.text.toString()
            userObject["lastName"] = lastnameView.text.toString()
            userObject["email"] = emailView.text.toString()
            userObject["mobile"] = mobileView.text.toString()

            if (mSelectedImageFileUri != null){
                FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                userObject["image"] = mUserProfileImageURL

                val sharedPreferences = this.getSharedPreferences(
                    Constants.SPP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.USER_PROFILE_IMAGE_URI,
                    mUserProfileImageURL
                )
                editor.apply()

                Glide.with(this).load(mUserProfileImageURL).into(profilePicture)
            }

            if (validateData(userObject)){
                if(userObject["mobile"].toString() == ""){
                    userObject.remove("mobile")
                }
                saveData(userObject)
            } else {
                Toast.makeText(this, "Data validation error.", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun saveData(userHashMap: HashMap<String, Any>){
        userHashMap["mobile"] = Integer.parseInt(userHashMap["mobile"].toString())
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    private fun validateData(userHashMap: HashMap<String, Any>): Boolean {

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

    fun imageUploadSuccess(imageURL: String){
        mUserProfileImageURL = imageURL
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    try{
                        mSelectedImageFileUri = data.data!!
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