package ch.zice.spp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import ch.zice.spp.EditParty
import ch.zice.spp.EditProfileActivity
import ch.zice.spp.R
import ch.zice.spp.utils.firestore.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*


class PartiesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        val view: View = inflater.inflate(R.layout.fragment_parties, container, false)

        view.findViewById<Button>(R.id.add_party_button).setOnClickListener(){
            activity?.startActivity(Intent(activity, EditParty::class.java).apply{})
        }

        return view
    }


}




