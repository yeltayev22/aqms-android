package kz.yeltayev.aqms.utils

/**
 * Wrapper class for Android's shared preferences.
 */
interface LocalStorage {
    fun getString(key: String, defaultValue: String? = null): String?
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun getLong(key: String, defaultValue: Long = 0): Long
    fun getStringList(key: String): List<String>

    /**
     * Put a string value into the local storage.
     * @param key The unique key of the entry
     * @param value The value. If null is passed, then the entry is removed.
     * @param sync If true, then the update is synchronously saved to the persistent storage.
     *  Otherwise the value is first saved into memory, and persisted asynchronously.
     */
    fun putString(key: String, value: String?, sync: Boolean = false)
    fun putBoolean(key: String, value: Boolean, sync: Boolean = false)
    fun putInt(key: String, value: Int?, sync: Boolean = false)
    fun putLong(key: String, value: Long, sync: Boolean = false)
    fun putStringList(key: String, list: List<String>, sync: Boolean = false)

    fun removeItem(key: String, sync: Boolean = false)
}