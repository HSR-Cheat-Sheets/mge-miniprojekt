package ch.zice.spp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ch.zice.spp.utils.firestore.FirestoreClass

class AddParty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_party)

        val name = findViewById<EditText>(R.id.addPartyName)
        val date = findViewById<EditText>(R.id.addPartyDate)
        val location = findViewById<EditText>(R.id.addPartyLocation)

        val addPartyButton = findViewById<Button>(R.id.button_add_party)
        addPartyButton.setOnClickListener {

            val nameText = name.text
            val dateText = date.text
            val locationText = location.text
            val userID = FirestoreClass().getCurrentUserID()

            val partyHashmap = HashMap<String, Any>()
            partyHashmap["name"] = nameText.toString()
            partyHashmap["date"] = dateText.toString()
            partyHashmap["location"] = locationText.toString()
            partyHashmap["user_id"] = userID

            FirestoreClass().updateParty(this, partyHashmap)
        }
    }

    fun onSuccessCreate(){
        finish()
    }

}

