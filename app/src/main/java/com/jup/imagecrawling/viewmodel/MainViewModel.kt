package com.jup.imagecrawling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.jup.imagecrawling.model.GettyImageModel
import com.jup.imagecrawling.repository.ImageRepository
import com.jup.imagecrawling.utils.AppProp
import kotlinx.coroutines.*
import org.jsoup.nodes.Document

/**
* @author: JiMinLee
* @description: 이미지 뷰 작업의 비즈니스 로직
**/

class MainViewModel(private val imageRepository:ImageRepository): ViewModel() {
    private val _onReFresh = MutableLiveData<Boolean>()
    private val _onImageLoad = MutableLiveData<ArrayList<GettyImageModel>>()
    private val _onError = MutableLiveData<String>()

    val onReFresh:LiveData<Boolean> get () = _onReFresh
    val onImageLoad:LiveData<ArrayList<GettyImageModel>> get () = _onImageLoad
    val onError:LiveData<String> get () = _onError

    private val url = "https://www.gettyimages.com/photos/collaboration?mediatype=photography&phrase=collaboration&sort=best"
    private val searchQuery = ".search-content__gallery-assets img[src]"
    private val getTag = "src"

    init {
        onReFresh()
    }

    fun onReFresh() {
        _onReFresh.value = true
        onImageLoad()
    }

    private fun onImageLoad() {
        CoroutineScope(Dispatchers.IO).launch {
            imageRepository.getUrlDocument(url)?.let {
                when(it) {
                    null -> _onError.postValue(AppProp.STATUS_MESSAGE_NULL_EXCETPION.value)
                    else -> _onImageLoad.postValue(getParsingArrayList(it, searchQuery, getTag))
                }
            }
        }
    }

    /**
    * @author: JiMinLee
    * @param: [doc], [query], [tag]
    * @return: ArrayList<GettyImageModel>
    * @description: [doc]의 [query]결과 중 [tag]를 추출하여 ArrayList 리턴
    **/
    private fun getParsingArrayList(doc:Document?, query:String, tag:String): ArrayList<GettyImageModel> {
        val gettyImageModelList = arrayListOf<GettyImageModel>()
        var attr:String

        doc!!.select(query)?.forEach { e ->
            attr = e.attr(tag)

            if(attr.contains("http"))
                gettyImageModelList.add(GettyImageModel(e.attr(tag)))

        }

        return gettyImageModelList
    }
}
