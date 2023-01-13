package com.saba.spark

class SharedAchievement {
    var profileImg:String = ""
    var posterName:String = ""
    var time:String = ""
    var date:String = ""
    var achievement:String = ""

    constructor(){}

    constructor(profileImg:String,posterName:String,time:String,date:String,achievement:String){
        this.profileImg = profileImg
        this.posterName = posterName
        this.time = time
        this.date = date
        this.achievement = achievement

    }

}