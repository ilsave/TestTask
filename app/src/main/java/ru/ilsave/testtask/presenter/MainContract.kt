package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import android.view.View
import retrofit2.Response
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.model.afterLogin.apiResponse
import ru.ilsave.testtask.model.commonRequest.commonResponse
import ru.ilsave.testtask.model.selfRequest.UserSefRequest
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject
import ru.ilsave.testtask.ui.fragments.DocumentsFragment

interface MainContract {

    interface BaseView {
        fun showText(message: String)
    }


    interface LoginView : BaseView {
        fun progressBarToVisible()
        fun progressBarToINvisible()
        fun navigationToNextScreen(userDb: UserDb)
    }

    interface DocumentsFragmentView : BaseView {
        fun cleanAdapterList()
        //quite antiPattern, isnt it?:) Anyway I didnt know how to update my adapter from presenter
        //so Im really sorry
        fun updateAdapterList(list: ArrayList<AdapterPojoObject>, toolBarName: String, view: View)
    }

    interface InfoView : BaseView {
        fun navigationToLoginScreen()
        fun navigationToMyDocuments(documentsFragment: DocumentsFragment)
        fun navigationToCommonDocuments(documentsFragment: DocumentsFragment)
    }

    interface InfoPresenter {
        fun navigationPresenterToMyDocuments()
        fun navigationPresenterToCommonDocuments()
        fun start()
    }

    interface MainPresenter {
        fun onButtonWasClicked(
            portalName: String,
            login: String,
            password: String,
            sharedPreferences: SharedPreferences
        )

        fun onDestroy()
    }

    interface FragmentPresenter {
        fun workingList(adapterPojoObject: AdapterPojoObject, userDb: UserDb, view: View)
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

        suspend fun callGetCommonDocuments(
            myHost: String,
            ascAuthKey: String,
            myportal: String
        ): Response<commonResponse>

        suspend fun callGetFolderDocuments(
            myHost: String,
            ascAuthKey: String,
            myportal: String,
            idFolder: String
        ): Response<commonResponse>
    }
}