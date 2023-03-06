package pe.com.rompecabezascanario.servicios

import pe.com.rompecabezascanario.clases.Rol
import retrofit2.Call
import retrofit2.http.*

interface RolService {
    @GET("rol")
    fun MostrarRol(): Call<List<Rol>?>?

    @GET("rol/custom")
    fun MostrarRolPersonalizado(): Call<List<Rol>?>?

    @POST("rol")
    fun RegistrarRol(@Body c: Rol?): Call<Rol?>?

    @PUT("rol/{id}")
    fun ActualizarRol(@Path("id") id:Long, @Body c: Rol?): Call<Rol?>?

    @DELETE("rol/{id}")
    fun ActualizarRol(@Path("id") id:Long): Call<Rol?>?
}