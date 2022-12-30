package fr.danyhamimi.projet_hamimi_kaabeche

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Language::class], version = 1, exportSchema = false)
abstract class LanguageDatabase : RoomDatabase() {
    abstract val languageDao: LanguageDao
}