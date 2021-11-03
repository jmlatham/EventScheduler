package com.jmlatham.eventscheduler.models

import java.util.*
import kotlin.collections.ArrayList

class User(var name:NameObj, var email:String, var events:ArrayList<Event>) {
    constructor(name:NameObj, email:String) : this(name, email, ArrayList<Event>())
    val uid = UUID.randomUUID().toString()
}