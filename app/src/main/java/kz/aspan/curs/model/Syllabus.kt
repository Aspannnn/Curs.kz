package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Syllabus(
    @SerializedName("AcademicYear")
    val academicYear: String,
    @SerializedName("AcademicYearId")
    val academicYearId: String,
    @SerializedName("DocumentURL")
    val documentURL: String,
    @SerializedName("IUPSid")
    val iUPSid: String,
    @SerializedName("Semesters")
    val semesters: List<Semester>,
    @SerializedName("Title")
    val title: String
) : Serializable