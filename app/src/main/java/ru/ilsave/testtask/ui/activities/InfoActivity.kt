package ru.ilsave.testtask.ui.activities

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.model.UserHeaderInfo
import ru.ilsave.testtask.networking.RetrofitInstance
import ru.ilsave.testtask.ui.fragments.CommonFragment
import ru.ilsave.testtask.ui.fragments.MyDocumentsFragment
import java.io.IOException

class InfoActivity : AppCompatActivity() {

    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader
    private lateinit var toolBar: Toolbar
    private lateinit var currentProfile: ProfileDrawerItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val userDb = intent.getSerializableExtra("user") as? UserDb
        //textView.text = userDb.toString()
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("access_auth", userDb?.userToken)
        editor.putString("host", userDb?.userPortal)
        editor.apply()
        val userHeaderInfo: UserHeaderInfo
        //val responseX = ResponseX()

        GlobalScope.launch(Dispatchers.Main) {

            val job = GlobalScope.launch(Dispatchers.IO) {
                val response = RetrofitInstance
                    .api
                    .getUserInfo(
                        "${userDb?.userPortal.toString()}.onlyoffice.eu",
                        "asc_auth_key=${userDb?.userToken.toString()}",
                        userDb?.userPortal.toString()
                    )
                if (response.isSuccessful) {
                    response.body()?.apply {
                        val sharedPreference =
                            getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                        var editor = sharedPreference.edit()
                        editor.putString("imageUrl", this.response.avatarMedium)
                        editor.putString(
                            "name",
                            "${this.response.firstName} ${this.response.lastName}"
                        )
                        editor.putString("email", this.response.email)
                        editor.apply()
                        this.response.firstName
                    }
                }
            }
            job.join()
//             //   Log.d("Response", response.toString())
//                textView.text = response.toString()
//            }

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
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {

                //функция нажатия на че нить из нашего drawer\а
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_SHORT)
                        .show()
                    //getapplicationContext потому что мы находимся в кликере (this если в самой активности как я понял)

                    when (position) {
                        //addBackStack - чтобы потом можно было вернуться в основное активити
                        1 -> {
                            GlobalScope.launch(Dispatchers.Default) {
//                                val sharedPreference =
//                                    getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//                                val ascAuthKey = sharedPreference.getString("access_auth", "null")
//                                val host = sharedPreference.getString("host", "null").toString()
//                                val response = RetrofitInstance.api.getMyDocuments(
//                                    "$host.onlyoffice.eu",
//                                    "asc_auth_key=$ascAuthKey",
//                                    host
//                                )


                                val myDocucmentFragment =
                                    MyDocumentsFragment.getNewInstance("Dowloaded!")
                                supportFragmentManager.beginTransaction()
                                    .addToBackStack(null)
                                    .replace(
                                        R.id.frameLayout,
                                        myDocucmentFragment
                                    ).commit()
//                                    response.body()?.let {
//                                        it.response.files.get(0)
//
//                                        it.response.files[0].toString()
//
//                                    }

                            }

                        }
                        2 ->
                            supportFragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(
                                    R.id.frameLayout,
                                    CommonFragment()
                                ).commit()

                    }
                    return false
                }
            }).build()
    }

    private fun createHeader() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val ascAuthKey = sharedPreference.getString("access_auth", "null")
        val name = sharedPreference.getString("name", "name")
        val email = sharedPreference.getString("email", "email")
        val url = sharedPreference.getString("imageUrl", "imageurl")

        currentProfile = ProfileDrawerItem()
            .withName(name)
            .withEmail(email)
            .withIcon("https://i.pinimg.com/originals/04/94/b8/0494b801bb0d71bd8d7d4923ba996795.png")
            .withIdentifier(200)

        header = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                currentProfile
            ).build()
    }


    fun ImageView.downloadandSetImage(url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_baseline_folder_24)
            .into(this)
    }

    private fun initLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                val ascAuthKey = sharedPreference.getString("access_auth", "null")
                val pathImage = sharedPreference.getString("imageUrl", "imageurl")

//                GlobalScope.launch(Dispatchers.Default){
//                   val response =  RetrofitInstance.api.getPhoto("asc_auth_key={$ascAuthKey}" , "ilsave", pathImage!!)
//                    Log.d("ImageInfoActivity", response.toString())
//
//                 }

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val newRequest: Request = chain.request().newBuilder()
                                .addHeader(
                                    "Cookie",
                                    "asc_auth_key=OB0UZ5R66IyWtetJJQJ8Z52lcW1wODXYtOIvAbSLJG2EWuGAMxhXgyjWYrw/iRaSoEaqO70yLrJtOVmYRzWPgv8/yK/1IS/aX7NA4zuG3dySsoFrjypccBKLbNTbU/vBQshZd+BSVmd2t3SlTN09jaADZlDZcGilsLGPRpdEjN4=; tmtalk=ver:2.0::sndbyctrlentr:1::sid:0::hidscd:1; socketio.sid=s%3Ar2T3T3C5-fyzXS0-dfuCJuW7VJwbgyAw.f3krPSz7TZQ9j2xHX9QhL5%2Bgy2JOzo4ZatZcmKKRC%2Fo; is_retina=true; ASP.NET_SessionId=4ypsip5zfvm5vkqrzxtba503"
                                )
                                .build()
                            return chain.proceed(newRequest)
                        }
                    })
                    .build()

                val picasso = Picasso.Builder(applicationContext)
                    .downloader(OkHttp3Downloader(client))
                    .build()


                picasso
                    .load("https://ilsave.onlyoffice.eu/storage/userPhotos/root/d3874710-08d6-4ea2-973e-05e6f3208a66_size_82-82.jpeg?_=1995263857")
                    .into(imageView)
            }
        })
    }

    private fun initFields() {
        toolBar = findViewById(R.id.mainToolbar)
    }
}