package com.skyrin.qjm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.skyrin.qjm.data.bean.Sms
import com.skyrin.qjm.data.repository.ILocalSource
import com.skyrin.qjm.utils.SMS_READ
import com.skyrin.qjm.utils.SMS_UNREAD

@Dao
interface SmsDao: ILocalSource.SmsSource{
    @Query("SELECT * FROM sms_table ORDER BY date desc")
    override fun getSmsList(): LiveData<List<Sms>>

    @Query("select * from sms_table where if_read = $SMS_UNREAD order by date desc")
    override fun getUnreadSms(): LiveData<List<Sms>>

    @Query("select * from sms_table where if_read = $SMS_READ order by date desc")
    override fun getReadSms(): LiveData<List<Sms>>

    @Query("select * from sms_table where body like :keywords order by date desc")
    override fun getSmsListWithKeywords(keywords: String): LiveData<List<Sms>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(sms: Sms): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override  suspend fun insert(smsList: List<Sms>): List<Long>

    @Delete
    override suspend fun delete(sms: Sms): Int

    @Query("delete from sms_table where if_read = $SMS_READ")
    override suspend fun deleteReadSms()

    @Query("delete from sms_table where if_read = $SMS_UNREAD")
    override suspend fun deleteUnreadSms()

    @Query("delete from sms_table")
    override suspend fun deleteAllSms()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(sms: Sms): Int
}