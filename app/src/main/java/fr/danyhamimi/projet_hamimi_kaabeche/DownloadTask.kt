@file:Suppress("DEPRECATION")

package fr.danyhamimi.projet_hamimi_kaabeche

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class DownloadTask(private val filesDir: File, private val ListIDWords: ArrayList<Long>) : AsyncTask<LinkedList<URL>, Void, String>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: LinkedList<URL>): String {

        val urlList = params[0]
        for (i in urlList.indices) {
            try {
                val url = urlList[i]
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val inStream = connection.inputStream
                val html = readInputStream(inStream)
                val file = File(filesDir, "${ListIDWords[i]}.html")
                println("${ListIDWords[i]}.html")
                val outStream = FileOutputStream(file)
                outStream.write(html.toByteArray())
                outStream.close()
            }
            catch (e: java.lang.Exception){
                println("Impossible d'ajouter ce site")
            }

        }
        val length = urlList.size
        println("$length mots enregisgtres")

        return "Done"
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String) {
    }

    private fun readInputStream(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line)
            line = reader.readLine()
        }
        return sb.toString()
    }




}