package de.thro.packsimulator.service

interface APIService {

    @POST("/api/auth/register")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("/api/auth/{username}")
    suspend fun getUser(@Path("username") username: String): Response<User>

    @POST("/api/packs/open")
    suspend fun openPack(@Query("userId") userId: String, @Query("setId") setId: String): Response<List<Card>>
}
