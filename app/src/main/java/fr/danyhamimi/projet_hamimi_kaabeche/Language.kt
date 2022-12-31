package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Entity
import androidx.room.PrimaryKey

// This is the Entity class, which represents a table in the database
@Entity(tableName = "language_table")
data class Language(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val MotSource: String?,
    val MotDestination: String?,
    val LangueSource : String?,
    val LangueDestination : String?,
    val Lien: String,
    val NomDictionnaire: String,
    val isFileSaved: Boolean,
    val isInNotification: Boolean,
)


