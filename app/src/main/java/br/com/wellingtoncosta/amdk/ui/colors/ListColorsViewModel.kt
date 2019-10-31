package br.com.wellingtoncosta.amdk.ui.colors

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import br.com.wellingtoncosta.amdk.data.remote.response.Resource
import br.com.wellingtoncosta.amdk.data.remote.response.Response
import br.com.wellingtoncosta.amdk.data.remote.response.Status
import br.com.wellingtoncosta.amdk.domain.model.Color
import br.com.wellingtoncosta.amdk.domain.repository.ColorRepository
import br.com.wellingtoncosta.amdk.ui.base.BaseViewModel
import br.com.wellingtoncosta.amdk.util.schedulers.BaseScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Wellington Costa on 31/12/2017.
 */
class ListColorsViewModel
@Inject constructor(
        private val scheduler: BaseScheduler,
        private val colorRepository: ColorRepository
) : BaseViewModel<List<Color>>() {

    override fun loadData() {

        disposable.add(colorRepository.getColors()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnSubscribe { loadingStatus.setValue(true) }
                .doAfterTerminate { loadingStatus.setValue(false) }
                .subscribe({ result ->
                    response.setValue(Response(Status.SUCCESS, result.data, null))
                }, { throwable ->
                    response.setValue(Response(Status.ERROR, null, throwable))
                }))


//        colorRepository.getColors()
//                .subscribeOn(scheduler.io())
//                .observeOn(scheduler.ui())
//                .doOnSubscribe { loadingStatus.setValue(true) }
//                .doAfterTerminate { loadingStatus.setValue(false) }
//                .subscribe({
//                    result -> response.setValue(Response(Status.SUCCESS, result.data, null))
//                }, {
//                    throwable -> response.setValue(Response(Status.ERROR, null, throwable))
//                })
    }

//    fun loadDatas(): MutableLiveData<Resource<List<Color>>> {
//        disposable.add(colorRepository.getColors()
//                .subscribeOn(scheduler.io())
//                .observeOn(scheduler.ui())
//                .doOnSubscribe { loadingStatus.setValue(true) }
//                .doAfterTerminate { loadingStatus.setValue(false) }
//                .subscribe({ result ->
//                    response1.setValue(Resource.success(result.data))
//                }, { throwable ->
//                    response1.setValue(Resource.error(throwable))
//                }))
//
//        return response1
//    }

}