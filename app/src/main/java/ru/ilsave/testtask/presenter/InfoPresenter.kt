package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.ui.fragments.DocumentsFragment

class InfoPresenter(var mView: MainContract.InfoView, var shared: SharedPreferences) :
    MainContract.InfoPresenter {

    private val repository = Repository()

    override fun navigationPresenterToMyDocuments() {
        GlobalScope.launch(Dispatchers.Default) {

            val ascAuthKey = shared.getString("access_auth", "null")
            val host = shared.getString("host", "null").toString()
            val myHost = "$host.onlyoffice.eu"
            val ascKey = "asc_auth_key=$ascAuthKey"

            val response = repository.callGetMyDocuments(
                myHost,
                ascKey,
                host
            )
            if (response.isSuccessful) {
                val arrayListFiles = ArrayList(response.body()?.response?.files)
                val arrayListFolders =
                    ArrayList(response.body()?.response?.folders)

                val myDocucmentFragment =
                    DocumentsFragment.getNewInstance(
                        arrayListFiles,
                        arrayListFolders,
                        UserDb(host,ascAuthKey!!)
                    )
                mView.navigationToMyDocuments(myDocucmentFragment)

            }

        }
    }

    override fun navigationPresenterToCommonDocuments() {
        GlobalScope.launch(Dispatchers.Default) {

            val ascAuthKey = shared.getString("access_auth", "null")
            val host = shared.getString("host", "null").toString()
            var myHost = "$host.onlyoffice.eu"
            var myKey = "asc_auth_key=$ascAuthKey"
            val response = repository.callGetCommonDocuments(myHost, myKey, host)

            if (response.isSuccessful) {
                val arrayListFiles = ArrayList(response.body()?.response?.files)
                val arrayListFolders =
                    ArrayList(response.body()?.response?.folders)

                val myDocucmentFragment =
                    DocumentsFragment.getNewInstance(
                        arrayListFiles,
                        arrayListFolders,
                        UserDb(host,ascAuthKey!!)
                    )

                mView.navigationToMyDocuments(myDocucmentFragment)


            }
        }
    }
    override fun start() {
        GlobalScope.launch(Dispatchers.Default) {

            val ascAuthKey = shared.getString("access_auth", "null")
            val myportal = shared.getString("host", "null").toString()
            val myHost = "$myportal.onlyoffice.eu"
            val myKey = "asc_auth_key=$ascAuthKey"
            val response = repository.callGetMyDocuments(myHost, myKey, myportal)

            if (response.isSuccessful) {
                val arrayListFiles = ArrayList(response.body()?.response?.files)
                val arrayListFolders =
                    ArrayList(response.body()?.response?.folders)

                val myDocucmentFragment =
                    DocumentsFragment.getNewInstance(
                        arrayListFiles,
                        arrayListFolders,
                        UserDb(myportal,ascAuthKey!!)
                    )
                mView.navigationToMyDocuments(myDocucmentFragment)

            }
        }
    }









}