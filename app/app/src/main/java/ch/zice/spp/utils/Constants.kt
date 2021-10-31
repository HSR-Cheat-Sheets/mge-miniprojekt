package ch.zice.spp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String = "users"
    const val SPP_PREFERENCES: String = "SPP_prefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val READ_STORAGE_PERMISSION_CODE: Int = 2
    const val PICK_IMAGE_REQUEST_CODE: Int = 1

    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val USER_PROFILE_IMAGE_URI: String = "User_Profile_Image_uri"

    const val PARTIES: String = "parties"


    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}