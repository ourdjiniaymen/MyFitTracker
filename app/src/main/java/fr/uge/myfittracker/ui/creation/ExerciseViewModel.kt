package fr.uge.myfittracker.ui.training

import Step
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.local.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps: StateFlow<List<Step>> get() = _steps.asStateFlow()

    // Liste des exercices enregistrés
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises.asStateFlow()

    // Titre et description de l'exercice
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description.asStateFlow()

    // Mettre à jour le titre
    fun updateTitle(newTitle: String) {
        Log.i("title update",_title.value )
        _title.value = newTitle
        Log.i("title to add",newTitle )
    }

    // Mettre à jour la description
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun addStep(step: Step) {
        viewModelScope.launch {
            _steps.update { it + step }
            Log.i("test step", _steps.value.toString())
        }
    }

    fun saveExercise(title: String, description: String) {
        viewModelScope.launch {
            val newExercise = Exercise(
                title = title,
                description = description,
                steps = _steps.value // Utilise les étapes actuelles
            )
            _exercises.update { it + newExercise }
            _steps.update { emptyList() } // Réinitialise les étapes après enregistrement
            Log.i("ExerciseViewModel", "Exercise saved: ${newExercise.title}")
        }
    }
}