package com.skyrin.qjm.data.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_table")
data class Sms(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    @ColumnInfo(name = "sender")
    var sender: String?,
    @ColumnInfo(name = "date")
    var date: Long?,
    @ColumnInfo(name = "body")
    var body: String?,
    @ColumnInfo(name = "code")
    var code: String?,
    @ColumnInfo(name = "code_type")
    var codeType: String?,
    @ColumnInfo(name = "brand")
    var brand: String?,
    @ColumnInfo(name = "if_read")
    var ifRead: Int?,
    @ColumnInfo(name = "sim_id")
    var simId: String?,
    @ColumnInfo(name = "remarks")
    var remarks: String?
)