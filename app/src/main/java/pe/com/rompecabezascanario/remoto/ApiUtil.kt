package pe.com.rompecabezascanario.remoto

import pe.com.rompecabezascanario.servicios.CategoriaService
import pe.com.rompecabezascanario.servicios.RolService

object ApiUtil {
    //reemplazar localhost por tu direccion IP
    val API_URl="http://192.168.18.99:8095/empcanario/"

    val categoriaService: CategoriaService?
        get() =RetrofitClient.getClient(API_URl)?.create(CategoriaService::class.java)

    val rolService: RolService?
        get() =RetrofitClient.getClient(API_URl)?.create(RolService::class.java)
}