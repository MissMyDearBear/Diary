package cjx.com.diary.mode;

import java.util.List;

import cjx.com.diary.common.CommonResult;

/**
 * Created by bear on 2017/5/4.
 */

public class ImagesResult extends CommonResult {

    /**
     * data : {"images":["http://img1.3lian.com/gif/more/11/2012/03/ac2aa48cd57f9b9c355cb9c7ba7e9dad.jpg","http://img1.3lian.com/gif/more/11/2012/03/fb8f4cdc908e9006287b279cb1f0e4b5.jpg","http://img1.3lian.com/gif/more/11/2012/03/09d1f2fde648b26fa57507486e1a301c.jpg","http://img1.3lian.com/gif/more/11/2012/03/d270d41b055ff815a56c7c616bd62216.jpg","http://img1.3lian.com/gif/more/11/2012/03/f01e619a06370fed7d00c14e77d0c79e.jpg","http://img1.3lian.com/gif/more/11/2012/03/0170991af5a7703eaeb30bdde49b9bcf.jpg"]}
     */

    public DataBean data;

    public static class DataBean {
        public List<String> images;
    }
}
