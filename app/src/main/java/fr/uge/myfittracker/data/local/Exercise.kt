package fr.uge.myfittracker.data.local

import Step

data class Exercise(
    val title: String, // Titre de l'exercice
    val description: String, // Description de l'exercice
    val steps: List<Step> // Liste des étapes de l'exercice
)