package com.example.project_farmcare.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    fun fromAnyToString(attribute: Any?) : String{
        if(attribute == null)
            return ""
        return attribute.toString()
    }
    @TypeConverter
    fun fromStringToAny(attribute: String) : Any{
        if(attribute == null)
            return ""
        return attribute
    }

}