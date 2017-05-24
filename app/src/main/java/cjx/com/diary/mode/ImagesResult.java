package cjx.com.diary.mode;

import java.util.List;

import cjx.com.diary.common.CommonResult;

/**
 * Created by bear on 2017/5/4.
 */

public class ImagesResult extends CommonResult {

    /**
     * status : true
     * total : 5
     * tngou : [{"count":6,"fcount":0,"galleryclass":1,"id":18,"img":"/ext/150714/e76407c9a23da57a0f30690aa7917f3e.jpg","rcount":0,"size":6,"time":1436878500000,"title":"MiStar苏小曼姿势性感诱人私房照"}]
     */

    public boolean status;
    public int total;
    public List<TngouBean> tngou;

    public static class TngouBean {
        /**
         * count : 6
         * fcount : 0
         * galleryclass : 1
         * id : 18
         * img : /ext/150714/e76407c9a23da57a0f30690aa7917f3e.jpg
         * rcount : 0
         * size : 6
         * time : 1436878500000
         * title : MiStar苏小曼姿势性感诱人私房照
         */

        public int count;
        public int fcount;
        public int galleryclass;
        public int id;
        public String img;
        public int rcount;
        public int size;
        public long time;
        public String title;
    }
}
