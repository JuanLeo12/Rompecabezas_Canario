package pe.com.rompecabezascanario.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Categoria {
    @SerializedName("idcategoria")
    @Expose
    var idcategoria:Long=0
    @SerializedName("nombre")
    @Expose
    var nombre:String?=null
    @SerializedName("estado")
    @Expose
    var estado:Boolean=false

    //constructores
    constructor(){}
    constructor(idcategoria: Long, nombre: String?, estado: Boolean) {
        this.idcategoria = idcategoria
        this.nombre = nombre
        this.estado = estado
    }
}