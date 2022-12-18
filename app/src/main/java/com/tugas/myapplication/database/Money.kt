package com.tugas.myapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Money(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "transaction")
    var transaction: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "income")
    var income: String = "",

    @ColumnInfo(name = "outcome")
    var outcome: String = ""


) : Parcelable