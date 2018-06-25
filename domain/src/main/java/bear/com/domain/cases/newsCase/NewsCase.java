package bear.com.domain.cases.newsCase;

import java.util.List;

import bear.com.domain.model.News;
import bear.com.domain.repository.NewsRepository;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/25.
 */
public class NewsCase {
private NewsRepository mNewsRepository;

    public  NewsCase(NewsRepository mNewsRepository) {
        this.mNewsRepository = mNewsRepository;
    }

    public List<News>getQiuBai(){
        return mNewsRepository.getQiuBai();
    }
}
