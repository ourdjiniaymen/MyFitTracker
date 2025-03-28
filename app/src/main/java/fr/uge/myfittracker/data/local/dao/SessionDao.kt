package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionWithSeries

@Dao
interface SessionDao {
     @Insert
     suspend fun insertSession(session: Session): Long

     @Transaction
     @Query(
         "SELECT * FROM session WHERE plan_id=:planId"
     )
     suspend fun getSessionFromPlanId(planId: Long): List<SessionWithSeries>
}