package xyz.thingapps.thingphrase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.thingapps.thingphrase.data.AppDatabase
import xyz.thingapps.thingphrase.data.Bookmark
import xyz.thingapps.thingphrase.data.BookmarkRepository

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookmarkRepository
    val allBookmarks: LiveData<List<Bookmark>>

    init {
        val bookmarkDao = AppDatabase.getDatabase(application, viewModelScope).bookmarkDao()
        repository = BookmarkRepository(bookmarkDao)
        allBookmarks = repository.allBookmarks()
    }

    fun insert(bookmark: Bookmark) = viewModelScope.launch {
        repository.insert(bookmark)
    }

    fun delete(bookmark: Bookmark) = viewModelScope.launch {
        repository.delete(bookmark)
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
