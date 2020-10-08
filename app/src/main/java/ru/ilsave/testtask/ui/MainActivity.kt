package ru.ilsave.testtask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.User
import ru.ilsave.testtask.networking.RetrofitInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {

            val portalName = etPortalName.text.toString()
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            if (portalName.trim().isNotEmpty()
                && login.trim().isNotEmpty()
                && password.trim().isNotEmpty() ) {
                if (isEmailValid(login)) {
                    runBlocking {
                        val base_url = "https://${portalName}.onlyoffice.eu"
                        val response = RetrofitInstance.api.pushUser2(
                            "${portalName}.onlyoffice.eu",
                            portalName,
                            User(login, password)
                        )

                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                response.message(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.d("MainActivity", response.message())
                            Log.d("MainActivity", response.body().toString())
                            Log.d("MainActivity", response.toString())
                            Log.d("MainActivity", response.raw().toString())
                            Log.d("MainActivity", response.errorBody().toString())
                            Log.d("MainActivity", response.code().toString())
                            response.body()?.let {
                                Log.d("MainActivity", it.response.token)
                            }

                        } else {
                            Toast.makeText(
                                applicationContext,
                                response.message(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.d("MainActivity", response.message())
                            Log.d("MainActivity", response.body().toString())
                            Log.d("MainActivity", response.toString())

                        }

                        intent = Intent(applicationContext, InfoActivity::class.java)
                        //   intent.putExtra("token", response.)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "it seems you didn't enter your email address correctly ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "all fields should be filled! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}