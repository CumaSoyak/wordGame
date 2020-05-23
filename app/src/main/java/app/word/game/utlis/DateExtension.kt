package app.word.game.utlis


import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun String.toDate(): Long {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).parse(this).time
}

fun Long.calculateTime(): Boolean {
    return (TimeUnit.MILLISECONDS.toHours(currentDate() - this)) <= 24
}

fun currentDate(): Long {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).format(Date())
        .toDate()
}
