package ch.zice.spp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ch.zice.spp.EditProfileActivity
import ch.zice.spp.R
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.auth.FirebaseAuthClass


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
        }

        val sharedPrefs = this.getActivity()?.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPrefs?.getString(Constants.LOGGED_IN_USERNAME, "")!!
        val tmp = view.findViewById<TextView>(R.id.profile_textview_name)
        tmp?.text = username

        return view
    }


}

