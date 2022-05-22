package com.zerdasoftware.thebooks.model

import com.google.gson.annotations.SerializedName

//API'den ilk başta liste yerine result datası gelir
class ObjectData (
    //Retfotitten hangi isimde data çekileceğini belirtiyoruz
    @SerializedName("results")
    val results:ResultsData) {

}