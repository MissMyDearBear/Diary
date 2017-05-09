package cjx.com.diary.mode.diary;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by bear on 2017/5/9.
 */
@Entity
public class Diary {
    @Id(autoincrement = true)
    public long id;
    public String title;
    public String content;
    public String createDate;
    @Generated(hash = 1849712061)
    public Diary(long id, String title, String content, String createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
    @Generated(hash = 112123061)
    public Diary() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
