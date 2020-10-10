package ru.ilsave.testtask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mydoc.*
import kotlinx.android.synthetic.main.fragment_mydoc.view.*
import ru.ilsave.testtask.R
import ru.ilsave.testtask.model.commonRequest.File
import ru.ilsave.testtask.model.commonRequest.Folder
import ru.ilsave.testtask.ui.adapter.AdapterItems
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject
import java.io.Serializable


class MyDocumentsFragment : Fragment() {

    companion object{
        fun getNewInstance(line: String): MyDocumentsFragment {
            val myDocumentsFragment = MyDocumentsFragment()
            val args = Bundle()
            args.putString("line",line)
            myDocumentsFragment.arguments = args
            return myDocumentsFragment
        }
        fun getNewInstance(listFiles: ArrayList<File>, listFolders: ArrayList<Folder> ): MyDocumentsFragment {
            val myDocumentsFragment = MyDocumentsFragment()
            val args = Bundle()
            args.putSerializable("listFiles", listFiles )
            args.putSerializable("listFolders", listFolders)
            myDocumentsFragment.arguments = args
            return myDocumentsFragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        //tvMyDocumentsFragment.text = arguments?.getString("line","LINE!").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mydoc, container, false)
        //val tvmydocuments = view.findViewById<TextView>(R.id.tvMyDocumentsFragment)
      //  view.tvMyDocumentsFragment.text = arguments?.getString("line","LINE!").toString()
        Log.d("ListFragment", arguments?.getSerializable("listFiles").toString())
        Log.d("ListFragment", arguments?.getSerializable("listFolders").toString())
        //tvmydocuments.text = arguments?.getString("line","LINE!")
        var listFiles = arguments?.getSerializable("listFiles") as List<File>
        var listFolders = arguments?.getSerializable("listFiles") as List<File>

//        for (file in listFiles){
//            Log.d("ListFragment", file.toString())
//        }
        val adapterList = ArrayList<AdapterPojoObject>()
        for (file in listFiles){
            adapterList.add(AdapterPojoObject(file.title, false, true, file.created))
        }
        for (file in listFolders){
            adapterList.add(AdapterPojoObject(file.title, false, true, file.created))
        }
        view.rvItems.layoutManager = LinearLayoutManager(view.context)
        view.rvItems.adapter = AdapterItems(adapterList)

        return view
    }


}