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
                navigationToMyDocuments(myDocucmentFragment)

//                supportFragmentManager.beginTransaction()
//                    .addToBackStack(null)
//                    .replace(
//                        R.id.frameLayout,
//                        myDocucmentFragment
//                    ).commit()
            }

        }
    }

    override fun createCustomPicasso() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun navigationToLoginScreen() {
        TODO("Not yet implemented")
    }

    override fun navigationToMyDocuments(documentsFragment: DocumentsFragment) {
        mView.navigationToMyDocuments(documentsFragment)
    }

    override fun navigationToCommonDocuments(documentsFragment: DocumentsFragment) {
        TODO("Not yet implemented")
    }

    override fun showText(message: String) {
        TODO("Not yet implemented")
    }


}