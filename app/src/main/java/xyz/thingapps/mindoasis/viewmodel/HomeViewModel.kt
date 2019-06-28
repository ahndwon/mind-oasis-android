package xyz.thingapps.mindoasis.viewmodel

import androidx.databinding.Bindable
import xyz.thingapps.mindoasis.model.Maxim
import xyz.thingapps.mindoasis.util.ObservableViewModel

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