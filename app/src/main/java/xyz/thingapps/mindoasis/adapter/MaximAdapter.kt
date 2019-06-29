package xyz.thingapps.mindoasis.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_maxim.view.*
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.model.Maxim
import xyz.thingapps.mindoasis.util.showCenter

class MaximAdapter : RecyclerView.Adapter<MaximAdapter.ViewHolder>() {
    private var backPressedTime = 0L
    var maximList: List<Maxim> = emptyList()
    var onEnd: ((Int) -> Unit)? = null
    var onDoubleClick: ((Maxim) -> Unit)? = null

    companion object {
        const val CLICK_INTERVAL_TIME = 2000L
    }

    override fun getItemCount(): Int {
        return maximList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_maxim, parent, false))
    }

    @SuppressLint("ShowToast")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == maximList.size - 1) onEnd?.invoke(position + 1)
        holder.bind(maximList[position])
        holder.itemView.setOnClickListener {
            val tempTime = System.currentTimeMillis()
            val intervalTime = tempTime - backPressedTime

            if (intervalTime in 0..CLICK_INTERVAL_TIME) {
                Toast.makeText(it.context, "북마크 되었습니다", Toast.LENGTH_SHORT).showCenter()
                onDoubleClick?.invoke(maximList[position])
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