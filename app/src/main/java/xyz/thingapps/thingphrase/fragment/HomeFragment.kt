package xyz.thingapps.thingphrase.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_fragment.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import xyz.thingapps.thingphrase.R
import xyz.thingapps.thingphrase.adapter.MaximAdapter
import xyz.thingapps.thingphrase.data.Bookmark
import xyz.thingapps.thingphrase.model.Maxim
import xyz.thingapps.thingphrase.sharedApp
import xyz.thingapps.thingphrase.util.*
import xyz.thingapps.thingphrase.viewmodel.BookmarkViewModel
import xyz.thingapps.thingphrase.viewmodel.HomeViewModel
import java.util.*

class HomeFragment : Fragment(), AnkoLogger {
    private var backPressedTime = 0L

    private lateinit var viewModel: HomeViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var popupAd: InterstitialAd

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

        bookmarkViewModel =
            ViewModelProviders.of(this@HomeFragment).get(BookmarkViewModel::class.java)

        setupAdapter()
//        setupPopupAd()

        val lastUpdate = activity?.sharedApp?.maximUpdate ?: 0L
        val lastUpdateDay = Instant.ofEpochMilli(lastUpdate).atZone(ZoneId.systemDefault())

        val current = Instant.now().toEpochMilli()
        val currentUpdateDay = Instant.ofEpochMilli(current).atZone(ZoneId.systemDefault())

        val index = if (lastUpdateDay.isSameDay(currentUpdateDay)) {
            info("no update")
            activity?.sharedApp?.maximIndex ?: 0
        } else {
            (0..MAX_INDEX).random()
        }

        val onSuccess: (() -> Unit) =
            if (lastUpdateDay.isSameDay(currentUpdateDay)) {
                {
                    adapter.notifyDataSetChanged()
                }
            } else {
                {
                    activity?.sharedApp?.maximIndex = index
                    activity?.sharedApp?.maximUpdate = Instant.now().toEpochMilli()
                    adapter.notifyDataSetChanged()
                }
            }

        viewModel.getMaxim(index) {
            onSuccess.invoke()
        }

        val isTutorial = context?.sharedApp?.nextMaximTutorial ?: true

        if (isTutorial) {
            view?.handImageView?.visibility = View.VISIBLE
            view?.tutorialTextView?.visibility = View.VISIBLE

            ObjectAnimator.ofFloat(view?.handImageView, "translationY", 75f).apply {
                duration = 2000
                repeatMode = ValueAnimator.RESTART
                repeatCount = INFINITE
                start()
            }
        }

        // Data Insertion to FireStore
//        maximToFirestore("190628_maxim_db.csv", 0,300)

//        viewModel.getMaxims(disposeBag) {
//            adapter.notifyDataSetChanged()
//        }
    }

    private fun setupAdapter() {
        adapter.maximList = viewModel.maximList

//        adapter.onEnd = { position ->
//            viewModel.getMaxims(disposeBag) { listSize ->
//                adapter.notifyItemRangeChanged(position, listSize)
//            }
//        }

        adapter.onDoubleClick = { maxim ->
            bookmark(maxim)
//            showPopupAd()
        }

        adapter.onLongClick = { maxim ->
            val items = arrayOf("다른 명언 보기", "북마크 하기")

            this@HomeFragment.context?.let { context ->
                AlertDialog.Builder(
                    context
                ).setItems(items) { _, position ->
                    when (position) {
                        0 -> {
//                            showPopupAd()
                            val index = (0..MAX_INDEX).random()
                            activity?.sharedApp?.maximIndex = index
                            activity?.sharedApp?.maximUpdate = Instant.now().toEpochMilli()
                            resetFragment()
                        }

                        1 -> {
                            bookmark(maxim)
                        }
                    }
                }.show()
            }

            context?.sharedApp?.nextMaximTutorial = false
            view?.handImageView?.visibility = View.GONE
            view?.tutorialTextView?.visibility = View.GONE
        }
    }

    private fun showPopupAd() {
        this@HomeFragment.context?.let {
            AdUtil.showPopup(it, popupAd)
        }

//        activity?.sharedApp?.let {
//            Log.d(HomeFragment::class.java.name, "popupCount : ${it.popupCount}")
//            it.popupCount = (it.popupCount ?: 0) + 1
//            if ((it.popupCount ?: 0) % 5 != 0) return
//        }
//
//        popupAd.show()
    }

    private fun setupPopupAd() {
        if (context == null) return

        popupAd = InterstitialAd(context)
        popupAd.adUnitId = getString(R.string.admob_popup_id)
//        popupAd.adUnitId = getString(R.string.admob_test_popup_id)
        popupAd.loadAd(AdRequest.Builder().build())
    }

    private fun resetFragment() {
        fragmentManager?.fragments?.last()?.let { homeFragment ->
            fragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                ?.detach(homeFragment)
                ?.attach(homeFragment)
                ?.commit()

        }
    }

    private fun bookmark(maxim: Maxim) {
        bookmarkViewModel.insert(
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