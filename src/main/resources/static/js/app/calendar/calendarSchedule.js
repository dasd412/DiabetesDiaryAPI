const listSet=new Set();//자바의 Set과 동일함.
const listOfEvent=new Array();

let locationOfMonth=0;
let locationOfYear=0;


class HashMap{
    constructor(){
        this.map=new Array();
    }

    put(key,value){
        this.map[key]=value;
    }

    get(key){
        return this.map[key];
    }

    containsKey(key){
        return key in this.map;
    }

    containsValue(value){
       for(let i in this.map){
         if(this.map[i]==value){
            return true;
         }
       }
       return false;
    }

    getAll(){
        return this.map;
    }

    clear(){
        this.map=new Array();
    }

    remove(key){
        delete this.map[key];
    }

    keys(){
        const keys=new Array();
        for(let i in this.map){
            keys.push(i);
        }
        return keys;
    }

    values(){
      const values=new Array();
      for(let i in this.map){
         values.push(this.map[i]);
      }
      return values;
    }

    size(){
      let count=0;
      for(let i in this.map){
         count++;
      }
      return count;
    }

}//HashMap class

class Event {
    constructor(id,fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,remark,createAt,updatedAt,writtenTime){
        this.id=id;
        this.fastingPlasmaGlucose=fastingPlasmaGlucose;
        this.breakfastBloodSugar=breakfastBloodSugar;
        this.lunchBloodSugar=lunchBloodSugar;
        this.dinnerBloodSugar=dinnerBloodSugar;
        this.remark=remark;
        this.createAt=createAt;
        this.updatedAt=updatedAt;
        this.writtenTime=writtenTime;
    }
}//event class


const hashMap=new HashMap();//(key,value)==(event의 id값(db id값이 아닌 날짜 기준 ), event 객체)


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

function scheduleAdd(year,month,day){
let sb=new StringBuffer();
sb.append(year);
sb.append("/");
sb.append(month);
sb.append("/");
sb.append(day);

let key=new StringBuffer();
key.append(year);
key.append(month);
key.append(day);

if(hashMap.get(key.toString())){
  calendarEventList();
  selectCalendarEvent(key.toString(),year,month,day);
  $("#ddModalSelect").attr("style", "display:block;");
}else{
  $("#modalYear").attr("value",year);
  $("#modalMonth").attr("value",formatNumber(month));
  $("#modalDay").attr("value",formatNumber(day));
  $("#ddModal").attr("style", "display:block;");
}
$(".left-h2").html(sb.toString());
}

function selectCalendarEvent(eventKey,year,month,day){
    const id=hashMap.get(eventKey).id;//날짜 기준이 아닌 db 기준 id

    $.ajax({
        url: "/api/diabetes/diary/"+id,
        type: 'get',
        dataType:'json',
        contentType:'application/json;charset=utf-8'
    }).done(function(data){
     const e=data.response;

      $("#FastingPlasmaGlucoseSelect").attr("value",e.fastingPlasmaGlucose);
      $("#breakFastValueSelect").attr("value",e.breakfastBloodSugar);
      $("#lunchValueSelect").attr("value",e.lunchBloodSugar);
      $("#dinnerValueSelect").attr("value",e.dinnerBloodSugar);
      $("#diaryId").attr("value",e.id);
      $("#modalYearSelect").attr("value",year);
      $("#modalMonthSelect").attr("value",formatNumber(month));
      $("#modalDaySelect").attr("value",formatNumber(day));
    });
}//get data selected


function monthDayIndex(month,day){
    for(let i=0;i<month.length;i++){
        if(month[i]==day){
            return i;
        }
    }
}

function moveMonthPre(){
    locationOfMonth--;
    screenWriteMonth();
}

function moveMonthNext(){
    locationOfMonth++;
    screenWriteMonth();
}

function moveFastMonthPre(){
    locationOfYear--;
    screenWriteMonth();
}

function moveFastMonthNext(){
    locationOfYear++;
    screenWriteMonth();
}

