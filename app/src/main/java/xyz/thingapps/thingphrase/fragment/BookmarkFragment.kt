package xyz.thingapps.thingphrase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.bookmark_fragment.view.*
import org.jetbrains.anko.AnkoLogger
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.adapter.BookmarkAdapter
import xyz.thingapps.thingphrase.viewmodel.BookmarkViewModel

class BookmarkFragment : Fragment(), AnkoLogger {
    val adapter = BookmarkAdapter()

    companion object {
        fun newInstance() = BookmarkFragment()
    }

    private lateinit var viewModel: BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bookmark_fragment, container, false)
        adapter.onLongClick = { bookmark ->
            context?.let { context ->
                androidx.appcompat.app.AlertDialog.Builder(context)
                    .setMessage("삭제 하시겠습니까?")
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.delete(bookmark)
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .create()
                    .show()
            }
        }

        view.bookmarkRecyclerView.adapter = adapter
        view.bookmarkRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this@BookmarkFragment).get(BookmarkViewModel::class.java)

        viewModel.allBookmarks.observe(this, Observer { bookmarks ->
            bookmarks?.let { adapter.setBookmarks(it) }
            if (bookmarks.isNotEmpty()) {
                view?.noBookmarksCardView?.visibility = View.GONE
            } else {
                view?.noBookmarksCardView?.visibility = View.VISIBLE
            }
        })
    }
}
