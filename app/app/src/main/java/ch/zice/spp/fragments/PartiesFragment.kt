package ch.zice.spp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ch.zice.spp.R
import ch.zice.spp.utils.firestore.FirestoreClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PartiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartiesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //Get data from DB
        val db = FirebaseFirestore.getInstance();

        val parties: MutableList<Party> = mutableListOf();

        db.collection("parties").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {


                    Log.d("exists", "${document.data}")
                    val party = Party(
                        document.data.get("name") as String,
                        document.data.get("location") as String,
                        document.data.get("organizer") as String,
                        Date());
                    parties.add(party);


                    Log.d("exists", "${parties.get(0).getName()}")
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parties, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PartiesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PartiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class Party(name: String, location: String, organizer: String, date: Date ) {

    private val name: String = name;
    private val location: String = location;
    private val organizer: String = organizer;
    private val date: Date = date;

    fun getName(): String {
        return this.name;
    }
    fun getLocation(): String {
        return this.location;
    }
    fun getOranizer(): String {
        return this.organizer;
    }
    fun getDate(): Date {
        return this.date;
    }
}




