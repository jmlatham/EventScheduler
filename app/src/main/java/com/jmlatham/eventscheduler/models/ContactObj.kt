package com.jmlatham.eventscheduler.models

class ContactObj(var phoneNumber:String, var emailAddress:String, var website:String) {
    constructor() : this("", "", "")
}
