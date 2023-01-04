package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.room.Room

class NotificationSender : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "DELETE_ACTION") {
            val a = intent.getIntExtra("IDAN", 0)
            println("Not working")
        }
        else{
            val preferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            var learningSourceLanguage = preferences.getString("learning_Sourcelanguage", "Anglais")
            var learningLanguage = preferences.getString("learning_language", "Francais")
            var NotificationToSend = preferences.getInt("words_per_wave",10)
            if (learningSourceLanguage == "Francais") {
                learningSourceLanguage = "fr"
            }
            if (learningSourceLanguage == "Anglais") {
                learningSourceLanguage = "en"
            }
            if (learningSourceLanguage == "Espagnol") {
                learningSourceLanguage = "es"
            }
            if (learningSourceLanguage == "Allemand") {
                learningSourceLanguage = "de"
            }
            if (learningLanguage == "Francais") {
                learningLanguage = "fr"
            }
            if (learningLanguage == "Anglais") {
                learningLanguage = "en"
            }
            if (learningLanguage == "Espagnol") {
                learningLanguage = "es"
            }
            if (learningLanguage == "Allemand") {
                learningLanguage = "de"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val db = Room.databaseBuilder(
                context,
                LanguageDatabase::class.java, "languageDB3"
            ).allowMainThreadQueries().build()

            val words = db.languageDao.getLanguageByLangueSource(learningSourceLanguage!!,learningLanguage!!)
            val currentNotifications = notificationManager.activeNotifications
            val notificationCount = currentNotifications.size
            db.close()
            if(words.size < NotificationToSend){
                NotificationToSend = words.size
            }
            if(notificationCount<NotificationToSend){
                for (i in 0 until NotificationToSend-notificationCount) {
                    val randomBis = (0..20000000).random()
                    intent.putExtra("notification_id", randomBis)// pour éviter d'erase une autre notif
                    val clickIntent = Intent(context, fr.danyhamimi.projet_hamimi_kaabeche.NotificationSender::class.java).putExtra("notification_id", i)
                    val random = words.random()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(random.Lien))
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_IMMUTABLE)
                    val intentDelete = Intent(context, fr.danyhamimi.projet_hamimi_kaabeche.NotificationSender::class.java).putExtra("IDAN", 33)
                    intentDelete.action = "DELETE_ACTION"
                    val deleteIntent = PendingIntent.getBroadcast(context, 0, intentDelete, PendingIntent.FLAG_IMMUTABLE)



                    val builder = NotificationCompat.Builder(context, "CHANNEL_IDDANY")
                        .setContentTitle(random.MotSource.toString())
                        .setContentText("Comment dit-t-on "+random.MotSource.toString() + " en "+ preferences.getString("learning_language", "Francais"))
                        .setSmallIcon(R.drawable.arrow)
                        .setContentIntent(pendingIntent)
                        .setDeleteIntent(deleteIntent)
                        .setAutoCancel(true)

                    clickIntent.putExtra("url",words.random().Lien.toString())
                    notificationManager.notify(randomBis, builder.build())
                }
            }
        }
        }


    companion object {
        const val NOTIFICATION_ID = 0
    }
}