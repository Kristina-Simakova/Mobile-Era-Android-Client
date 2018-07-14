package rocks.mobileera.mobileera.utils

import android.content.Context

class Preferences(private var context: Context) {

    private val SHARED_PREFERENCES_KEY: String = "SHARED_PREFERENCES_KEY"

    private val SELECTED_TAGS: String = "SELECTED_TAGS"
    private val ALL_TAGS: String = "ALL_TAGS"
    private val SHOW_ONLY_FAVORITES: String = "SHOW_ONLY_FAVORITES"

    fun store(key: String, value: Boolean) {
        context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).
                edit().
                putBoolean(key, value).
                apply()
    }

    fun store(key: String, value: List<String>) {
        context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).
                edit().
                putString(key, value.joinToString(separator = "•")).
                apply()
    }

    fun read(key: String): Boolean {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getBoolean(key, false)
    }

    private fun readArray(key: String): ArrayList<String> {
        return ArrayList(context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getString(key, "").split("•"))
    }

    var showOnlyFavorite: Boolean
        get() = read(SHOW_ONLY_FAVORITES)
        set(value) {
            store(SHOW_ONLY_FAVORITES, value)
        }

    var allTags: List<String>
        get() = readArray(ALL_TAGS)
        set(value) {
            store(ALL_TAGS, value)
        }

    var selectedTags: ArrayList<String>
        get() = readArray(SELECTED_TAGS)
        set(value) {
            store(SELECTED_TAGS, value)
        }

    fun toggleShowOnlyFavorites() {
        showOnlyFavorite = !showOnlyFavorite
    }

    fun toggleSelectedTag(tag: String) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag)
        } else {
            selectedTags.add(tag) // TODO: test if store is being called
        }
    }
}