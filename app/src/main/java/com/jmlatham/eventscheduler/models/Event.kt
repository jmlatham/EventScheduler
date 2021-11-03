package com.jmlatham.eventscheduler.models

import java.util.*
import kotlin.collections.ArrayList

class Event (var ownerId:String, var name:String, var description:String, var logo:String, var dateStart:GregorianCalendar, var dateEnd:GregorianCalendar, var schedule:ArrayList<String>, var organizer:String, var contactInfo:ContactObj, var activities:ArrayList<String>, var notifications:ArrayList<String>){
    constructor(owner:String, name:String, description:String, dateStart:GregorianCalendar, dateEnd:GregorianCalendar, organizer:String) : this(owner, name, description, "", dateStart, dateEnd, ArrayList<String>(), organizer, ContactObj(), ArrayList<String>(), ArrayList<String>())
    val uniqueId:String = UUID.randomUUID().toString()

}
/*
*
* GregorianCalendar()
* GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
* calendar = GregorianCalendar()
* calendar.get(Calendar.HOUR) [12 hour]
* calendar.get(Calendar.HOUR_OF_DAY) [24 hour]
* calendar.get(Calendar.AM/Calendar.AM_PM/Calendar.PM)
* Calendar.
*   DATE/DAY_OF_MONTH
*   MINUTE
*   MONTH
*   SECOND
*   YEAR
*   AM_PM ==> AM=0; PM=1
*
*
*
* GOOD CODE EXAMPLE FOR SORTING THE ARRAYLIST OF EVENTS!!!
import java.util.*

fun main(args: Array<String>) {

    val list = ArrayList<CustomObject>()
    list.add(CustomObject("Z", GregorianCalendar(2000, 2, 1, 11, 0, 0), GregorianCalendar(2000, 2, 1, 13, 0, 0)))
    list.add(CustomObject("A", GregorianCalendar(2000, 2, 1, 11, 0, 0), GregorianCalendar(2000, 2, 1, 12, 0, 0)))
    list.add(CustomObject("B", GregorianCalendar(2001, 2, 1, 11, 0, 0), GregorianCalendar(2001, 2, 1, 12, 0, 0)))
    list.add(CustomObject("X", GregorianCalendar(2000, 2, 1, 10, 0, 0), GregorianCalendar(2000, 2, 1, 10, 30, 0)))
    list.add(CustomObject("Aa", GregorianCalendar(1999, 2, 1, 11, 0, 0), GregorianCalendar(1999, 2, 1, 12, 0, 0)))

    var sortedList = list.sortedWith(compareBy({ it.dateStart; it.dateEnd }))

    for (obj in sortedList) {
        println(String.format("%1s - %2s {%3s} \n %4s", obj.name, obj.dateStart, obj.dateEnd, obj.uniqueId))
    }
}

public class CustomObject(val name: String, val dateStart:GregorianCalendar, val dateEnd:GregorianCalendar) {
    val uniqueId:String = UUID.randomUUID().toString()
}
*
*
*
*
* */