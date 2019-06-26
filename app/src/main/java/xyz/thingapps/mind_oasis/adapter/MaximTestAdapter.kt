package xyz.thingapps.mind_oasis.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_maxim.view.*
import xyz.thingapps.mind_oasis.R
import xyz.thingapps.mind_oasis.fragment.HomeFragment
import xyz.thingapps.mind_oasis.model.Maxim
import xyz.thingapps.mind_oasis.util.Dummies
import xyz.thingapps.mind_oasis.util.showCenter

class MaximTestAdapter : RecyclerView.Adapter<MaximTestAdapter.ViewHolder>() {
    private var backPressedTime = 0L

    override fun getItemCount(): Int {
        return Dummies.dummyMaximList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_maxim, parent, false))
    }

    @SuppressLint("ShowToast")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Dummies.dummyMaximList[position])
        holder.itemView.setOnClickListener {
            val tempTime = System.currentTimeMillis()
            val intervalTime = tempTime - backPressedTime

            if (intervalTime in 0..HomeFragment.CLICK_INTERVAL_TIME) {
                Toast.makeText(it.context, "북마크 되었습니다", Toast.LENGTH_SHORT).showCenter()
            } else {
                backPressedTime = tempTime
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(maxim: Maxim) {
            this.itemView.maximTextView.text = maxim.body
            this.itemView.authorTextView.text = this.itemView.context
                    .getString(R.string.author_format, maxim.author)
        }
    }
}