package com.mckj.mc.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by drq
 * date: 2020/8/1 22:04.
 */
class HomeViewModel() : ViewModel() {
    private val repo: HomeRepository by lazy { HomeRepository() }


    private val name: MutableLiveData<String> =
        MutableLiveData<String>().also {
            it.value = "init"
        }


    fun getName(): LiveData<String> {
        return name
    }

    fun changeName() {

        name.value = "newName${repo.index}"
        repo.index++
    }
}