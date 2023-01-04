package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.room.Room
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")
        if(name!!.isEmpty()){
            InitializeDB()
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
            finish()
        }

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameNotif = "My Channel"
            val descriptionText = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_IDDANY", nameNotif, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notificationSenderIntent = Intent(this, NotificationSender::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationSenderIntent, PendingIntent.FLAG_IMMUTABLE)


        // Set the alarm to go off every second
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 10 * 1000 // 5 minutes in milliseconds
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval.toLong(), pendingIntent)
*/


        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun InitializeDB() {
        val db = Room.databaseBuilder(
            this,
            LanguageDatabase::class.java, "languageDB3"
        ).allowMainThreadQueries().build()

        val Language1 = LanguageItem("Bonjour","Hello","fr","en","https://www.wordreference.com/fren/bonjour","wordreference",false,false)
        val Language2 = LanguageItem("Merci","Thank you","fr","en","https://www.wordreference.com/fren/merci","wordreference",false,false)
        val Language3 = LanguageItem("Au revoir","Goodbye","fr","en","https://www.wordreference.com/fren/au%20revoir","wordreference",false,false)
        val Language4 = LanguageItem("Je m'appelle","My name is","fr","en","https://www.wordreference.com/fren/je%20m'appelle","wordreference",false,false)
        val Language5 = LanguageItem("Comment ça va?","How are you?","fr","en","https://www.wordreference.com/fren/comment%20%C3%A7a%20va?","wordreference",false,false)
        val Language6 = LanguageItem("Je vais bien, merci","I'm fine, thank you","fr","en","https://www.wordreference.com/fren/je%20vais%20bien%2C%20merci","wordreference",false,false)
        val Language7 = LanguageItem("Je suis désolé","I'm sorry","fr","en","https://www.wordreference.com/fren/je%20suis%20d%C3%A9sol%C3%A9","wordreference",false,false)
        val Language8 = LanguageItem("Je ne parle pas français","I don't speak french","fr","en","https://www.wordreference.com/fren/je%20ne%20parle%20pas%20fran%C3%A7ais","wordreference",false,false)
        val Language9 = LanguageItem("Je ne comprends pas","I don't understand","fr","en","https://www.wordreference.com/fren/je%20ne%20comprends%20pas","wordreference",false,false)
        val Language10 = LanguageItem("Je ne sais pas","I don't know","fr","en","https://www.wordreference.com/fren/je%20ne%20sais%20pas","wordreference",false,false)
        val Language11 = LanguageItem("Je ne connais pas","I don't know","fr","en","https://www.wordreference.com/fren/je%20ne%20connais%20pas","wordreference",false,false)
        val Language12 = LanguageItem("Coucou","Hi","fr","en","https://www.wordreference.com/fren/coucou","wordreference",false,false)
        val Language13 = LanguageItem("Hello","Bonjour","en","fr","https://www.wordreference.com/enfr/hello","wordreference",false,false)
        val Language14 = LanguageItem("Thank you","Merci","en","fr","https://www.wordreference.com/enfr/thank%20you","wordreference",false,false)
        val Language15 = LanguageItem("Goodbye","Au revoir","en","fr","https://www.wordreference.com/enfr/goodbye","wordreference",false,false)
        val Language16 = LanguageItem("My name is","Je m'appelle","en","fr","https://www.wordreference.com/enfr/my%20name%20is","wordreference",false,false)
        val Language17 = LanguageItem("How are you?","Comment ça va?","en","fr","https://www.wordreference.com/enfr/how%20are%20you?","wordreference",false,false)
        val Language18 = LanguageItem("I'm fine, thank you","Je vais bien, merci","en","fr","https://www.wordreference.com/enfr/i'm%20fine%2C%20thank%20you","wordreference",false,false)
        val Language19 = LanguageItem("I'm sorry","Je suis désolé","en","fr","https://www.wordreference.com/enfr/i'm%20sorry","wordreference",false,false)
        val Language20 = LanguageItem("I don't speak french","Je ne parle pas français","en","fr","https://www.wordreference.com/enfr/i%20don't%20speak%20french","wordreference",false,false)
        val Language21 = LanguageItem("I don't understand","Je ne comprends pas","en","fr","https://www.wordreference.com/enfr/i%20don't%20understand","wordreference",false,false)
        val Language22 = LanguageItem("I don't know","Je ne sais pas","en","fr","https://www.wordreference.com/enfr/i%20don't%20know","wordreference",false,false)
        val Language23 = LanguageItem("Hi","Coucou","en","fr","https://www.wordreference.com/enfr/hi","wordreference",false,false)
        val Language24 = LanguageItem("Bonjour","Hola","fr","es","https://www.wordreference.com/fres/bonjour","wordreference",false,false)
        val Language25 = LanguageItem("Merci","Gracias","fr","es","https://www.wordreference.com/fres/merci","wordreference",false,false)
        val Language26 = LanguageItem("Au revoir","Adiós","fr","es","https://www.wordreference.com/fres/au%20revoir","wordreference",false,false)
        val Language27 = LanguageItem("Je m'appelle","Me llamo","fr","es","https://www.wordreference.com/fres/je%20m'appelle","wordreference",false,false)
        val Language28 = LanguageItem("Comment ça va?","¿Cómo estás?","fr","es","https://www.wordreference.com/fres/comment%20%C3%A7a%20va?","wordreference",false,false)
        val Language29 = LanguageItem("Je vais bien, merci","Estoy bien, gracias","fr","es","https://www.wordreference.com/fres/je%20vais%20bien%2C%20merci","wordreference",false,false)
        val Language30 = LanguageItem("Je suis désolé","Lo siento","fr","es","https://www.wordreference.com/fres/je%20suis%20d%C3%A9sol%C3%A9","wordreference",false,false)
        val Language31 = LanguageItem("Je ne parle pas français","No hablo francés","fr","es","https://www.wordreference.com/fres/je%20ne%20parle%20pas%20fran%C3%A7ais","wordreference",false,false)
        val Language32 = LanguageItem("Je ne comprends pas","No entiendo","fr","es","https://www.wordreference.com/fres/je%20ne%20comprends%20pas","wordreference",false,false)
        val Language33 = LanguageItem("Je ne sais pas","No sé","fr","es","https://www.wordreference.com/fres/je%20ne%20sais%20pas","wordreference",false,false)
        val Language34 = LanguageItem("Coucou","Hola","fr","es","https://www.wordreference.com/fres/coucou","wordreference",false,false)
        val Language35 = LanguageItem("Bonjour","Hallo","fr","de","https://www.wordreference.com/frde/bonjour","wordreference",false,false)
        val Language36 = LanguageItem("Merci","Danke","fr","de","https://www.wordreference.com/frde/merci","wordreference",false,false)
        val Language37 = LanguageItem("Au revoir","Auf Wiedersehen","fr","de","https://www.wordreference.com/frde/au%20revoir","wordreference",false,false)
        val Language38 = LanguageItem("Je m'appelle","Ich heibe","fr","de","https://www.wordreference.com/frde/je%20m'appelle","wordreference",false,false)
        val Language39 = LanguageItem("Comment ça va?","Wie geht's?","fr","de","https://www.wordreference.com/frde/comment%20%C3%A7a%20va?","wordreference",false,false)
        val Language40 = LanguageItem("Je vais bien, merci","Mir geht's gut, danke","fr","de","https://www.wordreference.com/frde/je%20vais%20bien%2C%20merci","wordreference",false,false)
        val Language41 = LanguageItem("Je suis désolé","Es tut mir leid","fr","de","https://www.wordreference.com/frde/je%20suis%20d%C3%A9sol%C3%A9","wordreference",false,false)
        val Language42 = LanguageItem("Je ne parle pas français","Ich spreche kein Französisch","fr","de","https://www.wordreference.com/frde/je%20ne%20parle%20pas%20fran%C3%A7ais","wordreference",false,false)
        val Language43 = LanguageItem("Je ne comprends pas","Ich verstehe nicht","fr","de","https://www.wordreference.com/frde/je%20ne%20comprends%20pas","wordreference",false,false)
        val Language44 = LanguageItem("Je ne sais pas","Ich weib nicht","fr","de","https://www.wordreference.com/frde/je%20ne%20sais%20pas","wordreference",false,false)
        val Language45 = LanguageItem("Coucou","Hallo","fr","de","https://www.wordreference.com/frde/coucou","wordreference",false,false)
        val Language46 = LanguageItem("Hola","Bonjour","es","fr","https://www.wordreference.com/esfr/hola","wordreference",false,false)
        val Language47 = LanguageItem("Gracias","Merci","es","fr","https://www.wordreference.com/esfr/gracias","wordreference",false,false)
        val Language48 = LanguageItem("Adiós","Au revoir","es","fr","https://www.wordreference.com/esfr/adi%C3%B3s","wordreference",false,false)
        val Language49 = LanguageItem("Me llamo","Je m'appelle","es","fr","https://www.wordreference.com/esfr/me%20llamo","wordreference",false,false)
        val Language50 = LanguageItem("¿Cómo estás?","Comment ça va?","es","fr","https://www.wordreference.com/esfr/c%C3%B3mo%20est%C3%A1s?","wordreference",false,false)
        val Language51 = LanguageItem("Estoy bien, gracias","Je vais bien, merci","es","fr","https://www.wordreference.com/esfr/estoy%20bien%2C%20gracias","wordreference",false,false)
        val Language52 = LanguageItem("Lo siento","Je suis désolé","es","fr","https://www.wordreference.com/esfr/lo%20siento","wordreference",false,false)
        val Language53 = LanguageItem("No hablo francés","Je ne parle pas français","es","fr","https://www.wordreference.com/esfr/no%20hablo%20franc%C3%A9s","wordreference",false,false)
        val Language54 = LanguageItem("No entiendo","Je ne comprends pas","es","fr","https://www.wordreference.com/esfr/no%20entiendo","wordreference",false,false)
        val Language55 = LanguageItem("No sé","Je ne sais pas","es","fr","https://www.wordreference.com/esfr/no%20s%C3%A9","wordreference",false,false)
        val Language56 = LanguageItem("Hola","Coucou","es","fr","https://www.wordreference.com/esfr/hola","wordreference",false,false)
        val Language57 = LanguageItem("Hola","Hello","es","en","https://www.wordreference.com/esen/hola","wordreference",false,false)
        val Language58 = LanguageItem("Gracias","Thank you","es","en","https://www.wordreference.com/esen/gracias","wordreference",false,false)
        val Language59 = LanguageItem("Adiós","Goodbye","es","en","https://www.wordreference.com/esen/adi%C3%B3s","wordreference",false,false)
        val Language60 = LanguageItem("Me llamo","My name is","es","en","https://www.wordreference.com/esen/me%20llamo","wordreference",false,false)
        val Language61 = LanguageItem("¿Cómo estás?","How are you?","es","en","https://www.wordreference.com/esen/c%C3%B3mo%20est%C3%A1s?","wordreference",false,false)
        val Language62 = LanguageItem("Estoy bien, gracias","I'm fine, thank you","es","en","https://www.wordreference.com/esen/estoy%20bien%2C%20gracias","wordreference",false,false)
        val Language63 = LanguageItem("Lo siento","I'm sorry","es","en","https://www.wordreference.com/esen/lo%20siento","wordreference",false,false)
        val Language64 = LanguageItem("No hablo francés","I don't speak French","es","en","https://www.wordreference.com/esen/no%20hablo%20franc%C3%A9s","wordreference",false,false)
        val Language65 = LanguageItem("No entiendo","I don't understand","es","en","https://www.wordreference.com/esen/no%20entiendo","wordreference",false,false)
        val Language66 = LanguageItem("No sé","I don't know","es","en","https://www.wordreference.com/esen/no%20s%C3%A9","wordreference",false,false)
        val Language67 = LanguageItem("Hola","Guten Tag","es","de","https://www.wordreference.com/esde/hola","wordreference",false,false)
        val Language68 = LanguageItem("Gracias","Danke","es","de","https://www.wordreference.com/esde/gracias","wordreference",false,false)
        val Language69 = LanguageItem("Adiós","Auf Wiedersehen","es","de","https://www.wordreference.com/esde/adi%C3%B3s","wordreference",false,false)
        val Language70 = LanguageItem("Me llamo","Ich heibe","es","de","https://www.wordreference.com/esde/me%20llamo","wordreference",false,false)
        val Language71 = LanguageItem("¿Cómo estás?","Wie geht es dir?","es","de","https://www.wordreference.com/esde/c%C3%B3mo%20est%C3%A1s?","wordreference",false,false)
        val Language72 = LanguageItem("Estoy bien, gracias","Ich bin gut, danke","es","de","https://www.wordreference.com/esde/estoy%20bien%2C%20gracias","wordreference",false,false)
        val Language73 = LanguageItem("Lo siento","Es tut mir leid","es","de","https://www.wordreference.com/esde/lo%20siento","wordreference",false,false)
        val Language74 = LanguageItem("No hablo francés","Ich spreche kein Französisch","es","de","https://www.wordreference.com/esde/no%20hablo%20franc%C3%A9s","wordreference",false,false)
        val Language75 = LanguageItem("No entiendo","Ich verstehe nicht","es","de","https://www.wordreference.com/esde/no%20entiendo","wordreference",false,false)
        val Language76 = LanguageItem("No sé","Ich weib nicht","es","de","https://www.wordreference.com/esde/no%20s%C3%A9","wordreference",false,false)
        val Language77 = LanguageItem("Hallo","Hello","de","en","https://www.wordreference.com/deen/hallo","wordreference",false,false)
        val Language78 = LanguageItem("Danke","Thank you","de","en","https://www.wordreference.com/deen/danke","wordreference",false,false)
        val Language79 = LanguageItem("Auf Wiedersehen","Goodbye","de","en","https://www.wordreference.com/deen/auf%20wiedersehen","wordreference",false,false)
        val Language80 = LanguageItem("Ich heibe","My name is","de","en","https://www.wordreference.com/deen/ich%20heibe","wordreference",false,false)
        val Language81 = LanguageItem("Wie geht es dir?","How are you?","de","en","https://www.wordreference.com/deen/wie%20geht%20es%20dir?","wordreference",false,false)
        val Language82 = LanguageItem("Ich bin gut, danke","I'm fine, thank you","de","en","https://www.wordreference.com/deen/ich%20bin%20gut%2C%20danke","wordreference",false,false)
        val Language83 = LanguageItem("Es tut mir leid","I'm sorry","de","en","https://www.wordreference.com/deen/es%20tut%20mir%20leid","wordreference",false,false)
        val Language84 = LanguageItem("Ich spreche kein Französisch","I don't speak French","de","en","https://www.wordreference.com/deen/ich%20spreche%20kein%20franz%C3%B6sisch","wordreference",false,false)
        val Language85 = LanguageItem("Ich verstehe nicht","I don't understand","de","en","https://www.wordreference.com/deen/ich%20verstehe%20nicht","wordreference",false,false)
        val Language86 = LanguageItem("Ich weib nicht","I don't know","de","en","https://www.wordreference.com/deen/ich%20weib%20nicht","wordreference",false,false)
        val Language87 = LanguageItem("Hello","Guten Tag","en","de","https://www.wordreference.com/ende/hello","wordreference",false,false)
        val Language88 = LanguageItem("Thank you","Danke","en","de","https://www.wordreference.com/ende/thank%20you","wordreference",false,false)
        val Language89 = LanguageItem("Goodbye","Auf Wiedersehen","en","de","https://www.wordreference.com/ende/goodbye","wordreference",false,false)
        val Language90 = LanguageItem("My name is","Ich heibe","en","de","https://www.wordreference.com/ende/my%20name%20is","wordreference",false,false)
        val Language91 = LanguageItem("How are you?","Wie geht es dir?","en","de","https://www.wordreference.com/ende/how%20are%20you?","wordreference",false,false)
        val Language92 = LanguageItem("I'm fine, thank you","Ich bin gut, danke","en","de","https://www.wordreference.com/ende/i'm%20fine%2C%20thank%20you","wordreference",false,false)
        val Language93 = LanguageItem("I'm sorry","Es tut mir leid","en","de","https://www.wordreference.com/ende/i'm%20sorry","wordreference",false,false)
        val Language94 = LanguageItem("I don't speak French","Ich spreche kein Französisch","en","de","https://www.wordreference.com/ende/i%20don't%20speak%20french","wordreference",false,false)
        val Language95 = LanguageItem("I don't understand","Ich verstehe nicht","en","de","https://www.wordreference.com/ende/i%20don't%20understand","wordreference",false,false)
        val Language96 = LanguageItem("I don't know","Ich weib nicht","en","de","https://www.wordreference.com/ende/i%20don't%20know","wordreference",false,false)
        val Language97 = LanguageItem("Hello","Buenos días","en","es","https://www.wordreference.com/enes/hello","wordreference",false,false)
        val Language98 = LanguageItem("Thank you","Gracias","en","es","https://www.wordreference.com/enes/thank%20you","wordreference",false,false)
        val Language99 = LanguageItem("Goodbye","Adiós","en","es","https://www.wordreference.com/enes/goodbye","wordreference",false,false)
        val Language100 = LanguageItem("My name is","Me llamo","en","es","https://www.wordreference.com/enes/my%20name%20is","wordreference",false,false)
        val Language101 = LanguageItem("How are you?","¿Cómo estás?","en","es","https://www.wordreference.com/enes/how%20are%20you?","wordreference",false,false)
        val Language102 = LanguageItem("I'm fine, thank you","Estoy bien, gracias","en","es","https://www.wordreference.com/enes/i'm%20fine%2C%20thank%20you","wordreference",false,false)
        val Language103 = LanguageItem("I'm sorry","Lo siento","en","es","https://www.wordreference.com/enes/i'm%20sorry","wordreference",false,false)
        val Language104 = LanguageItem("I don't speak French","No hablo francés","en","es","https://www.wordreference.com/enes/i%20don't%20speak%20french","wordreference",false,false)
        val Language105 = LanguageItem("I don't understand","No entiendo","en","es","https://www.wordreference.com/enes/i%20don't%20understand","wordreference",false,false)
        val Language106 = LanguageItem("I don't know","No sé","en","es","https://www.wordreference.com/enes/i%20don't%20know","wordreference",false,false)
        val Language107 = LanguageItem("Hallo","Hola","de","es","https://www.wordreference.com/dees/hallo","wordreference",false,false)
        val Language108 = LanguageItem("Danke","Gracias","de","es","https://www.wordreference.com/dees/danke","wordreference",false,false)
        val Language109 = LanguageItem("Auf Wiedersehen","Adiós","de","es","https://www.wordreference.com/dees/auf%20wiedersehen","wordreference",false,false)
        val Language110 = LanguageItem("Ich heibe","Me llamo","de","es","https://www.wordreference.com/dees/ich%20heibe","wordreference",false,false)
        val Language111 = LanguageItem("Wie geht es dir?","¿Cómo estás?","de","es","https://www.wordreference.com/dees/wie%20geht%20es%20dir?","wordreference",false,false)
        val Language112 = LanguageItem("Ich bin gut, danke","Estoy bien, gracias","de","es","https://www.wordreference.com/dees/ich%20bin%20gut%2C%20danke","wordreference",false,false)
        val Language113 = LanguageItem("Es tut mir leid","Lo siento","de","es","https://www.wordreference.com/dees/es%20tut%20mir%20leid","wordreference",false,false)
        val Language114 = LanguageItem("Ich spreche kein Französisch","No hablo francés","de","es","https://www.wordreference.com/dees/ich%20spreche%20kein%20französisch","wordreference",false,false)
        val Language115 = LanguageItem("Ich verstehe nicht","No entiendo","de","es","https://www.wordreference.com/dees/ich%20verstehe%20nicht","wordreference",false,false)
        val Language116 = LanguageItem("Ich weib nicht","No sé","de","es","https://www.wordreference.com/dees/ich%20weib%20nicht","wordreference",false,false)
        val Language117 = LanguageItem("Hallo","Bonjour","de","fr","https://www.wordreference.com/defr/hallo","wordreference",false,false)
        val Language118 = LanguageItem("Danke","Merci","de","fr","https://www.wordreference.com/defr/danke","wordreference",false,false)
        val Language119 = LanguageItem("Auf Wiedersehen","Au revoir","de","fr","https://www.wordreference.com/defr/auf%20wiedersehen","wordreference",false,false)
        val Language120 = LanguageItem("Ich heibe","Je m'appelle","de","fr","https://www.wordreference.com/defr/ich%20heibe","wordreference",false,false)
        val Language121 = LanguageItem("Wie geht es dir?","Comment allez-vous?","de","fr","https://www.wordreference.com/defr/wie%20geht%20es%20dir?","wordreference",false,false)
        val Language122 = LanguageItem("Ich bin gut, danke","Je vais bien, merci","de","fr","https://www.wordreference.com/defr/ich%20bin%20gut%2C%20danke","wordreference",false,false)
        val Language123 = LanguageItem("Es tut mir leid","Je suis désolé","de","fr","https://www.wordreference.com/defr/es%20tut%20mir%20leid","wordreference",false,false)
        val Language124 = LanguageItem("Ich spreche kein Französisch","Je ne parle pas français","de","fr","https://www.wordreference.com/defr/ich%20spreche%20kein%20französisch","wordreference",false,false)
        val Language125 = LanguageItem("Ich verstehe nicht","Je ne comprends pas","de","fr","https://www.wordreference.com/defr/ich%20verstehe%20nicht","wordreference",false,false)
        val Language126 = LanguageItem("Ich weib nicht","Je ne sais pas","de","fr","https://www.wordreference.com/defr/ich%20weib%20nicht","wordreference",false,false)
        Toast.makeText(this, "Initialisation de la bdd..", Toast.LENGTH_SHORT).show()
        db.languageDao.insertAll(Language1,Language2,Language3,Language4,Language5,Language6,Language7,Language8,Language9,Language10,Language11,Language12,Language13,Language14,Language15,Language16,Language17,Language18,Language19,Language20,Language21,Language22,Language23,Language24,Language25,Language26,Language27,Language28,Language29,Language30,Language31,Language32,Language33,Language34,Language35,Language36,Language37,Language38,Language39,Language40,Language41,Language42,Language43,Language44,Language45,Language46,Language47,Language48,Language49,Language50,Language51,Language52,Language53,Language54,Language55,Language56,Language57,Language58,Language59,Language60,Language61,Language62,Language63,Language64,Language65,Language66,Language67,Language68,Language69,Language70,Language71,Language72,Language73,Language74,Language75,Language76,Language77,Language78,Language79,Language80,Language81,Language82,Language83,Language84,Language85,Language86,Language87,Language88,Language89,Language90,Language91,Language92,Language93,Language94,Language95,Language96,Language97,Language98,Language99,Language100,Language101,Language102,Language103,Language104,Language105,Language106,Language107,Language108,Language109,Language110,Language111,Language112,Language113,Language114,Language115,Language116,Language117,Language118,Language119,Language120,Language121,Language122,Language123,Language124,Language125,Language126)
        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}