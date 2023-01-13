package com.saba.spark

class userProfile {
    var senderUid:String = ""
    var profileName:String = ""
    var profileImg:String = ""

    constructor(){}
    constructor(senderUid:String,profileName:String,
    profileImg:String){
        this.senderUid = senderUid
        this.profileImg = profileImg
        this.profileName = profileName
    }
}