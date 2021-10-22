package ch.zice.spp.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object Constants {
    const val USERS: String = "users"
    const val SPP_PREFERENCES: String = "SPP_prefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val READ_STORAGE_PERMISSION_CODE: Int = 2
    const val PICK_IMAGE_REQUEST_CODE: Int = 1

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

}