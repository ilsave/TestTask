package ru.ilsave.testtask.ui.fragments

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mydoc.view.*
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.model.commonRequest.File
import ru.ilsave.testtask.model.commonRequest.Folder
import ru.ilsave.testtask.presenter.FragmentPresenter
import ru.ilsave.testtask.presenter.MainContract
import ru.ilsave.testtask.ui.activities.InfoActivity
import ru.ilsave.testtask.ui.adapter.AdapterItems
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject


class DocumentsFragment : Fragment(), MainContract.DocumentsFragmentView {


    lateinit var adapterList: ArrayList<AdapterPojoObject>
    lateinit var itemAdapter: AdapterItems

    var mPresenter: FragmentPresenter? = null

    companion object {
        fun getNewInstance(
            listFiles: ArrayList<File>,
            listFolders: ArrayList<Folder>,
            userDb: UserDb
        ): DocumentsFragment {
            val myDocumentsFragment = DocumentsFragment()
            val args = Bundle()
            args.putSerializable("listFiles", listFiles)
            args.putSerializable("listFolders", listFolders)
            args.putSerializable("userDb", userDb)
            myDocumentsFragment.arguments = args
            return myDocumentsFragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mydoc, container, false)

        Log.d("ListFragment", arguments?.getSerializable("listFiles").toString())
        Log.d("ListFragment", arguments?.getSerializable("listFolders").toString())

        var listFiles = arguments?.getSerializable("listFiles") as List<File>
        var listFolders = arguments?.getSerializable("listFolders") as List<Folder>

        mPresenter = FragmentPresenter(this)

        adapterList = ArrayList<AdapterPojoObject>()
        for (file in listFiles) {
            adapterList.add(
                AdapterPojoObject(
                    file.title,
                    false,
                    true,
                    mPresenter!!.dateMonth(file.created.substring(0, 10)),
                    file.contentLength,
                    "none"
                )
            )
        }
        for (file in listFolders) {
            adapterList.add(
                AdapterPojoObject(
                    file.title,
                    true,
                    false,
                    mPresenter!!.dateMonth(file.created.substring(0, 10)),
                    file.filesCount.toString(),
                    file.id.toString()
                )
            )
        }

        itemAdapter = AdapterItems(adapterList)
        view.rvItems.layoutManager = LinearLayoutManager(view.context)
        view.rvItems.adapter = itemAdapter


        itemAdapter.setOnItemClickListener {
            if (it.isFile) {
                Toast.makeText(view.context, "${it.name} file size: ${it.size}", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var userDb = arguments?.getSerializable("userDb") as UserDb
                mPresenter!!.workingList(it, userDb, view)
            }
        }
            return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter = null
    }

    override fun cleanAdapterList() {
        adapterList.clear()
    }


    override fun updateAdapterList(list: ArrayList<AdapterPojoObject>, toolBarName: String, view: View) {
       cleanAdapterList()
        Log.d("LISTadapter", list.toString())
        adapterList = list
       (activity as InfoActivity).supportActionBar?.title = toolBarName

        itemAdapter = AdapterItems(adapterList)
        view.rvItems.layoutManager = LinearLayoutManager(view.context)
        view.rvItems.adapter = itemAdapter

        itemAdapter.notifyDataSetChanged()
        itemAdapter.setOnItemClickListener {
            if (it.isFile) {
                Toast.makeText(view.context, "${it.name} file size: ${it.size}", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var userDb = arguments?.getSerializable("userDb") as UserDb
                mPresenter!!.workingList(it, userDb, view)
            }
        }

    }


    override fun showText(message: String) {
        Toast.makeText(view?.context, message, Toast.LENGTH_SHORT).show()
    }
}