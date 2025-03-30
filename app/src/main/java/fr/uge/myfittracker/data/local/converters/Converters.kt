package fr.uge.myfittracker.data.local.converters

import androidx.room.TypeConverter
import fr.uge.myfittracker.data.model.SessionType

class Converters {
    @TypeConverter
    fun toSessionType(name: String): SessionType {
        return SessionType.valueOf(name);
    }

    @TypeConverter
    fun fromSessionType(sessionType: SessionType): String {
        return sessionType.name;
    }
}