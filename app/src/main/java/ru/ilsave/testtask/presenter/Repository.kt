package ru.ilsave.testtask.presenter

import android.util.Log

class Repository(): MainContract.Repository {

    val TAG = "Repository"

    override fun onLoad() {
        Log.d(TAG,  "Reposity fun called")
    }


}