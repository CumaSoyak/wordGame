package app.word.game.utlis

import android.content.Context
import android.content.SharedPreferences
import app.word.game.CoreApp
import app.word.game.model.Question
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
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
    private const val Option = "Option"
    private const val Question = "Question"


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
        if (getUnLimitedHeart()) {
            return +999
        } else {
            return instance.getInt(HEART, 3)
        }
    }

    fun setHeart(heart: Int) {
        instance.setValue(HEART, getHeart() + heart)
    }

    fun setLevel(level: Int) {
        instance.setValue(LEVEL, level)
    }

    fun getLevel(): Int {
        return instance.getInt(LEVEL, 1)
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

    fun setPointTrue(category: String, point: Int) {
        instance.setValue(category + TRUEPOINT, point + getPointTrue(category))
    }

    fun setPointFalse(category: String, point: Int) {
        instance.setValue(category + FALSEPOINT, point + getPointFalse(category))
    }

    fun getPointTrue(category: String): Int {
        return instance.getInt(category + TRUEPOINT, 0)
    }

    fun getPointFalse(category: String): Int {
        return instance.getInt(category + FALSEPOINT, 0)
    }

    fun progressCalculate(category: String): Int {
        var sumProg = getPointTrue(category) - getPointFalse(category)
        if (sumProg > 0) {
            return sumProg
        } else {
            return 0
        }
    }

    fun pointSumTrue(): Int {
        return getPointTrue("1") + getPointTrue("2") + getPointTrue("3") + getPointTrue(
            "4"
        ) + getPointTrue("4") + getPointTrue("5")
    }

    fun pointSumFalse(): Int {
        return getPointFalse("1") + getPointFalse("2") + getPointFalse("3") + getPointFalse(
            "4"
        ) + getPointFalse("4") + getPointFalse("5")
    }

    fun getOption(): Boolean {
        return instance.getBoolean(Option, false) //default culture
    }

    fun getOptionCategory(): String {
        if (getOption()) {
            return "2"  //english
        } else {
            return "1" //culture
        }
    }

    fun setOption(boolean: Boolean) {
        instance.setValue(Option, boolean)
    }

    fun setQuestion(mode:String,category: String, data: String) {
        instance.setValue(mode+category + Question, data)
    }

    fun getQuestion(category: String): ArrayList<Question>? {
        val turnsType = object : TypeToken<ArrayList<Question>>() {}.type
        val turns = Gson().fromJson<ArrayList<Question>>(
            instance.getString(category + Question, ""),
            turnsType
        )
        return turns
    }

    /**Premium*/
    private const val OFFLINE = "OFFLINE"
    private const val UnLimitedTime = "UnLimitedTime"
    private const val UnLimitedHeart = "UnLimitedHeart"
    private const val NotAds = "NotAds"
    private const val NotAdsAndOffline = "NotAdsAndOffline"

    fun getOffline(): Boolean {
        return instance.getBoolean(OFFLINE, false)
    }

    fun getUnLimitedTime(): Boolean {
        return instance.getBoolean(UnLimitedTime, false)
    }

    fun getUnLimitedHeart(): Boolean {
        return instance.getBoolean(UnLimitedHeart, false)
    }

    fun getNotAds(): Boolean {
        return instance.getBoolean(NotAds, false)
    }

    fun getNotAdsAndOffline(): Boolean {
        return instance.getBoolean(NotAdsAndOffline, false)
    }


    fun setOffline() {
        instance.setValue(OFFLINE, true)
    }

    fun setUnLimitedTime() {
        instance.setValue(UnLimitedTime, true)
    }

    fun setUnLimitedHeart() {
        instance.setValue(UnLimitedHeart, true)
    }

    fun setNotAds() {
        instance.setValue(NotAds, true)
    }

    fun setNotAdsAndOffline() {
        instance.setValue(NotAdsAndOffline, true)
    }


}