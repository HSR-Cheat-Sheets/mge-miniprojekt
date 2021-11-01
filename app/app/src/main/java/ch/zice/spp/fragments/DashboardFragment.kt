package ch.zice.spp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ch.zice.spp.R
import ch.zice.spp.utils.firestore.FirestoreClass

class DashboardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_dashboard, container, false)

        getPartiesCountFromFirestore()
        getUserCountFromFirestore()
        return view

    }

    private fun getUserCountFromFirestore(){
        FirestoreClass().countAllUsers(this)
    }

    private fun getPartiesCountFromFirestore(){
        FirestoreClass().countAllParties(this)
    }

    fun successPartiesCountFromFirestore(count: Int) {
        val partiesCountView = view?.findViewById<TextView>(R.id.dashboard_parties)
        partiesCountView?.setText(count.toString())
    }

    fun successUsersCountFromFirestore(count: Int) {
        val userCountView = view?.findViewById<TextView>(R.id.dashboard_registered)
        userCountView?.setText(count.toString())
    }

}


