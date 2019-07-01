package xyz.thingapps.thingphrase.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_fragment_credit.view.*
import kotlinx.android.synthetic.main.item_credit.view.*
import xyz.thingapps.thingphrase.R


class CreditDialogFragment : FullWidthDialogFragment() {
    private val credits = listOf(
        Credit("Retrofit", "Square"),
        Credit("OkHttp", "Square"),
        Credit("RxJava", "ReactiveX"),
        Credit("Google Play Services", "AOSP"),
        Credit("Gson", "Google Inc."),
        Credit("Crashlytics", "Google Inc."),
        Credit("Anko", "JetBrains"),
        Credit("Icons", "Freepik")
    )

    data class Credit(val name: String, val developer: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_credit, container, false)

        val adapter = CreditListAdapter(credits)
        view.creditRecyclerView.adapter = adapter
        view.creditRecyclerView.layoutManager = LinearLayoutManager(view.context)
//        view.creditRecyclerView.addItemDecoration(SimpleDividerItemDecoration(view.appContext))

        return view
    }


    class CreditListAdapter(private val credits: List<Credit>) :
        RecyclerView.Adapter<CreditListAdapter.CreditViewHolder>() {

        class CreditViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_credit, parent, false)
        )


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
            return CreditViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return credits.size
        }

        override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
            val item = credits[position]
            with(holder.itemView) {
                nameTextView.text = item.name
                developerTextView.text = item.developer
            }
        }
    }
}