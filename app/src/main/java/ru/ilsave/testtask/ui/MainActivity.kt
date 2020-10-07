package ru.ilsave.testtask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            val base_url = "https://$etPortalName.onlyoffice.eu"
            runBlocking {

                val response = RetrofitInstance.
                api.
                pushUser2("${etPortalName.text}.onlyoffice.eu",  etPortalName.text.toString(), User(etLogin.text.toString(), etPassword.text.toString()))

                if (response.isSuccessful){
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", response.message())
                    Log.d("MainActivity", response.body().toString())
                    Log.d("MainActivity", response.toString())
                    Log.d("MainActivity", response.raw().toString())
                    Log.d("MainActivity", response.errorBody().toString())
                    Log.d("MainActivity", response.code().toString())
                    response.body()?.let {
                        Log.d("MainActivity",  it.response.token)
                    }

                }else {
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", response.message())
                    Log.d("MainActivity", response.body().toString())
                    Log.d("MainActivity", response.toString())

                }

                intent = Intent(applicationContext, InfoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}