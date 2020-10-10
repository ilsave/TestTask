package ru.ilsave.testtask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_mydoc.*
import kotlinx.android.synthetic.main.fragment_mydoc.view.*
import ru.ilsave.testtask.R



class MyDocumentsFragment : Fragment() {

    companion object{
        fun getNewInstance(line: String): MyDocumentsFragment {
            val myDocumentsFragment = MyDocumentsFragment()
            val args = Bundle()
            args.putString("line",line)
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
        view.tvMyDocumentsFragment.text = arguments?.getString("line","LINE!").toString()
        //tvmydocuments.text = arguments?.getString("line","LINE!")

        return view
    }


}