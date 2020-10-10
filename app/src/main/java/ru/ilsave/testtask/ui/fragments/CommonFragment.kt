package ru.ilsave.testtask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mydoc.view.*
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.commonRequest.File
import ru.ilsave.testtask.model.commonRequest.Folder
import ru.ilsave.testtask.ui.activities.InfoActivity
import ru.ilsave.testtask.ui.adapter.AdapterItems
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject


class CommonFragment : Fragment(R.layout.fragment_common) {

    companion object{
        fun getNewInstance(line: String): CommonFragment {
            val CommonFragment = CommonFragment()
            val args = Bundle()
            args.putString("line",line)
            CommonFragment.arguments = args
            return CommonFragment
        }
        fun getNewInstance(listFiles: ArrayList<File>, listFolders: ArrayList<Folder> ): CommonFragment {
            val CommonFragment = CommonFragment()
            val args = Bundle()
            args.putSerializable("listFiles", listFiles )
            args.putSerializable("listFolders", listFolders)
            CommonFragment.arguments = args
            return CommonFragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_common, container, false)

        (activity as InfoActivity).supportActionBar?.title = getString(R.string.name_common_documents)

        var listFiles = arguments?.getSerializable("listFiles") as List<File>
        var listFolders = arguments?.getSerializable("listFolders") as List<Folder>

//        for (file in listFiles){
//            Log.d("ListFragment", file.toString())
//        }
        val adapterList = ArrayList<AdapterPojoObject>()
        for (file in listFiles){
            adapterList.add(AdapterPojoObject(file.title, false, true, file.created.substring(0,10)))
        }
        for (file in listFolders){
            adapterList.add(AdapterPojoObject(file.title, true, false, file.created.substring(0,10)))
        }
        view.rvItems.layoutManager = LinearLayoutManager(view.context)
        view.rvItems.adapter = AdapterItems(adapterList)

        return view
    }



}