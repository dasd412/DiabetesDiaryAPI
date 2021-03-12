

let chartOfMonth=0;
let chartOfYear=0;


function convertDateFormat(str){// "2020-02-01:T00:00:00" -> 202021
    const strArr=str.split('T');
    const arr=strArr[0].split('-');

    return arr[0]+formatString(arr[1])+formatString(arr[2]);
}


function formatNumber(number){// 5-> "05"
    let str=""+number;
    if(number<10&&str.indexOf('0')==-1||str.length==1){
        str="0"+number;
    }
    return str;
}

function formatString(str){// "05"->5
    if(str.indexOf(0)=='0'){
      return  parseInt(str.substring(1,2));
    }
    else{
      return parseInt(str);
    }
}

function getTime(time){
    return formatNumber(time.getHours())+":"+formatNumber(time.getMinutes());
}


function monthDayIndex(month,day){
    for(let i=0;i<month.length;i++){
        if(month[i]==day){
            return i;
        }
    }
}

function moveMonthPre(){
    chartOfMonth--;
    calculateDays();
}

function moveMonthNext(){
    chartOfMonth++;
    calculateDays();
}

function moveFastMonthPre(){
    chartOfYear--;
    calculateDays();
}

function moveFastMonthNext(){
    chartOfYear++;
    calculateDays();
}

function calculateDays(){

    let date=new Date();
    let month=date.getMonth()+1+chartOfMonth;

    if(month==0){
        chartOfYear--;
        chartOfMonth=12-Math.abs(chartOfMonth);
        month=date.getMonth()+1+chartOfMonth;
    }
    else if(month==13){
        chartOfYear++;
        chartOfMonth=chartOfMonth-12;
        month=date.getMonth()+1+chartOfMonth;
    }

    let months=[month-1,month,month+1];

    if(month==1){
        months=[12,month,month+1];
    }
    else if(month==12){
        months=[month-1,month,1];
    }

    let year=date.getFullYear()+chartOfYear;
    let monthDay=calendar.convertCalendarToArray(year,months[1]);

    let firstDayIndex=0;

    for(let i=0;i<monthDay.length;i++){
        if(monthDay[i]==1){
            firstDayIndex=i;
            break;
        }
    }

    let lastDay=monthDay[monthDay.length-1];

    for(let i=firstDayIndex+1;i<monthDay.length;i++){
      if(monthDay[i-1]>monthDay[i]){
          lastDay=monthDay[i-1];
          break;
      }
    }
    const days=new Array();
    for(let i=1;i<=lastDay;i++){
        days.push(i);
    }
    //console.log(days);//<-각 월의 초일부터 말일까지 담은 배열

    $("#yearMonth").text(year+"."+formatNumber(months[1]));

}//screen write month()



$(document).ready(function(){
    calculateDays();
    $('#chartPre').attr('onclick', 'moveMonthPre()');
    $('#chartNext').attr('onclick', 'moveMonthNext()');
    $('#chartFastPre').attr('onclick', 'moveFastMonthPre()');
    $('#chartFastNext').attr('onclick', 'moveFastMonthNext()');
});
