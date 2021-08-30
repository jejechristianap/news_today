package com.jejec.newstoday.db

import androidx.room.TypeConverter
import com.jejec.newstoday.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}