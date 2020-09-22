package com.skyrin.qjm.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.data.dao.SmsDao
import com.skyrin.qjm.utils.DATABASE_NAME
import com.skyrin.qjm.utils.TAG
import com.skyrin.qjm.workers.SmsWorker

@Database(entities = [Sms::class],version = 2,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smsDao() : SmsDao

    companion object{
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table sms_table add column remarks text")
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // do something on database create
                        val request = OneTimeWorkRequestBuilder<SmsWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                        Log.d(TAG, "onCreate: >>>")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d(TAG, "onOpen: >>>")
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        Log.d(TAG, "onDestructiveMigration: >>>")
                    }
                }).addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}