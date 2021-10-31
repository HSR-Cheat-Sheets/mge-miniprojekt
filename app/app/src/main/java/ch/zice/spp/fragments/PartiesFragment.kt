package ch.zice.spp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.i("Resumed fragment", "REsssssssssssumed")
        getPartiesListFromFirestore()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        val view: View = inflater.inflate(R.layout.fragment_parties, container, false)

        view.findViewById<Button>(R.id.add_party_button).setOnClickListener(){
            activity?.startActivity(Intent(activity, AddParty::class.java).apply{})
        }

        return view
    }

    private fun getPartiesListFromFirestore(){
        val statusText = view?.findViewById<TextView>(R.id.PartiesLoadingStatusTextView)
        statusText?.text = "started..."
        FirestoreClass().getPartiesList(this)
    }

    fun successPartiesListFromFirestore(partiesList: ArrayList<Party>){

        val statusText = view?.findViewById<TextView>(R.id.PartiesLoadingStatusTextView)


        for(i in partiesList){
            Log.i("Product Name", i.name)
        }

        statusText?.text = "done"

        val tmp = view?.findViewById<RecyclerView>(R.id.rv_my_party_items)

        if(partiesList.size > 0){
            tmp?.layoutManager = LinearLayoutManager(activity)
            tmp?.setHasFixedSize(true)
            val adapterParties = MyPartiesListAdapter(requireActivity(), partiesList)
            tmp?.adapter = adapterParties
        }


    }


}




