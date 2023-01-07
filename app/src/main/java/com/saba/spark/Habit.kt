package com.saba.spark

class Habit {
    var habitName:String? = null
    var habitprogress:String? = null
    var dailyUseMoney:String? = null
    var status:String? = null

    constructor(){}


    constructor(habitName:String?,habitProgress:String?,dailyUseMoney:String?,status:String?){
        this.habitName = habitName
        this.habitprogress = habitProgress
        this.dailyUseMoney = dailyUseMoney
        this.status = status
    }

}