package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Semester(
    @SerializedName("Disciplines")
    val disciplines: List<Discipline>,
    @SerializedName("Number")
    val number: String
): Serializable