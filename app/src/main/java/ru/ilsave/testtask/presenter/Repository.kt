package ru.ilsave.testtask.presenter

import retrofit2.Response
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.commonRequest.commonResponse
import ru.ilsave.testtask.networking.RetrofitInstance

class Repository() : MainContract.Repository {

    override suspend fun callPushUser(myHost: String, myportal: String, user: User) =
        RetrofitInstance.api.pushUser2(myHost, myportal, user)

    override suspend fun callGetUserInfo(myHost: String, ascAuthKey: String, myportal: String) =
        RetrofitInstance.api.getUserInfo(myHost, ascAuthKey, myportal)

    override suspend fun callGetMyDocuments(myHost: String, ascAuthKey: String, myportal: String ) =
        RetrofitInstance.api.getMyDocuments(myHost, ascAuthKey, myportal)

    override suspend fun callGetCommonDocuments(myHost: String, ascAuthKey: String, myportal: String) =
        RetrofitInstance.api.getCommonDocuments(myHost, ascAuthKey, myportal)

    override suspend fun callGetFolderDocuments(myHost: String, ascAuthKey: String, myportal: String, idFolder: String) =
        RetrofitInstance.api.getFolderDocuments(myHost, ascAuthKey, myportal, idFolder)


}