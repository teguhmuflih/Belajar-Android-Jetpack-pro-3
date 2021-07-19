package com.bangkit.bajp_3_film.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentity")
data class MovieEntity (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int?,

    @ColumnInfo(name = "poster")
    @NonNull
    var poster: String?,

    @ColumnInfo(name = "id")
    @NonNull
    var id: Int?,

    @ColumnInfo(name = "title")
    @NonNull
    var title: String?,

    @ColumnInfo(name = "date")
    @NonNull
    var date: String?,

    @ColumnInfo(name = "score")
    @NonNull
    var score: String?,

    @ColumnInfo(name = "overview")
    @NonNull
    var overview: String?,

    @NonNull
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)