package fr.uge.myfittracker.ui.training.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionWithSeries
//import fr.uge.myfittracker.data.model.PlanExerciseCrossRef
import fr.uge.myfittracker.data.repository.Repository
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


class TrainingPlanViewModel (application:Application):AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application.applicationContext)
    private val planDao = db.planDao()
    private val exerciseDao = db.exerciseDao()
    private val sessionDao = db.sessionDao()
    private val seriesDao = db.seriesDao()
    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    private val _sessions = MutableStateFlow<List<SessionWithSeries>>(emptyList())
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    private val _currentSession = MutableStateFlow<SessionWithSeries?>(null)
    private val _currentPlan = MutableStateFlow<Plan?>(null)
    var selectedDate: Long? = null
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()
    val sessions: StateFlow<List<SessionWithSeries>> = _sessions.asStateFlow()
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()
    var currentSession : StateFlow<SessionWithSeries?> = _currentSession.asStateFlow()
    var currentPlan : StateFlow<Plan?> = _currentPlan.asStateFlow()

    init {
        fetchPlans()
    }

    fun setCurrentSession(session:SessionWithSeries){
        _currentSession.value = session
    }

    fun setCurrentPlan(plan:Plan){
        _currentPlan.value = plan
    }

    suspend fun getListPlans(): List<Plan> {
        return planDao.getAllPlansWithoutSessions()
    }

    fun fetchPlans(){
        viewModelScope.launch {
            _plans.value = getListPlans()
        }
    }

    fun fetchSessions(planId: Long){
        viewModelScope.launch {
            _sessions.value = getListSessionByPlanId(planId)
        }
    }

    fun fetchExercises(sessionId: Long){
        viewModelScope.launch {
            _exercises.value = getListExercises(sessionId)
        }
    }

    fun addNewSession(session: Session){
        viewModelScope.launch {
            insertSession(session)
            fetchSessions(session.planId)
        }
    }

    fun addNewExercise(exercise: Exercise, sessionId: Long){
        viewModelScope.launch {
            val exoId = insertExercise(exercise)
            fetchExercises(sessionId)
        }
    }
    fun addNewPlan(plan: Plan) {
        viewModelScope.launch {
            insertPlan(plan)
            fetchPlans()
        }
    }

    suspend fun insertPlan(plan: Plan){
       planDao.insertPlan(plan)
    }

    suspend fun insertExercise(exercise: Exercise){
        val id = exerciseDao.insertExercise(exercise)
        Log.d("exo id", id.toString())
    }

    suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    suspend fun getListSessionByPlanId(planId: Long):List<SessionWithSeries>{
        return sessionDao.getSessionFromPlanId(planId)
    }

    fun getPlanById(planId: Long):Flow<Plan?>{
        return flow {
            emit( planDao.getPlanById(planId))
        }.flowOn(Dispatchers.IO)
    }

    fun getSessionById(sessionId: Long):Flow<Session?>{
        return flow {
            emit( sessionDao.getSessionById(sessionId))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListExercises(sessionId: Long): List<Exercise>{
        return exerciseDao.getExercisesFromSessionId(sessionId)
    }

    suspend fun insertSeries(series: Series){
        seriesDao.insertSeries(series)
    }

    fun deletePlan(planId: Long){
        viewModelScope.launch {
            planDao.deletePlanById(planId)
            fetchPlans()
        }
    }

    suspend fun deleteAllPlans(){
        viewModelScope.launch {
            planDao.deleteAllPlans()
            planDao.resetPlanIdSequence()
            fetchPlans()
        }
    }

    suspend fun deleteAllExos(){
        viewModelScope.launch {
            exerciseDao.deleteAllExercises()
            exerciseDao.resetExerciseIdSequence()
        }
    }

}