package com.dasd412.domain.diary;

import com.dasd412.domain.BaseTimeEntity;
import com.dasd412.domain.diet.HashTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Entity
public class DiabetesDiary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private int fastingPlasmaGlucose;//공복 혈당(양수)

    private int breakfastBloodSugar;//아침 식사 1시간 후 혈당(양수)

    private int lunchBloodSugar;//점식 식사 1시간 후 혈당(양수)

    private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당(양수)


    @JsonIgnore
    @OneToMany(mappedBy = "diabetesDiary" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<HashTag> hashTags=new HashSet<>();//식단 해시태그 세트 (비중복 순서 무상관이므로 리스트보다는 셋이 적합함)

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="writer_id")
    private  Writer writer;//작성한 사람

    @Column(columnDefinition = "TEXT",length=500)
    private String remark;//비고(500자 제한)


    //JPA 시스템 상 기본 생성자가 필요하다.
    public DiabetesDiary(){ }

    public DiabetesDiary(int fastingPlasmaGlucose,int breakfastBloodSugar,int lunchBloodSugar,int dinnerBloodSugar, Writer writer){
        this(null,fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,writer,"");
    }

    public DiabetesDiary(int fastingPlasmaGlucose, int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, Writer writer, String remark) {
        this(null,fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,writer,remark);
    }

    public DiabetesDiary(Long id, int fastingPlasmaGlucose, int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, Writer writer, String remark) {
       //모델 단에서 validation 하는게 효율적!
        checkNotNull(writer,"writer must be provided");

        this.id = id;
        this.fastingPlasmaGlucose = fastingPlasmaGlucose;
        this.breakfastBloodSugar = breakfastBloodSugar;
        this.lunchBloodSugar = lunchBloodSugar;
        this.dinnerBloodSugar = dinnerBloodSugar;
        this.writer = writer;
        this.remark = defaultIfNull(remark," ");
    }



    public Long getId() {
        return id;
    }

    public int getFastingPlasmaGlucose() {
        return fastingPlasmaGlucose;
    }

    public int getBreakfastBloodSugar() {
        return breakfastBloodSugar;
    }

    public int getLunchBloodSugar() {
        return lunchBloodSugar;
    }

    public int getDinnerBloodSugar() {
        return dinnerBloodSugar;
    }

    public Writer getWriter() { return writer; }

    public String getRemark() {
        return remark;
    }


    public void modifyFastingPlasmaGlucose(int fastingPlasmaGlucose) {
        checkArgument(fastingPlasmaGlucose>0,"fastingPlasmaGlucose must be positive number");
        this.fastingPlasmaGlucose = fastingPlasmaGlucose;
    }

    public void modifyBreakfastBloodSugar(int breakfastBloodSugar) {
        checkArgument(breakfastBloodSugar>0,"breakfastBloodSugar must be positive number");
        this.breakfastBloodSugar = breakfastBloodSugar;
    }

    public void modifyLunchBloodSugar(int lunchBloodSugar) {
        checkArgument(lunchBloodSugar>0,"lunchBloodSugar must be positive number");
        this.lunchBloodSugar = lunchBloodSugar;
    }

    public void modifyDinnerBloodSugar(int dinnerBloodSugar) {
        checkArgument(dinnerBloodSugar>0,"dinnerBloodSugar must be positive number");
        this.dinnerBloodSugar = dinnerBloodSugar;
    }

    public void modifyRemark(String remark) {
        checkNotNull(remark,"remark must not be null");
        checkArgument(remark.length()<=500,"remark length must be under 500");
        this.remark = remark;
    }

    public void update(int fastingPlasmaGlucose, int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, String remark){
        if(fastingPlasmaGlucose>0)
        modifyFastingPlasmaGlucose(fastingPlasmaGlucose);

        if(breakfastBloodSugar>0)
        modifyBreakfastBloodSugar(breakfastBloodSugar);

        if(lunchBloodSugar>0)
        modifyLunchBloodSugar(lunchBloodSugar);

        if(dinnerBloodSugar>0)
        modifyDinnerBloodSugar(dinnerBloodSugar);

        if(remark!=null&&remark.length()<=500){
            modifyRemark(remark);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
       if(this==obj)return true;
       if(obj==null||getClass()!=obj.getClass())return false;
       DiabetesDiary diary=(DiabetesDiary)obj;
       return Objects.equals(this.id,diary.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id",id)
                .append("fastingPlasmaGlucose",fastingPlasmaGlucose)
                .append("breakfastBloodSugar",breakfastBloodSugar)
                .append("lunchBloodSugar",lunchBloodSugar)
                .append("dinnerBloodSugar",dinnerBloodSugar)
                .append("writer",writer)
                .append("remark",remark)
                .toString();

    }

    //생성자 파리미터가 많으므로 빌더 패턴 적용

    static public class Builder{
        private  Long id;
        private int fastingPlasmaGlucose;
        private int breakfastBloodSugar;
        private int lunchBloodSugar;
        private int dinnerBloodSugar;
        private Writer writer;
        private String remark;


        public Builder(){ }

        public Builder(DiabetesDiary diabetesDiary){
            this.id=diabetesDiary.id;
            this.fastingPlasmaGlucose=diabetesDiary.fastingPlasmaGlucose;
            this.breakfastBloodSugar=diabetesDiary.breakfastBloodSugar;
            this.lunchBloodSugar=diabetesDiary.lunchBloodSugar;
            this.dinnerBloodSugar=diabetesDiary.dinnerBloodSugar;
            this.writer=diabetesDiary.writer;
            this.remark=diabetesDiary.remark;
        }

        public Builder id(long id){
            this.id=id;
            return this;
        }

        public Builder fastingPlasmaGlucose(int fastingPlasmaGlucose){
            checkArgument(fastingPlasmaGlucose>0,"fastingPlasmaGlucose must be positive number");
            this.fastingPlasmaGlucose=fastingPlasmaGlucose;
            return this;
        }

        public Builder breakfastBloodSugar(int breakfastBloodSugar){
            checkArgument(breakfastBloodSugar>0,"breakfastBloodSugar must be positive number");
            this.breakfastBloodSugar=breakfastBloodSugar;
            return this;
        }

        public Builder lunchBloodSugar(int lunchBloodSugar){
            checkArgument(lunchBloodSugar>0,"lunchBloodSugar must be positive number");
            this.lunchBloodSugar=lunchBloodSugar;
            return this;
        }

        public Builder dinnerBloodSugar(int dinnerBloodSugar){
            checkArgument(dinnerBloodSugar>0,"dinnerBloodSugar must be positive number");
            this.dinnerBloodSugar=dinnerBloodSugar;
            return this;
        }


        public Builder writer(Writer writer){
            this.writer=writer;
            return this;
        }

        public Builder remark(String remark){
            this.remark=remark;
            return this;
        }


        public DiabetesDiary build(){
            return new DiabetesDiary(id,fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,writer,remark);
        }

    }
}
