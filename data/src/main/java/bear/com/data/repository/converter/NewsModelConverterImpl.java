package bear.com.data.repository.converter;

import java.util.List;

import bear.com.data.repository.db.model.NewsModel;
import bear.com.domain.model.News;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/25.
 */
public class NewsModelConverterImpl implements NewsModelConverter{
    @Override
    public List<News> getQiuBai(NewsModel newsModel) {
        if(newsModel!=null&&newsModel.data!=null&&newsModel.data.items!=null){
            return newsModel.data.items;
        }
        return null;
    }
}
