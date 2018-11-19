package de.fxnn.brainfapp

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["fileName"], unique = true)
    ]
)
data class RecentFile(
    @PrimaryKey var id: Int?
) {
    constructor(fileName: String): this(null) {
        this.fileName = fileName
    }

    var fileName: String? = null
}