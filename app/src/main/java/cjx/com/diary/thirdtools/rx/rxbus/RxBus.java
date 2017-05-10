package cjx.com.diary.thirdtools.rx.rxbus;

import com.hwangjr.rxbus.Bus;

/**
 * Created by bear on 2017/5/10.
 */

public  class RxBus {
    private static Bus sBus;
    public static synchronized Bus get(){
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}
