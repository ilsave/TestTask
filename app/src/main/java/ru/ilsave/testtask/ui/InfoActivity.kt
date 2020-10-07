package ru.ilsave.testtask.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
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
import ru.ilsave.testtask.R

class InfoActivity : AppCompatActivity() {

    private lateinit var drawer: Drawer
    private lateinit var header: AccountHeader
    private lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        setSupportActionBar(toolBar)
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
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                Picasso.get()
                    .load("https://www.google.com/search?q=minato+png&tbm=isch&ved=2ahUKEwiT39KuiKLsAhVQAHcKHTglCPUQ2-cCegQIABAA&oq=minato+p&gs_lcp=CgNpbWcQARgAMgIIADICCAAyBAgAEB4yBAgAEB4yBAgAEB4yBAgAEB4yBAgAEB4yBAgAEB4yBAgAEB4yBAgAEB46BAgAEENQllxYqGFgy3BoAHAAeACAAeYBiAGgA5IBBTAuMS4xmAEAoAEBqgELZ3dzLXdpei1pbWfAAQE&sclient=img&ei=jnx9X5PgG9CA3AO4yqCoDw&bih=938&biw=1853&client=ubuntu#imgrc=h5McDHDkqrNAyM")
                    .placeholder(placeholder).into(imageView)
            }

            override fun cancel(imageView: ImageView) {
                Picasso.get().cancelRequest(imageView)
            }

            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                super.set(imageView, uri, placeholder, tag)
            }

            override fun placeholder(ctx: Context): Drawable {
                return super.placeholder(ctx)
            }

            override fun placeholder(ctx: Context, tag: String?): Drawable {
                return super.placeholder(ctx, tag)
            }

        })
    }

    private fun createHeader() {
        header = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem().withName("Ilya Gushchin")
                    .withEmail("bulldoo@yandex.ru")
                 .withIcon("https://www.google.com/search?q=minato&client=ubuntu&hs=49m&channel=fs&sxsrf=ALeKk03YOQw-7CTU58-TSaWu7zLNdCXjfA:1602057508633&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjelNilgaLsAhVEmIsKHeGJAE0Q_AUoAXoECBgQAw&biw=1853&bih=938#imgrc=lFm7YzcU2G3BQM")
            ).build()
    }

    private fun initFields() {
        toolBar = findViewById(R.id.mainToolbar)
    }
}