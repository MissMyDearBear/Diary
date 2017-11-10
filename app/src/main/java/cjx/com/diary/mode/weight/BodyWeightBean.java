package cjx.com.diary.mode.weight;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * description:
 * author: bear .
 * Created date:  2017/7/24.
 */
@Entity(indexes = {
        @Index(value = "createdDate",unique = true)
})
public class BodyWeightBean {
    public String morningWeight;
    public String nightWeight;
    @Id
    public String createdDate;
@Generated(hash = 1186850918)
public BodyWeightBean(String morningWeight, String nightWeight,
        String createdDate) {
    this.morningWeight = morningWeight;
    this.nightWeight = nightWeight;
    this.createdDate = createdDate;
}
@Generated(hash = 413317410)
public BodyWeightBean() {
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
}
    }
