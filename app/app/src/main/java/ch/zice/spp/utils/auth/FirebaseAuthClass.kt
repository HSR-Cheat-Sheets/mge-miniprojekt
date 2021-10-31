package ch.zice.spp.utils.auth

import com.google.firebase.auth.FirebaseAuth


class FirebaseAuthClass {
    private val mFirebaseAuth = FirebaseAuth.getInstance()

    fun logOut(){
        mFirebaseAuth.signOut()
    }

}