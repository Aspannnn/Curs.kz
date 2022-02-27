package kz.aspan.curs.model


import com.google.gson.annotations.SerializedName

data class DisciplineName(
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("nameKk")
    val nameKk: String,
    @SerializedName("nameRu")
    val nameRu: String
)