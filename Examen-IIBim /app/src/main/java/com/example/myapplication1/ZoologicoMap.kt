package com.example.myapplication1

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.repository.UbicacionRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class ZoologicoMap : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false
    private val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var ubicacionRepository: UbicacionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zoologico_map)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ubicacionRepository = UbicacionRepository(DBHelper(this))

        solicitarPermisos()
        inicializarLogicaMapa()
    }

    private fun tengoPermisos(): Boolean {
        val contexto = applicationContext
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        permisos = tienePermisos
        return permisos
    }

    private fun solicitarPermisos() {
        if (!tengoPermisos()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    private fun inicializarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map ) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                mostrarTodasLasUbicaciones()
            }
        }
    }

    private fun mostrarTodasLasUbicaciones() {
        val ubicaciones = ubicacionRepository.getAllUbicaciones()
        if (ubicaciones.isNotEmpty()) {

            for (ubicacion in ubicaciones) {
                val posicion = LatLng(ubicacion.latitud, ubicacion.longitud)
                val titulo = "Zoológico ID: ${ubicacion.zoologicoId}"
                val marcadorPosicion = anadirMarcador(posicion, titulo)
                marcadorPosicion.tag = titulo

                println("Ubicación cargada: Zoológico ID ${ubicacion.zoologicoId}, Lat: ${ubicacion.latitud}, Lng: ${ubicacion.longitud}")
            }

            //mapa.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100))
            val firstZooLocation = LatLng(ubicaciones[0].latitud, ubicaciones[0].longitud)
            moverCamaraConZoom(firstZooLocation)
        } else {
            mostrarSnackbar("⚠️ No se encontraron ubicaciones en la base de datos.")
        }
    }
    fun anadirMarcador(latLang:LatLng, title: String): Marker {
        return mapa.addMarker(MarkerOptions().position(latLang).title(title))!!
    }
    fun moverCamaraConZoom(latLang:LatLng, zoom: Float = 17f){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang,zoom))
    }

    @SuppressLint("MissingPermission")
    private fun establecerConfiguracionMapa() {
        with(mapa) {
            if (tengoPermisos()) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}
