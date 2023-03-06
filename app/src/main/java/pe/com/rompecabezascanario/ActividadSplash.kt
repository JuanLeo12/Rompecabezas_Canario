package pe.com.rompecabezascanario

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView

class ActividadSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.actividad_splash)

        //agregamos animaciones
        val animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)
        val img_logo: ImageView = findViewById(R.id.img_logo)
        val img_nombre: ImageView = findViewById(R.id.img_nombre)
        img_nombre.animation = animacion2
        img_logo.animation = animacion1

        Handler().postDelayed({
            val intent = Intent(this, ActividadIngreso::class.java)

//            val pairs: Array<Pair<View, String>?> = arrayOfNulls(2)
//            pairs[0] = Pair<View, String>(img_logo, "img_logo_trans")
//            pairs[1] = Pair<View, String>(img_nombre, "img_nombre_trans")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val options = ActivityOptions.makeSceneTransitionAnimation(this@ActividadSplash, *pairs)
//            }
            startActivity(intent)
            finish()
        }, 4000)


    }
}