function screenWriteMonth(){

    let date=new Date();
    let month=date.getMonth()+1+locationOfMonth;

    if(month==0){
        locationOfYear--;
        locationOfMonth=12-Math.abs(locationOfMonth);
        month=date.getMonth()+1+locationOfMonth;
    }
    else if(month==13){
        locationOfYear++;
        locationOfMonth=locationOfMonth-12;
        month=date.getMonth()+1+locationOfMonth;
    }

    let months=[month-1,month,month+1];

    if(month==1){
        months=[12,month,month+1];
    }
    else if(month==12){
        months=[month-1,month,1];
    }

    let year=date.getFullYear()+locationOfYear;
    let monthDay=calendar.convertCalendarToArray(year,months[1]);




    let startIndex=monthDayIndex(monthDay,1);
    let lastIndex=monthDayIndex(calendar.makeOneCalendarArray(year,months[1]),calendar.getMaxDateNumberOfYear(year,months[1]))+startIndex;

    for(let i=0;i<monthDay.length;i++){
        let trIndex=parseInt(i/7);
        let tr=$("#tbody tr").eq(trIndex);
        let td=tr.children().eq((i%7)+1);
        let monthForSchedule;

        let sb=new StringBuffer();

            if(i<startIndex){
                if(months[0]==12){
                    sb.append((year-1));
                    sb.append(months[0]);
                    monthForSchedule=months[0];
                    sb.append(monthDay[i]);
                }
                else{
                    sb.append(year);
                    sb.append(months[0]);
                    monthForSchedule=months[0];
                    sb.append(monthDay[i]);
                }
            }
            else if(i<=lastIndex){
                sb.append(year);
                sb.append(months[1]);
                monthForSchedule=months[1];
                sb.append(monthDay[i]);
            }
            else{
                if(months[2]==1){
                    sb.append((year+1));
                    sb.append(months[2]);
                    monthForSchedule=months[2];
                    sb.append(monthDay[i]);
                }
                else{
                    sb.append(year);
                    sb.append(months[2]);
                    monthForSchedule=months[2];
                    sb.append(monthDay[i]);

                }
            }


            td.attr("id",sb.toString());

            td.mouseover(function(){
              td.css('cursor','pointer');
            });
            td.mouseleave(function(){
              td.css('cursor','default');
            })
            td.html("<a onclick='scheduleAdd("+year+","+monthForSchedule+","+monthDay[i]+")'>"+formatNumber((monthDay[i])+"</a>"));
    }//날짜 그리기

    for(j in listOfEvent){
       hashMap.put(convertDateFormat(listOfEvent[j].writtenTime),listOfEvent[j]);
    }//이벤트를 해시맵에 넣기

   for(let i=0;i<5;i++){
      const tr=$("#tbody tr").eq(i);
      for(let j=1;j<=7;j++){
       const td=tr.children().eq(j);
        if(hashMap.containsKey((td.attr("id")))){
            const e=hashMap.get(td.attr("id"));
            td.append(eventTagFormat(e.fastingPlasmaGlucose));
         }
      }
   }

    $("#yearMonth").text(year+"."+formatNumber(months[1]));

}//screen write month()


function eventTagFormat(fastingPlasmaGlucose) {

    var tag = new StringBuffer();

    tag.append("<p>");

    tag.append("blood sugar :"+fastingPlasmaGlucose);

    tag.append("</p>");

    return tag.toString();

}

function calendarEventList() {

    $.ajax({
        url: "/api/diabetes/diary/list",
        type: 'get'

    }).done(function(data){

    if(data.response==null||data.response.length==undefined){
     return;
    }

     for(let i=0;i<data.response.length;i++){
         if(!listSet.has(data.response[i].id)){
            listSet.add(data.response[i].id);
            const e=data.response[i];
            listOfEvent.push(new Event(e.id,e.fastingPlasmaGlucose,e.breakfastBloodSugar,e.lunchBloodSugar,e.dinnerBloodSugar,e.remark,e.createAt,e.updatedAt,e.writtenTime));
         }
     }
      screenWriteMonth();

    });
}//get calendar list


const ddForm={

    init:function(){
       const _this=this;
       const btn_save=document.querySelector("#btn-save");
       const btn_cancel=document.querySelector("#btn-cancel");

       $("#btn-save").on('click',function(){
           _this.save();
       });

       $("#btn-cancel").on('click',function(){
           $("#ddModal").attr("style", "display:none;");
           $("#FastingPlasmaGlucose").val('');
           $("#breakFastValue").val('');
           $("#lunchValue").val('');
           $("#dinnerValue").val('');
           $("#modalYear").val('');
           $("#modalMonth").val('');
           $("#modalDay").val('');
           $(".left-h2").html("");

       });

       $("#btn-save").on('mouseover',function(){
           _this.buttonHover(btn_save);
       });
       $("#btn-save").on('mouseout',function(){
         _this.buttonOut(btn_save);
       });
       $("#btn-cancel").on('mouseover',function(){
          _this.buttonHover(btn_cancel);
       });
       $("#btn-cancel").on('mouseout',function(){
          _this.buttonOut(btn_cancel);
       });



    },

    buttonHover:function(btn){
      if(btn.classList.contains('normal')){
          btn.classList.remove('normal');
          btn.classList.add('onmouseover');

      }
    },

    buttonOut:function(btn){
        if(btn.classList.contains('onmouseover')){
            btn.classList.remove('onmouseover');
            btn.classList.add('normal');
        }
    },


    save:function(){
        const data={
           fastingPlasmaGlucose:$("#FastingPlasmaGlucose").val(),
           breakfastBloodSugar:$("#breakFastValue").val(),
           lunchBloodSugar:$("#lunchValue").val(),
           dinnerBloodSugar:$("#dinnerValue").val(),
           year:$("#modalYear").val(),
           month:$("#modalMonth").val(),
           day:$("#modalDay").val()
        };

        if(data.fastingPlasmaGlucose<=0){
          swal("fasting plasma glucose must be positive number!");
          return;
        }
        else if(data.breakfastBloodSugar<=0){
           swal("breakfast blood sugar must be positive number!");
           return;
        }
        else if(data.lunchBloodSugar<=0){
           swal("lunch blood sugar must be positive number!");
           return;
        }
        else if(data.dinnerBloodSugar<=0){
           swal("dinner blood sugar must be positive number!");
           return;
        }


        $.ajax({
           type:'POST',
           url:'/api/diabetes/diary/post',
           dataType:'json',
           contentType:'application/json; charset=utf-8',
           data: JSON.stringify(data)
        }).done(function(){
          swal('save success!');
          $("#FastingPlasmaGlucose").val('');
          $("#breakFastValue").val('');
          $("#lunchValue").val('');
          $("#dinnerValue").val('');
          $("#modalYear").val('');
          $("#modalMonth").val('');
          $("#modalDay").val('');
          $("#ddModal").attr("style", "display:none;");
          calendarEventList();

        });
    }


};

