package com.example.modelstoreproject.model

data class RecyclerList(val items: List<RecyclerData>)
data class RecyclerData(var name: String?, var description: String?, val owner: Owner?)
data class Owner(val avatar_url: String?)
