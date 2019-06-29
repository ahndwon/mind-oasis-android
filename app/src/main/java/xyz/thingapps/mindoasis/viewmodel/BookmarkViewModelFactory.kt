package xyz.thingapps.mindoasis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.thingapps.mindoasis.data.BookmarkRepository

class BookmarkViewModelFactory(
    private val repository: BookmarkRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = BookmarkViewModel(repository) as T
}