const ddFormSelect={

    init:function(){
       const _this=this;
       const btn_update=document.querySelector("#btn-update");
       const btn_delete=document.querySelector("#btn-delete");
       const btn_cancel_select=document.querySelector("#btn-cancel-select")

       $("#btn-update").on('mouseover',function(){
           _this.buttonHover(btn_update);
       });
       $("#btn-update").on('mouseout',function(){
         _this.buttonOut(btn_update);
       });

       $("#btn-update").on('click',function(){
          _this.update();
       });




       $("#btn-delete").on('mouseover',function(){
         _this.buttonHover(btn_delete);
       });
       $("#btn-delete").on('mouseout',function(){
         _this.buttonOut(btn_delete);
       });
       $("#btn-delete").on('click',function(){
         _this.delete();
       });

       $("#btn-cancel-select").on('mouseover',function(){
          _this.buttonHover(btn_cancel_select);
       });
       $("#btn-cancel-select").on('mouseout',function(){
          _this.buttonOut(btn_cancel_select);
       });



    },

    buttonHover:function(btn){
      if(btn.classList.contains('normal')){
          btn.classList.remove('normal');
          btn.classList.add('onmouseover');

      }
    },

    buttonOut:function(btn){
        if(btn.classList.contains('onmouseover')){
            btn.classList.remove('onmouseover');
            btn.classList.add('normal');
        }
    },

    update : function(){
           const updateRequest={
                fastingPlasmaGlucose:$("#FastingPlasmaGlucoseSelect").val(),
                breakfastBloodSugar:$("#breakFastValueSelect").val(),
                lunchBloodSugar:$("#lunchValueSelect").val(),
                dinnerBloodSugar:$("#dinnerValueSelect").val()
           };

        const id=$("#diaryId").val();

         $.ajax({
           type:'PUT',
           url:'/api/diabetes/diary/'+id,
           dataType:'json',
           contentType:'application/json;charset=utf-8',
           data:JSON.stringify(updateRequest)
         }).done(function(data){
         const e=data.response;
         let sb=new StringBuffer();



         sb.append($("#modalYearSelect").val());
         sb.append(formatString($("#modalMonthSelect").val()));
         sb.append(formatString($("#modalDaySelect").val()));

         const writtenTime=hashMap.get(sb.toString()).writtenTime;
         const createAt=hashMap.get(sb.toString()).createAt;

         const found=listOfEvent.find(function(item){
           return item.id==e.id;
         });
          if(listOfEvent.indexOf(found)>-1){
          listOfEvent.splice(listOfEvent.indexOf(found),1);
          }
          hashMap.remove(sb.toString());

         const updatedEvent=new Event(updateRequest.id,updateRequest.fastingPlasmaGlucose,updateRequest.breakfastBloodSugar,updateRequest.lunchBloodSugar,updateRequest.dinnerBloodSugar,updateRequest.remark,createAt,new Date(),writtenTime);
         listOfEvent.push(updatedEvent);
         hashMap.put(sb.toString(),updatedEvent);

          swal('update success!');
          $("#ddModalSelect").attr("style", "display:none;");

          calendarEventList();
          window.location.reload(true);

         });
    },

    delete : function(){
       const id=$("#diaryId").val();

       $.ajax({
       type:'DELETE',
       url:'/api/diabetes/diary/'+id,
       dataType:'json',
       contentType:'application/json; charset=utf-8'


       }).done(function(data){
        const id=data.response;

        let sb=new StringBuffer();

        sb.append($("#modalYearSelect").val());
        sb.append(formatString($("#modalMonthSelect").val()));
        sb.append(formatString($("#modalDaySelect").val()));

        const found=listOfEvent.find(function(item){
                return item.id==id;
        });

        if(listOfEvent.indexOf(found)>-1){
                listOfEvent.splice(listOfEvent.indexOf(found),1);
        }

        hashMap.remove(sb.toString());

         swal('delete success!');
         $("#ddModalSelect").attr("style", "display:none;");
        calendarEventList();
        window.location.reload(true);

       });
    }
};

$(document).ready(function(){
    calendarEventList();
    $('#pre').attr('onclick', 'moveMonthPre()');
    $('#next').attr('onclick', 'moveMonthNext()');
    $('#fastPre').attr('onclick', 'moveFastMonthPre()');
    $('#fastNext').attr('onclick', 'moveFastMonthNext()');
    ddForm.init();
    ddFormSelect.init();
});
