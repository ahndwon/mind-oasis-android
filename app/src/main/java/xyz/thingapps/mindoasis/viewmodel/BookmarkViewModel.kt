package xyz.thingapps.mindoasis.viewmodel

import androidx.lifecycle.ViewModel;
import xyz.thingapps.mindoasis.data.Bookmark
import xyz.thingapps.mindoasis.data.BookmarkRepository

class BookmarkViewModel internal constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    var bookmarkList: List<Bookmark> = emptyList()

    fun addBookmark(bookmark: Bookmark) {
        bookmarkRepository.insert(bookmark)
    }

    fun getBookmarks() {
        bookmarkList = bookmarkRepository.getBookmarks()
    }
}
