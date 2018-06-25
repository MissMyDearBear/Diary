package bear.com.domain.repository;

import java.util.List;

import bear.com.domain.model.News;

/**
 * description:新闻数据仓库
 * author: bear .
 * Created date:  2018/6/25.
 */
public interface NewsRepository {
    List<News> getQiuBai();
}
