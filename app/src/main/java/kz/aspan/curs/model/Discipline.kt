package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Discipline(
    @SerializedName("DisciplineId")
    val disciplineId: String,
    @SerializedName("DisciplineName")
    val disciplineName: DisciplineName,
    @SerializedName("Lesson")
    val lesson: List<Lesson>
) : Serializable