package ru.ilsave.testtask.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.networking.RetrofitInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val portalName = etPortalName.text.toString()
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

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
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    response.message(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity,
                                    response.message(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        runOnUiThread {
                            progressBar.visibility = View.INVISIBLE
                        }
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
                                    val sharedPreference =
                                        getSharedPreferences(
                                            "PREFERENCE_NAME",
                                            Context.MODE_PRIVATE
                                        )
                                    var editor = sharedPreference.edit()
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


                            intent = Intent(applicationContext, InfoActivity::class.java)
                            val userDb = UserDb(portalName, it.response.token)
                            intent.putExtra("user", userDb)
                            startActivity(intent)
                        }

                    }

                } else {

                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            this,
                            "it seems you didn't enter your email address correctly ",
                            Toast.LENGTH_SHORT
                        ).show()

                }
            } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this,
                        "all fields should be filled! ",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }

    }

    override fun onStop() {
        super.onStop()
        etPortalName.setText("")
        etLogin.setText("")
        etPassword.setText("")
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}