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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.zice.spp.AddParty
import ch.zice.spp.EditProfileActivity
import ch.zice.spp.LoginActivity
import ch.zice.spp.R
import ch.zice.spp.adapters.MyPartiesListAdapter
import ch.zice.spp.utils.Constants
import ch.zice.spp.utils.auth.FirebaseAuthClass
import ch.zice.spp.utils.firestore.FirestoreClass
import ch.zice.spp.utils.models.Party


class ProfileFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        getmyPartiesListFromFirestore()
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

        view.findViewById<Button>(R.id.add_party_button).setOnClickListener(){
            activity?.startActivity(Intent(activity, AddParty::class.java).apply{})
        }

        val sharedPrefs = this.getActivity()?.getSharedPreferences(Constants.SPP_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPrefs?.getString(Constants.LOGGED_IN_USERNAME, "")!!
        val profileUsernameView = view.findViewById<TextView>(R.id.profile_textview_name)
        profileUsernameView?.text = username

        return view
    }

    private fun getmyPartiesListFromFirestore(){
        FirestoreClass().getMyPartiesList(this)
    }

    fun successMyPartiesListFromFirestore(partiesList: ArrayList<Party>){
        val listView = view?.findViewById<RecyclerView>(R.id.rv_my_party_items)

        if(partiesList.size > 0){
            listView?.layoutManager = LinearLayoutManager(activity)
            listView?.setHasFixedSize(true)
            val adapterParties = MyPartiesListAdapter(requireActivity(), partiesList)
            listView?.adapter = adapterParties
        }
    }
}

