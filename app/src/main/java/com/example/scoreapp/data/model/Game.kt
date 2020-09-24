package com.example.scoreapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ForeignKey(
        entity = Season::class,
        parentColumns = ["id"],
        childColumns = ["fkSeason"])
    val fkSeason: Long,
    val points: Int,
    val maxRecord: Boolean,
    val minRecord: Boolean
) {
}