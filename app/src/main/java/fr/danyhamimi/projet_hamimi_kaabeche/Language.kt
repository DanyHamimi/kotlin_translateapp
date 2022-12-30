package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Entity
import androidx.room.PrimaryKey

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
)