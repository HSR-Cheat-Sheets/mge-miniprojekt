package ch.zice.spp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.zice.spp.AddParty
import ch.zice.spp.R
import ch.zice.spp.adapters.MyPartiesListAdapter
import ch.zice.spp.utils.firestore.FirestoreClass
import ch.zice.spp.utils.models.Party
import kotlin.collections.ArrayList


class PartiesFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        getPartiesListFromFirestore()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_parties, container, false)

        view.findViewById<Button>(R.id.add_party_button).setOnClickListener(){
            activity?.startActivity(Intent(activity, AddParty::class.java).apply{})
        }

        return view
    }

    private fun getPartiesListFromFirestore(){
        FirestoreClass().getPartiesList(this)
    }

    fun successPartiesListFromFirestore(partiesList: ArrayList<Party>){

        val listView = view?.findViewById<RecyclerView>(R.id.rv_my_party_items)

        if(partiesList.size > 0){
            listView?.layoutManager = LinearLayoutManager(activity)
            listView?.setHasFixedSize(true)
            val adapterParties = MyPartiesListAdapter(requireActivity(), partiesList)
            listView?.adapter = adapterParties
        }

    }


}




