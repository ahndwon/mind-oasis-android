package xyz.thingapps.mindoasis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.thingapps.mindoasis.data.AppDatabase
import xyz.thingapps.mindoasis.data.Bookmark
import xyz.thingapps.mindoasis.data.BookmarkRepository

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookmarkRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allBookmarks: LiveData<List<Bookmark>>

    init {
        val bookmarkDao = AppDatabase.getDatabase(application, viewModelScope).bookmarkDao()
        repository = BookmarkRepository(bookmarkDao)
        allBookmarks = repository.allBookmarks()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(bookmark: Bookmark) = viewModelScope.launch {
        repository.insert(bookmark)
    }
}


//class BookmarkViewModel internal constructor(
//    private val bookmarkRepository: BookmarkRepository
//) : ViewModel() {
//    var bookmarkList: List<Bookmark> = emptyList()
//
//    fun addBookmark(bookmark: Bookmark) {
//        bookmarkRepository.insert(bookmark)
//    }
//
//    fun getBookmarks() {
//        bookmarkList = bookmarkRepository.getBookmarks()
//    }
//}
