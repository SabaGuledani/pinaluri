package com.saba.spark

class Habit {
    var habitName:String = ""
    var habitprogress:String = ""
    var dailyUseMoney:String= ""
    var status:String= ""
    var habitprogressnow:String= ""



    constructor(){}


    constructor(habitName:String,habitProgress:String,dailyUseMoney:String,status:String,habitprogressnow:String){
        this.habitName = habitName
        this.habitprogress = habitProgress
        this.dailyUseMoney = dailyUseMoney
        this.status = status
        this.habitprogressnow = habitprogressnow
    }

}