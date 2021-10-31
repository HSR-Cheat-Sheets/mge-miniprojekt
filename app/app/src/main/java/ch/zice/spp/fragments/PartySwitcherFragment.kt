
package ch.zice.spp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zice.spp.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PartySwitcher : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_party_switcher, container, false)


        //Get data from DB
        val db = FirebaseFirestore.getInstance();

        val parties: MutableList<Party> = mutableListOf();

        //TODO Update date to real date from DB
        // -> Currently errer because timestamp != Date
        db.collection("parties").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val party = Party(
                        document.data.get("name") as String,
                        document.data.get("location") as String,
                        document.data.get("organizer") as String,
                        Date()
                    );
                    parties.add(party);
                }
            }

    }


}

class Party(private val name: String, private val location: String,
            private val organizer: String, private val date: Date
) {

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
