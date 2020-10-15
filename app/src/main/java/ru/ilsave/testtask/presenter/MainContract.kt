package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import ru.ilsave.testtask.model.UserDb

interface MainContract {

    interface View {
        fun showText(message: String)
      //  fun navigationtoNextScreen(userDb: UserDb)
        fun progressBarToVisible()
        fun progressBarToINvisible()
    }

    interface InfoPresenter{

    }

    interface MainPresenter : View{
        fun onButtonWasClicked(portalName: String, login: String, password: String, sharedPreferences: SharedPreferences)
        fun navigationtoNextScreen(userDb: UserDb)
        fun onDestroy()
    }

    interface Repository{
        fun onLoad()
    }
}