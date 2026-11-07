package com.example.database

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.database.data.AppDataBase
import com.example.database.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val editName = findViewById<EditText>(R.id.editName)
        editName.filters = arrayOf()
        val editAge = findViewById<EditText>(R.id.editAge)
        val editCel = findViewById<EditText>(R.id.editCel)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val textList = findViewById<TextView>(R.id.textList)

        val db = AppDataBase.getDataBase(this)
        val userDao = db.userDao()

        //al iniciar la app cargar los datos existentes
        lifecycleScope.launch {
            val users = withContext(Dispatchers.IO){ userDao.getAll()}
            textList.text = users.joinToString("\n") { "${it.name} - ${it.age} años" }
        }
        // al hacer clic en el boton Guardar
        buttonSave.setOnClickListener {
            val name = editName.text.toString()
            val ageText = editAge.text.toString()
            //validar datos
            if(name.isBlank() || ageText.isBlank()){
                return@setOnClickListener
            }
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    userDao.insertarUsuario(User(name = name,age = ageText.toInt()))
                }
                //limpiar los campos
                editName.text.clear()
                editAge.text.clear()
                //recargar la lista
                val users = withContext(Dispatchers.IO){userDao.getAll()}
                textList.text = users.joinToString("\n") { "${it.name} - ${it.age} años" }
            }
        }
    }
}

