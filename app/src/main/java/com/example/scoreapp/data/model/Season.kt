package com.example.scoreapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Season(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0
)