package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_table")
data class Language(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val MotFR: String,
    val MotAnglais: String,
    val MotEspagnol: String,
    val MotAllemand: String,
)