package com.example.gr2sw2024b_jlse

class BBaseDatosMemoria {

    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador.add(BEntrenador(1,"Jonathan", "Descripcion Jonathan"))
            arregloBEntrenador.add(BEntrenador( 2,"Luciano", "Descripcion Luciano"))
            arregloBEntrenador.add(BEntrenador(3,"Yese", "Descripcion Yese"))
        }
    }
}