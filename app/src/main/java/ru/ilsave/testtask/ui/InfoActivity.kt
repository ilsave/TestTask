package ru.ilsave.testtask.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.networking.RetrofitInstance

class InfoActivity : AppCompatActivity() {

    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader
    private lateinit var toolBar: Toolbar
    private lateinit var currentProfile: ProfileDrawerItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val userDb = intent.getSerializableExtra("user") as? UserDb
        textView.text = userDb.toString()
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("access_auth",userDb?.userToken)
        editor.apply()

        GlobalScope.launch(Dispatchers.Main){

            val response =  RetrofitInstance
                .api
                .getUserInfo("${userDb?.userPortal.toString()}.onlyoffice.eu",
                    "asc_auth_key=${userDb?.userToken.toString()}",
                    userDb?.userPortal.toString()
                )

            if (response.isSuccessful){
                response.body()?.apply {
                    this.response.firstName
                }
                Log.d("Response", response.toString())
                textView.text = response.toString()
            }

        }

    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()

    }

    private fun initFunc() {
        setSupportActionBar(toolBar)
        initLoader()
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolBar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(header)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("My documents")
                    .withIcon(R.drawable.ic_baseline_folder_24)
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Common documents")
                    .withIcon(R.drawable.ic_baseline_folder_24)
                    .withSelectable(false),
            ).build()
    }

    private fun createHeader() {
        currentProfile = ProfileDrawerItem()
            .withName("Ilya Gushchin")
            .withEmail("bulldoo@yandex.ru")
            .withIcon("https://ilsave.onlyoffice.eu/storage/userPhotos/root/d3874710-08d6-4ea2-973e-05e6f3208a66_size_32-32.jpeg?_=1995263857")
            .withIdentifier(200)

        header = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                currentProfile
            ).build()
    }



    fun ImageView.downloadandSetImage(url: String){
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_folder_24)
            .into(this)
    }

    private fun initLoader(){
        DrawerImageLoader.init(object : AbstractDrawerImageLoader(){

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
                val ascAuthKey = sharedPreference.getString("access_auth","null")
//                GlobalScope.launch(Dispatchers.Default){
//                   val response =  RetrofitInstance.api.getPhoto("asc_auth_key={$ascAuthKey}" , "ilsave")
//                    Log.d("InfoActivity", response.toString())
//                 }
                Picasso.get()
                    .load("https://ilsave.onlyoffice.eu/storage/userPhotos/root/d3874710-08d6-4ea2-973e-05e6f3208a66_size_48-48.jpeg?_=1995263857g")
                    .placeholder(placeholder)
                    .into(imageView)

            }
        })
    }

    private fun initFields() {
        toolBar = findViewById(R.id.mainToolbar)
    }
}