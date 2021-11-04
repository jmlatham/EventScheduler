package com.jmlatham.eventscheduler.models

class User(val uid:String, var name:NameObj, var contactInfo:ContactObj, var events:ArrayList<Event>) {
    constructor(uid:String, name:NameObj, contactInfo: ContactObj) : this(uid, name, contactInfo, ArrayList<Event>())
//    val uid = UUID.randomUUID().toString()
}