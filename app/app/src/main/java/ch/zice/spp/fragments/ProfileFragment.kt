package ch.zice.spp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ch.zice.spp.EditProfileActivity
import ch.zice.spp.LoginActivity
import ch.zice.spp.R
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.auth.FirebaseAuthClass
import com.bumptech.glide.Glide


class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.profile_button_edit).setOnClickListener(){
            activity?.startActivity(Intent(activity, EditProfileActivity::class.java).apply{})
        }

        view.findViewById<Button>(R.id.profile_button_logout).setOnClickListener(){
            FirebaseAuthClass().logOut()
            val i = Intent(activity, LoginActivity::class.java).apply{
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            activity?.startActivity(i)

        }

        val sharedPrefs = this.getActivity()?.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPrefs?.getString(Constants.LOGGED_IN_USERNAME, "")!!
        val profileUsernameView = view.findViewById<TextView>(R.id.profile_textview_name)
        profileUsernameView?.text = username
//
//        val profileUri = sharedPrefs.getString(Constants.USER_PROFILE_IMAGE_URI, "")!!
//        Log.e("Custom Image error", profileUri)
//        val profileUriView = view.findViewById<ImageView>(R.id.imageView_profile_picture)
//        Glide.with(this).load(profileUri).into(profileUriView)



        return view
    }

    override fun onResume() {
        super.onResume()


    }


}

