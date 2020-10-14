package ru.ilsave.testtask.presenter

import android.util.Log

class Presenter(val mView: MainContract.View): MainContract.Presenter {

    val TAG = "Presenter"

    private val repository = Repository()


    override fun onButtonWasClicked() {
        Log.d(TAG, "Presenter clicked")
        mView.showText()
        repository.onLoad()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroyd")

    }
}