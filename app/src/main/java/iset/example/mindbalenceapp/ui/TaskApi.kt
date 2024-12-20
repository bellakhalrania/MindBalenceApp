package iset.example.mindbalenceapp.ui

import iset.example.mindbalenceapp.models.Task
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TaskApi {
    @GET("/getAllTasks")
    suspend fun getAllTask(): List<Task>

    @POST("/addTask ")
    suspend fun addTask(@Body task: Task): Task

    @PUT("/updateTask/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: Task): Task

    @DELETE("/deleteTask/{id}")
    suspend fun deleteTask(@Path("id") id: String)

    @PUT("/completeTask/{task.id}")
    suspend fun completeTask(@Path("id") id: Int): Task
}