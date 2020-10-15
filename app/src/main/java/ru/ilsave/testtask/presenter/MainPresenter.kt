package ru.ilsave.testtask.presenter

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.networking.RetrofitInstance

class MainPresenter(val mView: MainContract.MainPresenter) :
    MainContract.MainPresenter {

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
        repository.onLoad()

        mView.progressBarToVisible()

        if (portalName.trim().isNotEmpty()
            && login.trim().isNotEmpty()
            && password.trim().isNotEmpty()
        ) {
            if (isEmailValid(login)) {

                GlobalScope.launch(Dispatchers.Default) {
                    val base_url = "https://${portalName}.onlyoffice.eu"
                    val response = RetrofitInstance.api.pushUser2(
                        "${portalName}.onlyoffice.eu",
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
                    mView.progressBarToINvisible()

                    response.body()?.let {
                        Log.d("MainActivity", it.response.token)

                        val response = RetrofitInstance
                            .api
                            .getUserInfo(
                                "${portalName}.onlyoffice.eu",
                                "asc_auth_key=${it.response.token}",
                                portalName
                            )
                        if (response.isSuccessful) {
                            response.body()?.apply {
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
                        mView.navigationtoNextScreen(userDb)

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

    override fun navigationtoNextScreen(userDb: UserDb) {
        mView.navigationtoNextScreen(userDb)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroyd")
    }

    override fun showText(message: String) {
        mView.showText(message)
    }

    override fun progressBarToVisible() {
        mView.progressBarToVisible()
    }

    override fun progressBarToINvisible() {
        mView.progressBarToINvisible()
    }
}