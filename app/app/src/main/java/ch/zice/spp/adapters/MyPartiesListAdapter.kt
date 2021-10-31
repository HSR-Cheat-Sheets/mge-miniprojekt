package ch.zice.spp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.zice.spp.R
import ch.zice.spp.utils.models.Party

open class MyPartiesListAdapter(
    private val context: Context,
    private var list: ArrayList<Party>
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.party_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]



        if(holder is MyViewHolder){
            holder.itemView.findViewById<TextView>(R.id.tv_party_name_id).text = model.name
            holder.itemView.findViewById<TextView>(R.id.tv_party_location_id).text = model.location
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

}