package fr.uge.myfittracker.ui.home.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

//class StepCounterViewModel(context: Context) : ViewModel(),
class StepCounterViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {
    private val sensorManager:SensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var sensor: Sensor? = null

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount

    fun updateSteps(newSteps: Int) {
        Log.d("StepCounter", "Nouveaux pas détectés: $newSteps")
        _stepCount.update { newSteps }
    }

    init {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
            Log.e("StepCounterViewModel", "Capteur de pas détecté et enregistré ✅")
        } else {
            Log.e("StepCounterViewModel", "Aucun capteur de pas détecté ! ❌")
        }
    }



    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { event ->
            if (event.sensor.type==Sensor.TYPE_STEP_DETECTOR){
                _stepCount.update { count -> count + 1 }
                Log.d("StepCounterViewModel", "Pas détectés : ${_stepCount.value}")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO("Not yet implemented")
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }
}