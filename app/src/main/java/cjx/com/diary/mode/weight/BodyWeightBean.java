package cjx.com.diary.mode.weight;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * description:
 * author: bear .
 * Created date:  2017/7/24.
 */
@Entity
public class BodyWeightBean {
    @Id
    public long id;
    public String morningWeight;
    public String nightWeight;
    public String createdDate;
    @Generated(hash = 1883512768)
    public BodyWeightBean(long id, String morningWeight, String nightWeight,
            String createdDate) {
        this.id = id;
        this.morningWeight = morningWeight;
        this.nightWeight = nightWeight;
        this.createdDate = createdDate;
    }
    @Generated(hash = 413317410)
    public BodyWeightBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMorningWeight() {
        return this.morningWeight;
    }
    public void setMorningWeight(String morningWeight) {
        this.morningWeight = morningWeight;
    }
    public String getNightWeight() {
        return this.nightWeight;
    }
    public void setNightWeight(String nightWeight) {
        this.nightWeight = nightWeight;
    }
    public String getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }}
