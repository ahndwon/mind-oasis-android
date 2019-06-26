package xyz.thingapps.mind_oasis.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.home_fragment.view.*
import xyz.thingapps.mind_oasis.R
import xyz.thingapps.mind_oasis.adapter.MaximTestAdapter

class HomeFragment : Fragment() {
    private var backPressedTime = 0L

    companion object {
        const val CLICK_INTERVAL_TIME = 2000L
    }

    @SuppressLint("ShowToast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        view.maximRecyclerView.adapter = MaximTestAdapter()
        view.maximRecyclerView.layoutManager = LinearLayoutManager(view.context).apply {
            this.orientation = LinearLayoutManager.HORIZONTAL
        }

//        LinearSnapHelper().attachToRecyclerView(view.maximRecyclerView)

        PagerSnapHelper().attachToRecyclerView(view.maximRecyclerView)


        return view
    }


}