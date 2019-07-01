package xyz.thingapps.mindoasis.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.bookmark_fragment.view.*
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.adapter.BookmarkAdapter
import xyz.thingapps.mindoasis.viewmodel.BookmarkViewModel

class BookmarkFragment : Fragment() {
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
        view.bookmarkRecyclerView.adapter = adapter
        view.bookmarkRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val factory =
//            context?.let { InjectorUtils.providePlantListViewModelFactory(it) }

        viewModel =
            ViewModelProviders.of(this@BookmarkFragment).get(BookmarkViewModel::class.java)
//        adapter.bookmarks = viewModel.bookmarkList

        viewModel.allBookmarks.observe(this, Observer { bookmarks ->
            bookmarks?.let { adapter.setBookmarks(it) }
        })

//        runOnIoThread {
//            viewModel.getBookmarks()
//            runOnUiThread {
//                adapter.notifyDataSetChanged()
//            }
//        }


    }
}
