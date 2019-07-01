package xyz.thingapps.mindoasis.data

import androidx.lifecycle.LiveData

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun allBookmarks(): LiveData<List<Bookmark>> {
        return bookmarkDao.getBookmarks()
    }

    suspend fun insert(bookmark: Bookmark) {
        bookmarkDao.insert(bookmark)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: BookmarkRepository? = null

        fun getInstance(bookmarkDao: BookmarkDao) =
            instance ?: synchronized(this) {
                instance ?: BookmarkRepository(bookmarkDao).also { instance = it }
            }
    }
}