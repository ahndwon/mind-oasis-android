package xyz.thingapps.mind_oasis.viewmodel

import androidx.databinding.Bindable
import xyz.thingapps.mind_oasis.model.Maxim
import xyz.thingapps.mind_oasis.util.ObservableViewModel

class HomeViewModel : ObservableViewModel() {

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

}