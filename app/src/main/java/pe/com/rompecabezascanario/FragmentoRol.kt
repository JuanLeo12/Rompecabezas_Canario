package pe.com.rompecabezascanario

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import pe.com.rompecabezascanario.adaptadores.AdaptadorRol
import pe.com.rompecabezascanario.clases.Rol
import pe.com.rompecabezascanario.remoto.ApiUtil
import pe.com.rompecabezascanario.servicios.RolService
import pe.com.rompecabezascanario.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoRol : Fragment() {
    private lateinit var txtRol: EditText
    private lateinit var chkEst: CheckBox
    private lateinit var lblCodRol: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstRol: ListView

    val objrol= Rol()

    private var cod=0
    private var nom=""
    private var est=false
    private var fila=-1

    private var rolService: RolService?=null

    private var registrorol:List<Rol>?=null

    var objutilidad= Util()

    var ft: FragmentTransaction?=null

    private var _binding: FragmentoRol? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val raiz=inflater.inflate(R.layout.fragmento_rol,container,false)
        //creamos los controles
        txtRol=raiz.findViewById(R.id.txtRol)
        chkEst=raiz.findViewById(R.id.chkEst)
        lblCodRol=raiz.findViewById(R.id.lblCodRol)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstRol=raiz.findViewById(R.id.lstRol)

        registrorol=ArrayList()

        //implementamos el servicio
        rolService= ApiUtil.rolService

        //mostramos las categorias
        MostrarRol(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtRol.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el rol")
                txtRol.requestFocus()
            }else{
                //capturando valores
                nom=txtRol.getText().toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objrol.rol=nom
                objrol.estado=est
                //llamamos al metodo para registrar
                RegistrarRol(raiz.context,objrol)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)
                //actualizamos el fragmento
                val frol=FragmentoRol()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,frol,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstRol.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).idrol)
            txtRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).rol)
            if((registrorol as ArrayList<Rol>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }
        })

        return raiz

    }

    fun MostrarRol(context: Context?){
        val call= rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object : Callback<List<Rol>?> {
            override fun onResponse(
                call: Call<List<Rol>?>,
                response: Response<List<Rol>?>
            ) {
                if(response.isSuccessful){
                    registrorol=response.body()
                    lstRol.adapter= AdaptadorRol(context,registrorol)
                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarRol(context: Context?, r: Rol?){
        val call= rolService!!.RegistrarRol(r)
        call!!.enqueue(object : Callback<Rol?> {
            override fun onResponse(call: Call<Rol?>, response: Response<Rol?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro el rol")
                }
            }

            override fun onFailure(call: Call<Rol?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}