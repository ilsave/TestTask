package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import retrofit2.Response
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.model.afterLogin.apiResponse
import ru.ilsave.testtask.model.commonRequest.commonResponse
import ru.ilsave.testtask.model.selfRequest.UserSefRequest
import ru.ilsave.testtask.ui.fragments.DocumentsFragment

interface MainContract {

    interface View {
        fun showText(message: String)
    }


    interface LoginView : View {
        fun progressBarToVisible()
        fun progressBarToINvisible()
        fun navigationToNextScreen(userDb: UserDb)
    }

    interface InfoView : View {
        fun navigationToLoginScreen()
        fun navigationToMyDocuments(documentsFragment: DocumentsFragment)
        fun navigationToCommonDocuments(documentsFragment: DocumentsFragment)
    }

    //starSelf - функция, которая вызывается при открытии
    interface InfoPresenter : InfoView {
        fun navigationPresenterToMyDocuments()
        fun createCustomPicasso()
        fun onDestroy()
    }

    interface MainPresenter : LoginView {
        fun onButtonWasClicked(
            portalName: String,
            login: String,
            password: String,
            sharedPreferences: SharedPreferences
        )

        fun onDestroy()
    }

    interface Repository {
        suspend fun callPushUser(
            myHost: String,
            myportal: String,
            user: User
        ): Response<apiResponse>

        suspend fun callGetUserInfo(
            myHost: String,
            ascAuthKey: String,
            myportal: String,
        ): Response<UserSefRequest>

        suspend fun callGetMyDocuments(
            myHost: String,
            ascAuthKey: String,
            myportal: String
        ): Response<commonResponse>
    }
}