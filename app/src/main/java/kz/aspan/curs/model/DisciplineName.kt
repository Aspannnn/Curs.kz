package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DisciplineName(
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("nameKk")
    val nameKk: String,
    @SerializedName("nameRu")
    val nameRu: String
):Serializable