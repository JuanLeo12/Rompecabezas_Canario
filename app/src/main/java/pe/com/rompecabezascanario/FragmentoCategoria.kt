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
import pe.com.rompecabezascanario.adaptadores.AdaptadorCategoria
import pe.com.rompecabezascanario.clases.Categoria
import pe.com.rompecabezascanario.remoto.ApiUtil
import pe.com.rompecabezascanario.servicios.CategoriaService
import pe.com.rompecabezascanario.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoCategoria : Fragment() {

    private lateinit var txtNom: EditText
    private lateinit var chkEst: CheckBox
    private lateinit var lblCodCat: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var lstCat: ListView

    val objcategoria= Categoria()

    private var cod=0
    private var nom=""
    private var est=false
    private var fila=-1

    private var categoriaService: CategoriaService?=null

    private var registrocategoria:List<Categoria>?=null

    var objutilidad=Util()

    var ft: FragmentTransaction?=null

    private var _binding: FragmentoCategoria? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz=inflater.inflate(R.layout.fragmento_categoria,container,false)
        //creamos los controles
        txtNom=raiz.findViewById(R.id.txtNom)
        chkEst=raiz.findViewById(R.id.chkEst)
        lblCodCat=raiz.findViewById(R.id.lblCodCat)
        btnRegistrar=raiz.findViewById(R.id.btnRegistrar)
        btnActualizar=raiz.findViewById(R.id.btnActualizar)
        btnEliminar=raiz.findViewById(R.id.btnEliminar)
        lstCat=raiz.findViewById(R.id.lstCat)

        registrocategoria=ArrayList()

        //implementamos el servicio
        categoriaService= ApiUtil.categoriaService

        //mostramos las categorias
        MostrarCategoria(raiz.context)

        //agregamos los eventos
        btnRegistrar.setOnClickListener {
            if(txtNom.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingrese el nombre")
                txtNom.requestFocus()
            }else{
                //capturando valores
                nom=txtNom.getText().toString()
                est=if(chkEst.isChecked){
                    true
                }else{
                    false
                }
                //enviamos los valores a la clase
                objcategoria.nombre=nom
                objcategoria.estado=est
                //llamamos al metodo para registrar
                RegistrarCategoria(raiz.context,objcategoria)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmCategoria) as ViewGroup)
                //actualizamos el fragmento
                val fcategoria=FragmentoCategoria()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,fcategoria,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstCat.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodCat.setText(""+(registrocategoria as ArrayList<Categoria>).get(fila).idcategoria)
            txtNom.setText(""+(registrocategoria as ArrayList<Categoria>).get(fila).nombre)
            if((registrocategoria as ArrayList<Categoria>).get(fila).estado){
                chkEst.setChecked(true)
            }else{
                chkEst.setChecked(false)
            }
        })

        return raiz

    }

    fun MostrarCategoria(context: Context?){
        val call= categoriaService!!.MostrarCategoriaPersonalizado()
        call!!.enqueue(object : Callback<List<Categoria>?> {
            override fun onResponse(
                call: Call<List<Categoria>?>,
                response: Response<List<Categoria>?>
            ) {
                if(response.isSuccessful){
                    registrocategoria=response.body()
                    lstCat.adapter= AdaptadorCategoria(context,registrocategoria)
                }
            }

            override fun onFailure(call: Call<List<Categoria>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar categoria
    fun RegistrarCategoria(context: Context?, c: Categoria?){
        val call= categoriaService!!.RegistrarCategoria(c)
        call!!.enqueue(object : Callback<Categoria?> {
            override fun onResponse(call: Call<Categoria?>, response: Response<Categoria?>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la categoria")
                }
            }

            override fun onFailure(call: Call<Categoria?>, t: Throwable) {
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