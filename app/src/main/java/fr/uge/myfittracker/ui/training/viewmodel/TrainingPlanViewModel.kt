package fr.uge.myfittracker.ui.training.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithSessions
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionWithSeries
//import fr.uge.myfittracker.data.model.PlanExerciseCrossRef
import fr.uge.myfittracker.data.repository.Repository
import fr.uge.myfittracker.ui.training.SeriesWithExerciseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate


class TrainingPlanViewModel (application:Application):ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return TrainingPlanViewModel(
                    (application)
                ) as T
            }
        }
    }
    val repository = Repository(application)

    private val _plans = MutableStateFlow<List<PlanWithSessions>>(emptyList())
    private val _currentSession = MutableStateFlow<SessionWithSeries?>(null)
    private val _currentPlan = MutableStateFlow<PlanWithSessions?>(null)
    var selectedDate: Long? = null
    val plans: StateFlow<List<PlanWithSessions>> = _plans.asStateFlow()
    var currentSession : StateFlow<SessionWithSeries?> = _currentSession.asStateFlow()
    var currentPlan : StateFlow<PlanWithSessions?> = _currentPlan.asStateFlow()

    init {
        viewModelScope.launch {
            _plans.value = repository.getAllPlans()
        }
    }


    fun initPlan(){
        viewModelScope.launch {
            _plans.value = repository.getAllPlans()
        }
    }
    fun addPlanWithSessions(planWithSessions: PlanWithSessions){
        _plans.value += planWithSessions
    }

    fun setCurrentSession(session:SessionWithSeries){
        _currentSession.value = session
    }

    fun setCurrentPlan(plan:PlanWithSessions){
        _currentPlan.value = plan
    }

    suspend fun getListPlans(): List<PlanWithSessions> {
       // return planDao.getAllPlansWithoutSession()
        return repository.getAllPlans()
    }

    /*fun fetchPlans(){
        viewModelScope.launch {
            _plans.value = getListPlans()
        }
    }*/

    /*suspend fun getListSessionByPlanId(planId: Long):List<SessionWithSeries>{
        return sessionDao.getSessionFromPlanId(planId)
    }

    fun getPlanById(planId: Long):Flow<Plan?>{
        return flow {
            emit( planDao.getPlanById(planId))
        }.flowOn(Dispatchers.IO)
    }*/
}