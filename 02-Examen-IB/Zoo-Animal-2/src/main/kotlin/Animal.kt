package org.example

import java.util.*

class Animal (
    var id: Int,
    var name: String,
    var specie: String,
    var age: Int,
    var idZoo: Int,
    var birthDay: Date,
    var isFeeded: Boolean,
    var weight: Double
) {
    override fun toString(): String {
        return "Id: $id" +
                "\n Name: $name" +
                "\n Specie: $specie" +
                "\n Age: $age" +
                "\n Id Zoo: $idZoo" +
                "\n BirthDay: $birthDay" +
                "\n Is Feeded: $isFeeded" +
                "\n Weight: $weight"

    }
}