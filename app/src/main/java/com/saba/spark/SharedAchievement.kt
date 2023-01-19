package com.saba.spark

class SharedAchievement {
    var senderuid:String? = null
    var profileImg:String? = null
    var posterName:String? = null
    var time:String = ""
    var date:String = ""
    var achievement:String = ""
    var selectedHabit:String = ""

    constructor(){}

    constructor(senderuid:String?,profileImg:String?
                ,posterName:String?,time:String,date:String
                ,achievement:String,selectedHabit:String){
        this.senderuid = senderuid
        this.profileImg = profileImg
        this.posterName = posterName
        this.time = time
        this.date = date
        this.achievement = achievement
        this.selectedHabit = selectedHabit

    }

}