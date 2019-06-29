package xyz.thingapps.mindoasis.data

class BookmarkRepository private constructor(private val bookmarkDao: BookmarkDao) {

    fun getBookmarks() = bookmarkDao.getBookmarks()
    fun insert(bookmark: Bookmark) = bookmarkDao.insert(bookmark)

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