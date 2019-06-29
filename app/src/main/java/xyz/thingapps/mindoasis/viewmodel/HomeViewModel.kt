package xyz.thingapps.mindoasis.viewmodel

import androidx.databinding.Bindable
import com.google.firebase.firestore.FirebaseFirestore
import de.aaronoe.rxfirestore.getObservable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import xyz.thingapps.mindoasis.model.Maxim
import xyz.thingapps.mindoasis.util.COLLECTION_MAXIM
import xyz.thingapps.mindoasis.util.FIELD_INDEX
import xyz.thingapps.mindoasis.util.ObservableViewModel

class HomeViewModel : ObservableViewModel(), AnkoLogger {

    var maximList: ArrayList<Maxim> = arrayListOf()

    var maxim: Maxim = Maxim()
        set(value) {
            notifyChange()
            field = value
        }

    @Bindable
    fun getBody(): String {
        return maxim.body
    }

    @Bindable
    fun getAuthor(): String {
        return maxim.author
    }

    @Bindable
    fun getCategory(): String {
        return maxim.category
    }

    fun getMaxims(
        disposeBag: CompositeDisposable,
        onSuccess: ((Int) -> Unit)? = null
    ) {

        val randomIndices = HashSet<Int>()
        for (i in 0..10) {
            randomIndices.add((0..300).random())
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

//        collectionReference
//            .limit(30L)
//            .get()
//            .addOnSuccessListener { snapshots ->
//                bookmarks.addAll(snapshots.toObjects(Maxim::class.java))
//                for (shot in snapshots) {
//                    info("getMaxim: " + shot.toObject(Maxim::class.java))
//                }
//                onSuccess?.invoke()
//            }
//            .addOnFailureListener {
//                info("getMaxim failed : ", it)
//            }
    }

    private fun getMaximCollections(index: Int): Observable<List<Maxim>> {
        return FirebaseFirestore.getInstance()
            .collection(COLLECTION_MAXIM)
            .whereEqualTo(FIELD_INDEX, index)
            .getObservable()
    }

}