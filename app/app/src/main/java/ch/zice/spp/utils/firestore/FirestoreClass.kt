package ch.zice.spp.utils.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ch.zice.spp.LoginActivity
import ch.zice.spp.RegisterActivity
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User){
        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener{e ->
                 Log.e(
                     activity.javaClass.simpleName,
                     "Error while registering the user",
                     e
                 )
            }
    }

    fun getCurrentUserID(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""

        if(currentUser != null){
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun getUserDetails(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {
                document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(User::class.java)!!
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.SPP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                when(activity){
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }

            }



    }

}