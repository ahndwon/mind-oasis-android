package xyz.thingapps.mindoasis.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.DocumentChange.Type.*
import com.google.firebase.firestore.EventListener
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(private var mQuery: Query) : RecyclerView.Adapter<VH>(),
        EventListener<QuerySnapshot> {
    private var mRegistration: ListenerRegistration? = null

    private val mSnapshots = ArrayList<DocumentSnapshot>()

    fun startListening() {
        if (mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this)
        }
    }

    fun stopListening() {
        if (mRegistration != null) {
            mRegistration!!.remove()
            mRegistration = null
        }

        mSnapshots.clear()
        notifyDataSetChanged()
    }

    override fun onEvent(queryDocumentSnapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        // Handle errors
        if (e != null) {
            Log.w(TAG, "onEvent:error", e)
            return
        }

        queryDocumentSnapshots?.let { snapshots ->
            // Dispatch the event
            for (change in snapshots.documentChanges) {
                // Snapshot of the changed document
                val snapshot = change.document

                when (change.type) {
                    ADDED ->
                        onDocumentAdded(change)
                    MODIFIED ->
                        onDocumentModified(change)
                    REMOVED ->
                        onDocumentRemoved(change)
                    else -> Log.w(TAG, "not available type")

                }
            }
        }

        onDataChanged()
    }

    fun setQuery(query: Query) {
        // Stop listening
        stopListening()

        // Clear existing data
        mSnapshots.clear()
        notifyDataSetChanged()

        // Listen to new query
        mQuery = query
        startListening()
    }

    override fun getItemCount(): Int {
        return mSnapshots.size
    }

    private fun onDocumentAdded(change: DocumentChange) {
        mSnapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    private fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mSnapshots[change.oldIndex] = change.document
            notifyItemChanged(change.oldIndex)
        } else {
            // Item changed and changed position
            mSnapshots.removeAt(change.oldIndex)
            mSnapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    private fun onDocumentRemoved(change: DocumentChange) {
        mSnapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

    protected fun getSnapshot(index: Int): DocumentSnapshot {
        return mSnapshots[index]
    }

    protected fun onError(e: FirebaseFirestoreException) {}

    private fun onDataChanged() {}

    companion object {

        private const val TAG = "Firestore Adapter"
    }
}