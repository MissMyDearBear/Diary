package cjx.com.diary.share;

/**
 * description: bear分享入口
 * author: bear .
 * Created date:  2017/5/26.
 */
public class BearShare {


    private BearShare(){
    }


    private static class SingleHolder{
        private  static  final BearShare INSTANCE=new BearShare();
    }

    public static BearShare getIntance(){
       return SingleHolder.INSTANCE;
    }


    private void init(){

    }
}
