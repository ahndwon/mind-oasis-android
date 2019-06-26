package xyz.thingapps.mind_oasis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_maxim.view.*
import xyz.thingapps.mind_oasis.R
import xyz.thingapps.mind_oasis.model.Maxim
import xyz.thingapps.mind_oasis.util.Dummies

class MaximTestAdapter : RecyclerView.Adapter<MaximTestAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return Dummies.dummyMaximList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_maxim, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Dummies.dummyMaximList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(maxim: Maxim) {
            this.itemView.maximTextView.text = maxim.body
            this.itemView.authorTextView.text = this.itemView.context
                    .getString(R.string.author_format, maxim.author)
        }
    }
}