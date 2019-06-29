package xyz.thingapps.mindoasis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.model.Maxim

class MaximFireStoreAdapter(query: Query) :
    FirestoreAdapter<MaximFireStoreAdapter.ViewHolder>(query) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_maxim, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getSnapshot(position).toObject(Maxim::class.java)?.let { holder.bind(it) }
    }

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(maxim: Maxim) {

        }
    }
}