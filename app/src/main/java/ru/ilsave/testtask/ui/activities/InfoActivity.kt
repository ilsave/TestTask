package ru.ilsave.testtask.ui.activities

import android.content.Context
import android.content.Intent
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
import com.mikepenz.materialdrawer.model.DividerDrawerItem
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
import ru.ilsave.testtask.networking.RetrofitInstance
import ru.ilsave.testtask.presenter.InfoPresenter
import ru.ilsave.testtask.presenter.MainContract
import ru.ilsave.testtask.ui.fragments.DocumentsFragment
import java.io.IOException

class InfoActivity : AppCompatActivity() , MainContract.InfoView {

    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader
    private lateinit var toolBar: Toolbar
    private lateinit var currentProfile: ProfileDrawerItem

    private var mPresenter: InfoPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        mPresenter = InfoPresenter(this, sharedPreference)

        val userDb = intent.getSerializableExtra("user") as? UserDb
        var editor = sharedPreference.edit()
        editor.putString("access_auth", userDb?.userToken)
        editor.putString("host", userDb?.userPortal)
        editor.apply()

    }

    override fun onStart() {
        super.onStart()

        initFields()
        initFunc()
        supportActionBar?.title = resources.getText(R.string.name_my_documents)
        mPresenter?.start()

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
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Log out")
                    .withIcon(R.drawable.ic_baseline_arrow_exit_24)
                    .withSelectable(false),
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {

                //функция нажатия на че нить из нашего drawer\а
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (position) {
                        1 -> {
                            supportActionBar?.title = resources.getText(R.string.name_my_documents)
                            mPresenter?.navigationPresenterToMyDocuments()
                        }
                        2 -> {
                            supportActionBar?.title = resources.getText(R.string.name_common_documents)
                            mPresenter?.navigationPresenterToCommonDocuments()
                        }
                        4 -> {
                           navigationToLoginScreen()
                        }
                    }
                    return false
                }
            }).build()
    }



    private fun createHeader() {

        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val name = sharedPreference.getString("name", "name")
        val email = sharedPreference.getString("email", "email")

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

    private fun initLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                val ascAuthKey = sharedPreference.getString("access_auth", "null")
                val pathImage = sharedPreference.getString("imageUrl", "imageurl")
                val host = sharedPreference.getString("host", "host")

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val newRequest: Request = chain.request().newBuilder()
                                .addHeader(
                                    "Cookie",
                                    "asc_auth_key=$ascAuthKey"
                                )
                                .build()
                            return chain.proceed(newRequest)
                        }
                    })
                    .build()

                val picasso = Picasso.Builder(applicationContext)
                    .downloader(OkHttp3Downloader(client))
                    .build()

                var url = "https://$host.onlyoffice.eu$pathImage"

                picasso
                    .load(url)
                    .into(imageView)
            }
        })
    }


    private fun initFields() {
        toolBar = findViewById(R.id.mainToolbar)
    }

    override fun navigationToLoginScreen() {
        intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun navigationToMyDocuments(documentsFragment: DocumentsFragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.frameLayout,
                documentsFragment
            ).commit()
    }

    //знаю, что можно избежать дублирования кода, но решил оставить, чтобы не запутаться
    override fun navigationToCommonDocuments(documentsFragment: DocumentsFragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.frameLayout,
                documentsFragment
            ).commit()
    }

    override fun showText(message: String) {
       Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter = null
    }
}