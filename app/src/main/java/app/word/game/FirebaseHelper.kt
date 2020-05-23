package app.word.game

import app.word.game.CoreApp.Companion.db

class FirebaseHelper {
    fun isAppUpdate(update: (isUpdate: Boolean) -> Unit) {
        var isFirst = true
        val docRef = db.collection("update")
        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                if (!snapshot.documents.isNullOrEmpty()) {
                    val updateVersion = snapshot.documents.get(0)["lastVersion"].toString()
                    if (updateVersion > OtherUtils.versionNumber()!!) {
                        if (isFirst) {
                            update(true)
                            isFirst = false
                        }
                    } else {
                        if (isFirst) {
                            update(false)
                            isFirst = false
                        }
                    }
                }
            }
        }
    }

}