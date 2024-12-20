package iset.example.mindbalenceapp.models

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("_id") val id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("startDate") var date: String,
    @SerializedName("startTime") var time: String,
    @SerializedName("completed") var isCompleted: Boolean
)