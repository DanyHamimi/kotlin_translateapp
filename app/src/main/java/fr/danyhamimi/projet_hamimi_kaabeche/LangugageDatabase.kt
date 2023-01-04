package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

class LanguageItem(
    val MotSource: String?,
    val MotDestination: String?,
    val LangueSource : String?,
    val LangueDestination : String?,
    val Lien: String,
    val NomDictionnaire: String,
    val isFileSaved: Boolean,
    val isInNotification: Boolean,
)

@Entity(tableName = "language_table")
data class Language(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val MotSource: String?,
    val MotDestination: String?,
    val LangueSource : String?,
    val LangueDestination : String?,
    val Lien: String,
    val NomDictionnaire: String,
    val isFileSaved: Boolean,
    val isInNotification: Boolean,
)

@Database(entities = [Language::class], version = 1, exportSchema = false)
abstract class LanguageDatabase : RoomDatabase() {
    abstract val languageDao: LanguageDao
}