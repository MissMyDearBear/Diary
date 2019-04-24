package cjx.com.diary.base

import android.arch.lifecycle.LiveData

/**
 * description:
 * author: bear .
 * Created date:  2019-06-10.
 */
class BaseLiveData<T> :LiveData<T>(){

    public override fun setValue(value: T) {
        super.setValue(value)
    }
}