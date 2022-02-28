package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Lesson(
    @SerializedName("Hours")
    val hours: String,
    @SerializedName("LessonTypeId")
    val lessonTypeId: String,
    @SerializedName("RealHours")
    val realHours: String
):Serializable