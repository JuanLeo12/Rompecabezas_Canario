package pe.com.rompecabezascanario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import pe.com.rompecabezascanario.utilidad.Util

class ActividadIngreso : AppCompatActivity() {

    private val objutilidad= Util()
    //declaramos variales
    private var usu=""
    private var cla=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_ingreso)

        //declarando los controles
        val txtUsu=findViewById<EditText>(R.id.txtInputUsu)
        val txtCla=findViewById<EditText>(R.id.txtInputCla)
        val btnIngresar=findViewById<Button>(R.id.btnIngresar)
        val btnSalir=findViewById<Button>(R.id.btnSalir)


        //agregamos eventos a los botones
        btnIngresar.setOnClickListener {
            if(txtUsu.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese el usuario")
                txtUsu.requestFocus()
            }else if(txtCla.getText().toString()==""){
                objutilidad.MensajeToast(this,"Ingrese la clave")
                txtCla.requestFocus()
            }else{
                usu=txtUsu.getText().toString()
                cla=txtCla.getText().toString()
                if(usu.equals("jleonardo")&&cla.equals("123456")){
                    objutilidad.MensajeToast(this,"Bienvenidos al Sistema")
                    val formulario=Intent(this,ActividadPrincipal::class.java)
                    startActivity(formulario)
                    this.finish()
                }else{
                    objutilidad.MensajeToast(this,"Usuario o Clave no valido")
                    objutilidad.Limpiar((findViewById<View>(R.id.frmIngreso)as ViewGroup))
                    txtUsu.requestFocus()

                }
            }
        }

        btnSalir.setOnClickListener {
            objutilidad.SalirSistema(this)
        }
    }
}