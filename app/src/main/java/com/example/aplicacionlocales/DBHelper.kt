package com.example.aplicacionlocales
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class DBHelper(context: Context) : SQLiteOpenHelper(context,"LocalesDB",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE locales (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT,
            precio REAL,
            direccion TEXT,
            imagen BLOB
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS locales")
        onCreate(db)
    }

    fun insertarLocales(nombre:String, precio:Double, direccion: String, imagen: Bitmap?, db:SQLiteDatabase):Long {
        val valores = ContentValues()
        valores.put("nombre",nombre)
        valores.put("precio",precio)
        valores.put("direccion",direccion)

        if (imagen != null) {
            val stream = ByteArrayOutputStream()
            imagen.compress(Bitmap.CompressFormat.PNG,100,stream)
            valores.put("imagen", stream.toByteArray())
        }
        return  db.insert("locales",null,valores)
    }
}