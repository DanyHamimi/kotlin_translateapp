package fr.danyhamimi.projet_hamimi_kaabeche

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(
    private val sourceLanguages: ArrayList<String>,
    private val destinationLanguages: ArrayList<String>,
    private val words: ArrayList<String>,
    private val translatedWords: ArrayList<String>,
    private val webSite: ArrayList<String>,
    private val ids: ArrayList<String>,
    private val db: LanguageDatabase

) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceLanguageTextView: TextView = itemView.findViewById(R.id.source_language_text_view)
        val destinationLanguageTextView: TextView = itemView.findViewById(R.id.destination_language_text_view)
        val wordTextView: TextView = itemView.findViewById(R.id.word_text_view)
        val translatedWordTextView: TextView = itemView.findViewById(R.id.translated_word_text_view)
        val WebSiteView :TextView = itemView.findViewById(R.id.websiteName)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sourceLanguageTextView.text = "Langue d'origine : " + sourceLanguages[position]
        holder.destinationLanguageTextView.text = "Mot : " + destinationLanguages[position]
        holder.wordTextView.text = "Langue de traduction :" + words[position]
        holder.translatedWordTextView.text = "Mot traduit " + translatedWords[position]
        holder.WebSiteView.text = "Site web: " + webSite[position]

        holder.deleteButton.setOnClickListener {
            db.languageDao.deleteLanguageByMotSource(words[position],sourceLanguages[position],destinationLanguages[position])
            db.languageDao.deleteLanguageByMotDestination(words[position],destinationLanguages[position],sourceLanguages[position])
            sourceLanguages.removeAt(position)
            destinationLanguages.removeAt(position)
            words.removeAt(position)
            translatedWords.removeAt(position)
            webSite.removeAt(position)
            ids.removeAt(position)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return words.size
    }
}

