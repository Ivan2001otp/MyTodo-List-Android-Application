package com.example.mytodolist.Util;

import androidx.room.TypeConverter;

import com.example.mytodolist.Data.Priority;

import java.util.Date;

public class Converter {

        @TypeConverter
        public static Date dateFromTimeStamp(Long value){
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date){
            return date == null ? null : date.getTime();
        }

        @TypeConverter
        public static String fromPriority(Priority priority){
            return priority == null ? null : priority.name();
        }

        @TypeConverter
        public static Priority toPriority(String priority){
            return priority == null? null : Priority.valueOf(priority);
        }
}
