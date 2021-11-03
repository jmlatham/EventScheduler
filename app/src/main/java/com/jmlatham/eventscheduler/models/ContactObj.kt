package com.jmlatham.eventscheduler.models

import android.provider.ContactsContract

class ContactObj(var phoneNumber:String, var emailAddress:String, var website:String) {
    constructor() : this("", "", "")
}
