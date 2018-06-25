package bear.com.data.repository;

import java.util.List;

import bear.com.data.repository.converter.NewsModelConverter;
import bear.com.data.repository.db.model.NewsModel;
import bear.com.data.repository.mock.Mock;
import bear.com.data.utils.GsonUtils;
import bear.com.domain.model.News;
import bear.com.domain.repository.NewsRepository;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/25.
 */
public class NewsRepositoryImpl implements NewsRepository {
    private NewsModelConverter mConverter;

    public NewsRepositoryImpl(NewsModelConverter converter) {
        this.mConverter = converter;

    }

    @Override
    public List<News> getQiuBai() {
        NewsModel newsModel = GsonUtils.jsonToClass(Mock.getQiuBai(), NewsModel.class);
        return mConverter.getQiuBai(newsModel);
    }
}
