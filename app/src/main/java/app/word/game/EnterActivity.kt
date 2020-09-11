package app.word.game

import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import app.word.game.utlis.showError
import app.word.game.utlis.showSucces
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.soyak.extensions.utils.extensions.launchActivity
import kotlinx.android.synthetic.main.activity_enter.*
import java.util.*

class EnterActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    var category: String = ""
    var country: String = ""
    var options: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        db = FirebaseFirestore.getInstance()

        optionCountry.setOnClickListener {
            secCOuntry()
        }
        sec.setOnClickListener {
            secCategory()
        }
        option.setOnClickListener {
            option()
        }
        kaydet.setOnClickListener {
            savedQuestion()
        }
        clear.setOnClickListener {
            soru.text.clear()
            dogru.text.clear()
            yanlis.text.clear()
        }
        gec.setOnClickListener {
            launchActivity<MainActivity> { }
        }
    }

    fun option() {
        val popup = PopupMenu(this, sec)
        popup.menu.add(0, 2, 0, "İngilizce")
        popup.menu.add(0, 1, 0, "Kültür")
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            options = item?.itemId.toString()
            option.text = item?.title.toString()
            true
        }
        popup.show()
    }


    fun secCOuntry() {
        val popup = PopupMenu(this, sec)
        popup.menu.add(0, 1, 0, "tr")
        popup.menu.add(0, 2, 0, "en")
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            country = item?.title.toString()
            optionCountry.text = item?.title.toString()
            true
        }
        popup.show()
    }

    fun secCategory() {
        val popup = PopupMenu(this, sec)
        popup.menu.add(0, 1, 0, "Tarih")
        popup.menu.add(0, 2, 0, "Bilim")
        popup.menu.add(0, 3, 0, "Eglence")
        popup.menu.add(0, 4, 0, "Cografya")
        popup.menu.add(0, 5, 0, "Sanat")
        popup.menu.add(0, 6, 0, "Spor")
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            category = item?.itemId.toString()
            sec.text = item?.title.toString()
            true
        }
        popup.show()
    }


    fun savedQuestion() {

        var chooseOne: String
        var chooseTwo: String

        val uuid = UUID.randomUUID().toString()
        var random = (1..2).random()
        if (random == 1) {
            chooseOne = dogru.text.toString()
            chooseTwo = yanlis.text.toString()
        } else {
            chooseOne = yanlis.text.toString()
            chooseTwo = dogru.text.toString()
        }
        val question = hashMapOf(
            "question" to soru.text.toString(),
            "chooseOne" to chooseOne,
            "chooseTwo" to chooseTwo,
            "answer" to dogru.text.toString(),
            "option" to options + country
        )

        db.collection("question")
            .document("info")
            .collection(category)
            .document(uuid)
            .set(question, SetOptions.merge()).addOnSuccessListener {
                showSucces("Girildi")
                soru.text.clear()
                dogru.text.clear()
                yanlis.text.clear()
            }.addOnFailureListener {
                showError("Hata")
            }
    }
}
