package com.jmlatham.eventscheduler.models

class
NameObj(var firstName:String, var middleName:String, var lastName:String, var suffix:String, var title:String, var nickName:String) {
    constructor(firstName:String, middleName:String, lastName:String, suffix:String):this(firstName, middleName, lastName, suffix, "", "")
    constructor(firstName:String, middleName:String, lastName:String):this(firstName, middleName, lastName, "")
    constructor(firstName:String, lastName:String):this(firstName, "", lastName)
    override fun toString():String{
        return String.format("%5s %1s %2s %3s, %4s \"%6s\"",firstName, middleName, lastName, suffix, title, nickName)
    }
}
