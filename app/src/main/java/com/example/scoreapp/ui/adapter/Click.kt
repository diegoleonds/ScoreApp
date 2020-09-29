package com.example.scoreapp.ui.adapter

interface Click<T> {
    fun simpleClick(type: T)
    fun longClick(type: T): Boolean
}