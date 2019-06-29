package xyz.thingapps.mindoasis.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_fragment.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import xyz.thingapps.mindoasis.R
import xyz.thingapps.mindoasis.adapter.MaximAdapter
import xyz.thingapps.mindoasis.data.Bookmark
import xyz.thingapps.mindoasis.util.COLLECTION_MAXIM
import xyz.thingapps.mindoasis.util.InjectorUtils
import xyz.thingapps.mindoasis.util.MaximCsvReader
import xyz.thingapps.mindoasis.util.runOnIoThread
import xyz.thingapps.mindoasis.viewmodel.BookmarkViewModel
import xyz.thingapps.mindoasis.viewmodel.HomeViewModel
import java.util.*

class HomeFragment : Fragment(), AnkoLogger {
    private var backPressedTime = 0L

    private lateinit var viewModel: HomeViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private val adapter = MaximAdapter()
    private val disposeBag = CompositeDisposable()

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        view.maximRecyclerView.adapter = adapter
        view.maximRecyclerView.layoutManager = LinearLayoutManager(view.context).apply {
            this.orientation = LinearLayoutManager.HORIZONTAL
        }

        PagerSnapHelper().attachToRecyclerView(view.maximRecyclerView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this@HomeFragment).get(HomeViewModel::class.java)

        val factory = context?.let { InjectorUtils.providePlantListViewModelFactory(it) }
        bookmarkViewModel =
            ViewModelProviders.of(this@HomeFragment, factory).get(BookmarkViewModel::class.java)
        bookmarkViewModel

        adapter.maximList = viewModel.maximList
        adapter.onEnd = { position ->
            viewModel.getMaxims(disposeBag) { listSize ->
                adapter.notifyItemRangeChanged(position, listSize)
            }
        }

        adapter.onDoubleClick = { maxim ->
            runOnIoThread {
                bookmarkViewModel.addBookmark(
                    Bookmark(
                        maxim.id,
                        maxim.index,
                        maxim.body,
                        maxim.author,
                        maxim.category,
                        Calendar.getInstance().timeInMillis
                    )
                )
            }

        }

        // Data Insertion to FireStore
//        maximToFirestore("190628_maxim_db.csv", 0,300)

        viewModel.getMaxims(disposeBag) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun maximToFirestore(fileName: String, startIndex: Int, maximCount: Int) {
        val fireStore = FirebaseFirestore.getInstance()
        val maximCollection = fireStore.collection(COLLECTION_MAXIM)

        val inputStream = resources.assets.open(fileName)

        val list = MaximCsvReader().apply {
            this.startIndex = startIndex
            this.maximCount = maximCount
        }
            .read(inputStream)

        for (maxim in list) {
            maximCollection.add(maxim)
                .addOnSuccessListener {
                    info("addMaxim success")
                }
                .addOnFailureListener {
                    info("addMaxim failed :", it)
                }
        }
    }
}