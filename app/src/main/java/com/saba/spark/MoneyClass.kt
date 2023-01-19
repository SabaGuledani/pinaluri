package com.saba.spark

class MoneyClass {
    var dailyUseMoney:String = ""
    var habitProgressNow:String = ""


    constructor(){}

    constructor(dailyUseMoney:String,habitProgress:String){
        this.dailyUseMoney = dailyUseMoney
        this.habitProgressNow = habitProgress
    }
}