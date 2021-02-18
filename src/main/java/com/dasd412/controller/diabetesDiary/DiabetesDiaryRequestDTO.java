package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.Writer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class DiabetesDiaryRequestDTO {

    private int fastingPlasmaGlucose;//공복 혈당(양수)
    private int breakfastBloodSugar;//아침 식사 1시간 후 혈당(양수)
    private int lunchBloodSugar;//점식 식사 1시간 후 혈당(양수)
    private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당(양수)
    private Writer writer;
    private String remark;

    public DiabetesDiaryRequestDTO() { }


    public DiabetesDiaryRequestDTO(Writer writer) {
        this(0,0,0,0,writer,"");
    }

    public DiabetesDiaryRequestDTO(int fastingPlasmaGlucose, int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, Writer writer, String remark) {
        this.fastingPlasmaGlucose = fastingPlasmaGlucose;
        this.breakfastBloodSugar = breakfastBloodSugar;
        this.lunchBloodSugar = lunchBloodSugar;
        this.dinnerBloodSugar = dinnerBloodSugar;
        this.writer = writer;
        this.remark = remark;
    }

    public DiabetesDiary toEntity() {
        return new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(fastingPlasmaGlucose)
                .breakfastBloodSugar(breakfastBloodSugar)
                .lunchBloodSugar(lunchBloodSugar)
                .dinnerBloodSugar(dinnerBloodSugar)
                .writer(writer)
                .remark(remark)
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fastingPlasmaGlucose",fastingPlasmaGlucose)
                .append("breakfastBloodSugar",breakfastBloodSugar)
                .append("lunchBloodSugar",lunchBloodSugar)
                .append("dinnerBloodSugar",dinnerBloodSugar)
                .append("writer",writer)
                .append("remark",remark)
                .toString();
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

    public Writer getWriter() {
        return writer;
    }

    public String getRemark() {
        return remark;
    }

    static public class Builder{
        private int fastingPlasmaGlucose;
        private int breakfastBloodSugar;
        private int lunchBloodSugar;
        private int dinnerBloodSugar;
        private Writer writer;
        private String remark;

        public Builder(){ }

        public Builder(DiabetesDiaryRequestDTO diabetesDiary){
            this.fastingPlasmaGlucose=diabetesDiary.fastingPlasmaGlucose;
            this.breakfastBloodSugar=diabetesDiary.breakfastBloodSugar;
            this.lunchBloodSugar=diabetesDiary.lunchBloodSugar;
            this.dinnerBloodSugar=diabetesDiary.dinnerBloodSugar;
            this.writer=diabetesDiary.writer;
            this.remark=diabetesDiary.remark;
        }

        public DiabetesDiaryRequestDTO.Builder fastingPlasmaGlucose(int fastingPlasmaGlucose){
            checkArgument(fastingPlasmaGlucose>0,"fastingPlasmaGlucose must be positive number");
            this.fastingPlasmaGlucose=fastingPlasmaGlucose;
            return this;
        }

        public DiabetesDiaryRequestDTO.Builder breakfastBloodSugar(int breakfastBloodSugar){
            checkArgument(breakfastBloodSugar>0,"breakfastBloodSugar must be positive number");
            this.breakfastBloodSugar=breakfastBloodSugar;
            return this;
        }

        public DiabetesDiaryRequestDTO.Builder lunchBloodSugar(int lunchBloodSugar){
            checkArgument(lunchBloodSugar>0,"lunchBloodSugar must be positive number");
            this.lunchBloodSugar=lunchBloodSugar;
            return this;
        }

        public DiabetesDiaryRequestDTO.Builder dinnerBloodSugar(int dinnerBloodSugar){
            checkArgument(dinnerBloodSugar>0,"dinnerBloodSugar must be positive number");
            this.dinnerBloodSugar=dinnerBloodSugar;
            return this;
        }


        public DiabetesDiaryRequestDTO.Builder writer(Writer writer){
            this.writer=writer;
            return this;
        }

        public DiabetesDiaryRequestDTO.Builder remark(String remark){
            this.remark=remark;
            return this;
        }


        public DiabetesDiaryRequestDTO build(){
            return new DiabetesDiaryRequestDTO(fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,writer,remark);
        }

    }


}
