import com.example.myapplication1.models.Animal
import com.example.myapplication1.models.Zoologico

class BBaseDatosMemoria {
    companion object {
        val zoologicos = mutableListOf(
            Zoologico(
                id = 1,
                name = "Safari World",
                startDate = "Jul 14, 1998, 7:00:00 PM",
                animalsCount = 2,
                isOpen = true,
                anualIncome = 1500000.5,
                animals = mutableListOf(
                    Animal(1, "Leo", "Lion", 5, "May 13, 2018, 7:00:00 PM", true, 190.5),
                    Animal(2, "Milo", "Tiger", 4, "Mar 21, 2019, 7:00:00 PM", false, 210.8)
                )
            ),
            Zoologico(
                id = 2,
                name = "Ocean Park",
                startDate = "Sep 29, 2005, 7:00:00 PM",
                animalsCount = 3,
                isOpen = false,
                anualIncome = 2500000.75,
                animals = mutableListOf(
                    Animal(3, "Daisy", "Elephant", 8, "Aug 8, 2015, 7:00:00 PM", true, 1200.0),
                    Animal(4, "Max", "Giraffe", 6, "Jan 16, 2017, 7:00:00 PM", true, 800.4),
                    Animal(5, "Polly", "Parrot", 2, "Dec 24, 2021, 7:00:00 PM", false, 1.2)
                )
            )
        )
    }
}

