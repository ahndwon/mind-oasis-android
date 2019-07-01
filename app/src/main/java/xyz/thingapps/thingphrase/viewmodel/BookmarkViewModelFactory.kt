package xyz.thingapps.thingphrase.viewmodel

import androidx.lifecycle.ViewModelProvider
import xyz.thingapps.thingphrase.data.BookmarkRepository

class BookmarkViewModelFactory(
    private val repository: BookmarkRepository
) : ViewModelProvider.NewInstanceFactory() {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>) = BookmarkViewModel(repository) as T
}
