package com.example.scoreapp.ui.adapter

/**
 * used to set click actions in list items
 */
interface AdapterClick<T> {
    fun simpleClick(type: T)
    fun longClick(type: T): Boolean
}