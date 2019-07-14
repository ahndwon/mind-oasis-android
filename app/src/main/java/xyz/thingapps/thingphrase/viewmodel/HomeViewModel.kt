package xyz.thingapps.thingphrase.viewmodel

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import de.aaronoe.rxfirestore.getObservable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import xyz.thingapps.thingphrase.model.Maxim
import xyz.thingapps.thingphrase.util.*

class HomeViewModel : ObservableViewModel(), AnkoLogger {

    var maximList: ArrayList<Maxim> = arrayListOf()

    var maxim: Maxim = Maxim()
        set(value) {
            notifyChange()
            field = value
        }

    fun getMaxim(index: Int, onSuccess: ((QuerySnapshot) -> Unit)? = null) {
        FirebaseFirestore.getInstance()
            .collection(COLLECTION_MAXIM)
            .whereEqualTo(FIELD_INDEX, index)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.documents.isNotEmpty()) {
                    snapshot.documents[0].toObject(Maxim::class.java)?.let { it ->
                        if (maximList.isEmpty()) maximList.add(it)
                        else {
                            maximList.removeAt(0)
                            maximList.add(it)
                        }
                    }
                    onSuccess?.invoke(snapshot)
                }
            }
            .addOnFailureListener {
                info("getMaxim failed : ", it)
            }
    }


    fun getMaxims(
        disposeBag: CompositeDisposable,
        onSuccess: ((Int) -> Unit)? = null
    ) {

        val randomIndices = HashSet<Int>()
        for (i in 0..MAX_FETCH) {
            randomIndices.add((0..MAX_INDEX).random())
        }

        val observableList = ArrayList<Observable<List<Maxim>>>()
        for (index in randomIndices) {
            observableList.add(getMaximCollections(index))
        }

        Observable.zip(observableList) { args ->
            args.toList()
        }.subscribe({ lists ->
            val maxims = ArrayList<Maxim>()

            for (list in lists) {
                @Suppress("UNCHECKED_CAST")
                (list as? List<Maxim>)?.let {
                    maxims.addAll(it)
                }
            }

            maximList.addAll(maxims)
            onSuccess?.invoke(maxims.size)
        }, { e ->
            info("list Observable failed : ", e)
        }).addTo(disposeBag)

    }

    private fun getMaximCollections(index: Int): Observable<List<Maxim>> {
        return FirebaseFirestore.getInstance()
            .collection(COLLECTION_MAXIM)
            .whereEqualTo(FIELD_INDEX, index)
            .getObservable()
    }

}