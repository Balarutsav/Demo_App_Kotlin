package com.utsav.demo_app.Database

class UserDetail {
    constructor(
        image_path:String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        mobileNumber: String
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.dateOfBirth = dateOfBirth
        this.mobileNumber = mobileNumber
        this.image_path=image_path
    }

    var image_path:String?=null
    var firstName:String?=null
    var lastName:String?=null
    var dateOfBirth:String?=null
    var mobileNumber:String?=null
}