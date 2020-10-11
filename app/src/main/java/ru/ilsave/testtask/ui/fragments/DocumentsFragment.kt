package ru.ilsave.testtask.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mydoc.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.model.commonRequest.File
import ru.ilsave.testtask.model.commonRequest.Folder
import ru.ilsave.testtask.networking.RetrofitInstance
import ru.ilsave.testtask.ui.activities.InfoActivity
import ru.ilsave.testtask.ui.adapter.AdapterItems
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject


class DocumentsFragment : Fragment() {

    companion object {
        fun getNewInstance(line: String): DocumentsFragment {
            val myDocumentsFragment = DocumentsFragment()
            val args = Bundle()
            args.putString("line", line)
            myDocumentsFragment.arguments = args
            return myDocumentsFragment
        }

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

        val adapterList = ArrayList<AdapterPojoObject>()
        for (file in listFiles) {
            adapterList.add(
                AdapterPojoObject(
                    file.title,
                    false,
                    true,
                    dateMonth(file.created.substring(0, 10)),
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
                    dateMonth(file.created.substring(0, 10)),
                    file.filesCount.toString(),
                    file.id.toString()
                )
            )
        }
        var itemAdapter = AdapterItems(adapterList)
        view.rvItems.layoutManager = LinearLayoutManager(view.context)
        view.rvItems.adapter = itemAdapter
        itemAdapter.setOnItemClickListener {
            if (it.isFile) {
                Toast.makeText(view.context, "${it.name} file size: ${it.size}", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var userDb = arguments?.getSerializable("userDb") as UserDb
                GlobalScope.launch(Dispatchers.Default) {
                    val response = RetrofitInstance.api.getFolderDocuments(
                        "${userDb.userPortal}.onlyoffice.eu",
                        "asc_auth_key=${userDb.userToken}",
                        userDb.userPortal,
                        it.folderId
                    )
                    if (response.isSuccessful) {
                        val arrayListFiles = ArrayList(response.body()?.response?.files)
                        val arrayListFolders =
                            ArrayList(response.body()?.response?.folders)
                        if (arrayListFiles.isNotEmpty() || arrayListFolders.isNotEmpty()) {
                            adapterList.clear()
                            for (file in arrayListFiles) {
                                adapterList.add(
                                    AdapterPojoObject(
                                        file.title,
                                        false,
                                        true,
                                        dateMonth(file.created.substring(0, 10)),
                                        file.contentLength,
                                        "none"
                                    )
                                )
                            }
                            for (file in arrayListFolders) {
                                adapterList.add(
                                    AdapterPojoObject(
                                        file.title,
                                        true,
                                        false,
                                        dateMonth(file.created.substring(0, 10)),
                                        file.filesCount.toString(),
                                        file.id.toString()
                                    )
                                )
                            }

                            GlobalScope.launch(Dispatchers.Main) {
                                (activity as InfoActivity).supportActionBar?.title = it.name
                                itemAdapter.notifyDataSetChanged()
                            }

                            Log.d("Magic", response.body().toString())
                        } else {
                            GlobalScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    view.context,
                                    "Thats an empty folder",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }


        return view
    }

    fun dateMonth(line: String): String {
        val values = line
        val lstValues: List<String> = values.split("-").map { it -> it.trim() }
        val month = when (lstValues[1]) {
            "1" -> "January"
            "2" -> "February"
            "3" -> "March"
            "4" -> "April"
            "5" -> "May"
            "6" -> "June"
            "7" -> "July"
            "8" -> "August"
            "9" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "noInfo"
        }
        return "$month ${lstValues[2]}, ${lstValues[0]}"
    }
}