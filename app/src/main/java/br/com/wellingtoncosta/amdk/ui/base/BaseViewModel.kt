package br.com.wellingtoncosta.amdk.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.wellingtoncosta.amdk.data.remote.response.Resource
import br.com.wellingtoncosta.amdk.data.remote.response.Response
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Wellington Costa on 31/12/2017.
 */
abstract class BaseViewModel<T> : ViewModel() {

    val response: MutableLiveData<Response<T>> = MutableLiveData()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val disposable = CompositeDisposable()

    abstract fun loadData()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}