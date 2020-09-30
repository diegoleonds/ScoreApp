package com.example.scoreapp.ui.adapter

interface AdapterClick<T> {
    fun simpleClick(type: T)
    fun longClick(type: T): Boolean
}