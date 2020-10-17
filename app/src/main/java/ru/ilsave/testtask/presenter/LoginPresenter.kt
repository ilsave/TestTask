package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserDb

class LoginPresenter(val mView: MainContract.LoginView) :
    MainContract.MainPresenter   {

    val TAG = "Presenter"

    private val repository = Repository()


    override fun onButtonWasClicked(
        portalName: String,
        login: String,
        password: String,
        sharedpref: SharedPreferences
    ) {
        Log.d(TAG, "Presenter clicked")
        // mView.showText()
        //repository.onLoad()

        mView.progressBarToVisible()

        if (portalName.trim().isNotEmpty()
            && login.trim().isNotEmpty()
            && password.trim().isNotEmpty()
        ) {
            if (isEmailValid(login)) {

                GlobalScope.launch(Dispatchers.Default) {

                    var myHost = "${portalName}.onlyoffice.eu"
                    var response = repository.callPushUser(
                        myHost,
                        portalName,
                        User(login, password)
                    )

                    if (response.isSuccessful) {
                        launch(Dispatchers.Main) {
                            mView.showText(response.message())
                        }

                    } else {
                        launch(Dispatchers.Main) {
                            mView.showText(response.message())
                        }
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        mView.progressBarToINvisible()
                    }


                    response.body()?.let {
                        Log.d("MainActivity", it.response.token)
                        val ascKey = "asc_auth_key=${it.response.token}"
                        val responseCallUserInfo = repository.callGetUserInfo(myHost,
                            ascKey,
                            portalName)

                        if (responseCallUserInfo.isSuccessful) {
                            responseCallUserInfo.body()?.apply {
                                var editor = sharedpref.edit()
                                editor.putString("imageUrl", this.response.avatar)
                                editor.putString(
                                    "name",
                                    "${this.response.firstName} ${this.response.lastName}"
                                )
                                editor.putString("email", this.response.email)
                                editor.apply()
                                //this.response.firstName
                            }
                        }

                        val userDb = UserDb(portalName, it.response.token)
                        mView.navigationToNextScreen(userDb)
                      //  mView.navigationToNextScreen(userDb)

                    }

                }

            } else {
                mView.showText("it seems you didn't enter your email address correctly")
                mView.progressBarToINvisible()
            }
        } else {
            mView.progressBarToINvisible()
            mView.showText("all fields should be filled!")

        }

    }


    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroyd")
    }



}