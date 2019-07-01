package xyz.thingapps.mindoasis.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_bookmark.view.*
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.data.Bookmark

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    var bookmarks: List<Bookmark> = emptyList()
    var onEnd: ((Int) -> Unit)? = null

    companion object {
        const val CLICK_INTERVAL_TIME = 2000L
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookmark, parent, false)
        )
    }

    @SuppressLint("ShowToast")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == bookmarks.size - 1) onEnd?.invoke(position + 1)
        holder.bind(bookmarks[position])
        holder.itemView.setOnClickListener {

        }
    }

    internal fun setBookmarks(bookmarks: List<Bookmark>) {
        this.bookmarks = bookmarks
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bookmark: Bookmark) {
            this.itemView.bookmarkTextView.text = bookmark.body
            this.itemView.authorTextView.text = this.itemView.context
                .getString(R.string.author_format, bookmark.author)
        }
    }
}