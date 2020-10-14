package ru.ilsave.testtask.presenter

interface MainContract {

    interface View {
        fun showText()
    }

    interface Presenter{
        fun onButtonWasClicked()
        fun onDestroy()
    }

    interface Repository{
        fun onLoad()
    }
}