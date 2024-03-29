let calendar={

    LEAF_YEAR:[31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
    PLAIN_YEAR:[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],

    isLeafYear:function(year){
        if((year%4==0&&year%100!=0)||year%400==0){
           return true;
        }
        return false;
    },

    incrementDaysOfYear:function(year){
        let daySum=0;

        for(let i=1;i<year;i++){
            if(this.isLeafYear(i)){
                daySum+=366;
            }
            else{
                daySum+=365;
            }
        }

        return daySum;
    },

    incrementDaysOfMonth:function(year,month){
       let daySum=this.incrementDaysOfYear(year);

       for(let i=1;i<month;i++){
           daySum+=this.PLAIN_YEAR[i-1];
       }

       if(month>=2&&this.isLeafYear(year)){
           daySum++;
       }
       return daySum;
    },

    incrementDays(year,month,day){
        return this.incrementDaysOfMonth(year,month)+day;
    },

    getMaxDateNumberOfYear:function(year,month){
        let maxDateNumber=0;
        if(this.isLeafYear(year)){
            maxDateNumber=this.LEAF_YEAR[month-1];
        }
        else{
            maxDateNumber=this.PLAIN_YEAR[month-1];
        }
        return maxDateNumber;
    },

    isBeforeDays:function(year,month){
        let days=0;
        if(month==1){
            days=this.getMaxDateNumberOfYear(year-1,12);
        }
        else{
            days=this.getMaxDateNumberOfYear(year,month-1);
        }

        return days;
    },

    convertCalendarToArray:function(year,month){
        let dayOfWeek=(this.incrementDays(year,month,1))%7;
        let beforeLastDay=this.isBeforeDays(year,month);
        let startLastDay=beforeLastDay-dayOfWeek+1;
        let lastDay=this.getMaxDateNumberOfYear(year,month);
        let lastWeekDays=(7-(dayOfWeek+lastDay)%7)%7;

        if(this.isLeafYear(year)){
            startLastDay++;
            lastWeekDays++;
        }

        let dayArray=new Array();

        let cnt=0;

        for(let i=startLastDay;i<=beforeLastDay;i++,cnt++){
            dayArray[cnt]=i;
        }

        for(let i=1;i<=lastDay;i++,cnt++){
            dayArray[cnt]=i;
        }

        for(var i=1;i<=lastWeekDays;i++,cnt++){
            dayArray[cnt]=i;
        }

        return dayArray;
    },

    makeOneCalendarArray:function(year,month){
        let lastDay=this.getMaxDateNumberOfYear(year,month);

        let dayArray=new Array();

        let cnt=0;

        for(let i=1;i<=lastDay;i++,cnt++){
            dayArray[cnt]=i;
        }

        return dayArray;
    }
}//const obj