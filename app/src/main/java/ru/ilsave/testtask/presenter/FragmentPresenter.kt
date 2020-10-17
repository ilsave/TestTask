package ru.ilsave.testtask.presenter

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ilsave.testtask.model.UserDb
import ru.ilsave.testtask.ui.adapter.AdapterPojoObject

class FragmentPresenter(val mView: MainContract.DocumentsFragmentView) :
    MainContract.FragmentPresenter {

    private val repository = Repository()


    override fun workingList(adapterPojoObject: AdapterPojoObject, userDb: UserDb, view: View) {
        GlobalScope.launch(Dispatchers.Default) {
            var myHost = "${userDb.userPortal}.onlyoffice.eu"
            var ascKey = "asc_auth_key=${userDb.userToken}"
            val response = repository.callGetFolderDocuments(
                myHost,
                ascKey,
                userDb.userPortal,
                adapterPojoObject.folderId
            )
//                val response = RetrofitInstance.api.getFolderDocuments(
//                    myHost,
//                    ascKey,
//                    userDb.userPortal,
//                    adapterPojoObject.folderId
//                )
            if (response.isSuccessful) {

                val arrayListFiles = ArrayList(response.body()?.response?.files)
                val arrayListFolders =
                    ArrayList(response.body()?.response?.folders)
                if (arrayListFiles.isNotEmpty() || arrayListFolders.isNotEmpty()) {
                    var adapterList = ArrayList<AdapterPojoObject>()
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

                        mView.updateAdapterList(adapterList, adapterPojoObject.name, view)

                    }

                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        mView.showText("Thats an empty folder")
                    }
                }
            }
        }

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