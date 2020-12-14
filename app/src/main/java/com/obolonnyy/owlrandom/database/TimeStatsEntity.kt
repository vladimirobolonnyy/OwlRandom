package com.obolonnyy.owlrandom.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obolonnyy.owlrandom.model.TimeStats
import kotlinx.datetime.LocalDate

@Entity
class TimeStatsEntity(
    @PrimaryKey
    val date: String,
    val workedSeconds: Long
)

fun TimeStats.toEntity(): TimeStatsEntity {
    return TimeStatsEntity(
        date = this.date.toString(),
        workedSeconds = this.workedSeconds
    )
}

fun TimeStatsEntity.toModel(): TimeStats {
    return TimeStats(
        date = LocalDate.parse(date),
        workedSeconds = workedSeconds
    )
}