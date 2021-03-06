package ch.zice.spp.utils.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import ch.zice.spp.AddParty
import ch.zice.spp.EditProfileActivity
import ch.zice.spp.LoginActivity
import ch.zice.spp.RegisterActivity
import ch.zice.spp.fragments.DashboardFragment
import ch.zice.spp.fragments.PartiesFragment
import ch.zice.spp.fragments.ProfileFragment
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.models.Party
import ch.zice.spp.utils.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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
                editor.putString(
                    "firstname",
                    user.firstName
                )
                editor.putString(
                    "lastname",
                    user.lastName
                )
                editor.putString(
                    "email",
                    user.email
                )
                editor.putLong(
                    "mobile",
                    user.mobile
                )
                editor.apply()

                when(activity){
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }

            }

    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS).document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener { Toast.makeText(activity, "Profile update successfully", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(activity, "An error occurred while updating the data.", Toast.LENGTH_SHORT).show() }
        getUserDetails(activity)
        when(activity){
            is EditProfileActivity -> {
                activity.onSuccessSave()
            }
        }
    }

    fun updateParty(activity: Activity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.PARTIES)
            .add(userHashMap)
            .addOnSuccessListener {
                Toast.makeText(activity, "Party added successfully", Toast.LENGTH_SHORT).show()
                when(activity){
                    is AddParty -> {
                        activity.onSuccessCreate()
                    }
                }
            }
            .addOnFailureListener { Toast.makeText(activity, "An error occurred while adding the party.", Toast.LENGTH_SHORT).show() }
        getUserDetails(activity)
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?){

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtension(activity, imageFileURI)
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapshot ->
            Log.e("Firebase Image URL", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri ->
                    Log.e("Downloadable Image URL", uri.toString())
                    when(activity){
                        is EditProfileActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Exception", exception.toString())
                }

        }
    }


    fun getPartiesList(fragment: Fragment){
        mFireStore.collection(Constants.PARTIES)
            .whereNotEqualTo("user_id", getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e("Parties List", document.documents.toString())
                val partiesList: ArrayList<Party> = ArrayList()

                for (i in document.documents){
                    val party = i.toObject(Party::class.java)
                    party!!.id = i.id

                    partiesList.add(party)
                }

                when(fragment){
                    is PartiesFragment -> {
                        fragment.successPartiesListFromFirestore(partiesList)
                    }
                }

            }
    }

    fun getMyPartiesList(fragment: Fragment) {
        mFireStore.collection(Constants.PARTIES)
            .whereEqualTo("user_id", getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e("Parties List", document.documents.toString())
                val partiesList: ArrayList<Party> = ArrayList()

                for (i in document.documents){
                    val party = i.toObject(Party::class.java)
                    party!!.id = i.id

                    partiesList.add(party)
                }
                when(fragment){
                    is ProfileFragment -> {
                        fragment.successMyPartiesListFromFirestore(partiesList)
                    }
                }

            }
    }

    fun countAllParties(fragment: Fragment) {
        mFireStore.collection(Constants.PARTIES)
            .get()
            .addOnSuccessListener { document ->
                val partiesList: ArrayList<Party> = ArrayList()
                for (i in document.documents){
                    val party = i.toObject(Party::class.java)
                    if (party != null) {
                        partiesList.add(party)
                    }
                }
                when(fragment){
                    is DashboardFragment -> {
                        fragment.successPartiesCountFromFirestore(partiesList.size)
                    }
                }
            }

    }

    fun countAllUsers(fragment: Fragment) {
        mFireStore.collection(Constants.USERS)
            .get()
            .addOnSuccessListener { document ->
                val userList: ArrayList<User> = ArrayList()
                for (i in document.documents){
                    val user = i.toObject(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                when(fragment){
                    is DashboardFragment -> {
                        fragment.successUsersCountFromFirestore(userList.size)
                    }
                }
            }

    }


    fun deleteParty(fragment: Fragment, partyID: String){
        mFireStore.collection(Constants.PARTIES).document(partyID)
            .delete()
            .addOnSuccessListener {
                when(fragment){
                    is ProfileFragment -> {
                        fragment.deletePartyOnSuccess()
                    }
                }
            }
    }

}