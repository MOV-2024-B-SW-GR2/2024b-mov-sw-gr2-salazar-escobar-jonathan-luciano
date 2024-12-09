package org.example

import java.util.*

class Zoologico (
    var id: Int,
    var name: String,
    var startDate: Date,
    var animalsCount: Int,
    var isOpen: Boolean,
    var anualIncome: Double
) {
    var animals = ArrayList<Animal>()

    override fun toString(): String {
        var animalsString = ""
        animals.forEach {
            animalsString += " "+it.toString() + "\n"
        }

        return "id: $id" +
                "\n name: $name" +
                "\n startDate: ${startDate.toString()}" +
                "\n Animals Count: $animalsCount" +
                "\n Is Open: $isOpen" +
                "\n Anual Income: $anualIncome" +
                "\n Animals:" +
                "\n $animalsString"
    }
}