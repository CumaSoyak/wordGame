package app.word.game.utlis

import android.content.Context
import android.content.SharedPreferences
import app.word.game.CoreApp
import com.google.gson.GsonBuilder
import com.soyak.extensions.utils.extensions.get
import com.soyak.extensions.utils.extensions.removeValue
import com.soyak.extensions.utils.extensions.setValue

object PrefUtils {
    private const val FIRST_TIME = "is_First_Time_Open"
    private const val PREF_NAME = "PREF_NAME"
    private const val LEVEL = "LEVEL"
    private const val PREMIUM = "PREMIUM"
    private const val TRUEPOINT = "TRUEPOINT"
    private const val FALSEPOINT = "FALSEPOINT"
    private const val HEART = "HEART"


    private val instance: SharedPreferences =
        CoreApp.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun checkIsFirstTimeOpen(): Boolean {
        val firstTime = instance.getBoolean(FIRST_TIME, true)
        if (firstTime) {
            instance.setValue(FIRST_TIME, false)
            return true
        }
        return false
    }


    fun getHeart(): Int {
        return instance.getInt(HEART, 0)
    }

    fun setHeart(heart: Int) {
        instance.setValue(HEART, getHeart() + heart)
    }

    fun setLevel(level: String) {
        instance.setValue(LEVEL, level)
    }

    fun getLevel(): String {
        if (instance.getString(LEVEL, null).isNullOrEmpty()) {
            return "Level: 1"
        } else {
            return "Level: " + GsonBuilder().create()
                .fromJson(instance.getString(LEVEL, ""), String::class.java)
        }
    }

    fun updatePremium(type: String) {
        instance.setValue(PREMIUM, type)

    }


    fun remove(key: String) {
        instance.removeValue(key)
    }

    fun get(key: String, value: Any?) {
        instance.get(key, value)
    }

    fun setPointTrue(category: String, point: String) {
        instance.setValue(category + TRUEPOINT, point)
    }

    fun setPointFalse(category: String, point: String) {
        instance.setValue(category + FALSEPOINT, point)
    }

    fun getPointTrue(category: String): String {
        if (instance.getString(category + TRUEPOINT, null).isNullOrEmpty()) {
            return "1"
        } else {
            return GsonBuilder().create()
                .fromJson(instance.getString(category + TRUEPOINT, ""), String::class.java)
        }
    }

    fun getPointFalse(category: String): String {
        if (instance.getString(category + FALSEPOINT, null).isNullOrEmpty()) {
            return "1"
        } else {
            return GsonBuilder().create()
                .fromJson(instance.getString(category + FALSEPOINT, ""), String::class.java)
        }
    }

    fun pointSumTrue(): Int {
        return getPointTrue("1").toInt() + getPointTrue("2").toInt() + getPointTrue("3").toInt() + getPointTrue(
            "4"
        ).toInt() + getPointTrue("4").toInt() + getPointTrue("5").toInt()
    }

    fun pointSumFalse(): Int {
        return getPointFalse("1").toInt() + getPointFalse("2").toInt() + getPointFalse("3").toInt() + getPointFalse(
            "4"
        ).toInt() + getPointFalse("4").toInt() + getPointFalse("5").toInt()
    }


}