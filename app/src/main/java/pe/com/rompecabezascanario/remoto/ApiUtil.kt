package pe.com.rompecabezascanario.remoto

import pe.com.rompecabezascanario.servicios.CategoriaService
import pe.com.rompecabezascanario.servicios.RolService

object ApiUtil {
    //reemplazar localhost por tu direccion IP
    val API_URl="https://empresa-canario-backend.onrender.com/empcanario/"

    val categoriaService: CategoriaService?
        get() =RetrofitClient.getClient(API_URl)?.create(CategoriaService::class.java)

    val rolService: RolService?
        get() =RetrofitClient.getClient(API_URl)?.create(RolService::class.java)
}