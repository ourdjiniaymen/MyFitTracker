package fr.uge.myfittracker.data.local.converters

import androidx.room.TypeConverter
import fr.uge.myfittracker.data.model.ExerciseType
import fr.uge.myfittracker.data.model.StepType

class Converters {
    @TypeConverter
    fun toExerciseType(name: String): ExerciseType {
        return ExerciseType.valueOf(name);
    }

    @TypeConverter
    fun fromExerciseType(exerciseType: ExerciseType): String {
        return exerciseType.name;
    }

    @TypeConverter
    fun toStepType(name: String): StepType {
        return StepType.valueOf(name);
    }

    @TypeConverter
    fun fromStepType(stepType: StepType): String {
        return stepType.name;
    }
}