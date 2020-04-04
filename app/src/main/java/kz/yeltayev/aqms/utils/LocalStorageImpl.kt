package kz.yeltayev.aqms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

const val AQMS_PREFERENCES = "AQMS_PREFERENCES"

class LocalStorageImpl(
    context: Context
) : LocalStorage {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(AQMS_PREFERENCES, Context.MODE_PRIVATE)

    override fun getStringList(key: String): List<String> {
        val stringValue = getString(key, null)
        if (stringValue.isNullOrEmpty()) {
            return emptyList()
        }

        return stringValue.split(",").map {
            return@map it
        }
    }

    override fun getString(key: String, defaultValue: String?): String? {
        val value = prefs.all[key]
        return value as? String ?: defaultValue
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val value = prefs.all[key]
        return value as? Boolean ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = prefs.all[key]
        return value as? Int ?: defaultValue
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        val value = prefs.all[key]
        return value as? Long ?: defaultValue
    }

    override fun putStringList(key: String, list: List<String>, sync: Boolean) {
        putString(key, list.joinToString(","), sync)
    }

    override fun putString(key: String, value: String?, sync: Boolean) {
        if (value == null) {
            removeItem(key, sync)
        } else {
            updateValue(sync) { it.putString(key, value) }
        }
    }

    override fun putBoolean(key: String, value: Boolean, sync: Boolean) {
        updateValue(sync) { it.putBoolean(key, value) }
    }

    override fun putInt(key: String, value: Int?, sync: Boolean) {
        if (value != null) {
            updateValue(sync) { it.putInt(key, value) }
        } else {
            removeItem(key, sync)
        }
    }

    override fun putLong(key: String, value: Long, sync: Boolean) {
        updateValue(sync) { it.putLong(key, value) }
    }

    @SuppressLint("ApplySharedPref")
    override fun removeItem(key: String, sync: Boolean) {
        val editor = prefs.edit()
            .remove(key)

        if (sync) {
            editor.commit()
        } else {
            editor.apply()
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun updateValue(sync: Boolean, updateFn: (SharedPreferences.Editor) -> Unit) {
        prefs.edit().let { editor ->
            updateFn(editor)
            if (sync) {
                editor.commit()
            } else {
                editor.apply()
            }
        }
    }
}