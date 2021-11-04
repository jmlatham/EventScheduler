package com.jmlatham.eventscheduler.models

class User(val uid:String, var avatarUrl:String, var name:NameObj, var contactInfo:ContactObj, var events:ArrayList<Event>) {
    constructor(uid:String, avatarUrl:String, name:NameObj, contactInfo: ContactObj) : this(uid, avatarUrl, name, contactInfo, ArrayList<Event>())
    constructor() : this("", "", NameObj(), ContactObj())
//    val uid = UUID.randomUUID().toString()
}