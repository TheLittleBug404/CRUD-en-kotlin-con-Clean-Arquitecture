package com.example.database.data.users.dao.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,//uid
    val name:String,//name
    val age:Int//age
)