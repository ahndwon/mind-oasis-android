package xyz.thingapps.mindoasis.util

import android.content.Context
import xyz.thingapps.mindoasis.data.AppDatabase
import xyz.thingapps.mindoasis.data.BookmarkRepository
import xyz.thingapps.mindoasis.viewmodel.BookmarkViewModelFactory

object InjectorUtils {

    private fun getBookmarkRepository(context: Context): BookmarkRepository {
        return BookmarkRepository.getInstance(AppDatabase.getInstance(context).bookmarkDao())
    }

    fun providePlantListViewModelFactory(context: Context): BookmarkViewModelFactory {
        val repository = getBookmarkRepository(context)
        return BookmarkViewModelFactory(repository)
    }
}