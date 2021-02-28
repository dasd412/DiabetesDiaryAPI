const listSet=new Set();//자바의 Set과 동일함.
const listOfEvent=new Array();

let locationOfMonth=0;
let locationOfYear=0;

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


function formatNumber(number){
    let str=""+number;
    if(number<10&&str.indexOf('0')==-1||str.length==1){
        str="0"+number;
    }
    return str;
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

$("#ddModal").attr("style", "display:block;");
$("#modalYear").attr("value",year);
$("#modalMonth").attr("value",formatNumber(month));
$("#modalDay").attr("value",formatNumber(day));
$(".left-h2").html(sb.toString());
}


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
    }


    $("#yearMonth").text(year+"."+formatNumber(months[1]));

}//screen write month()

function calendarEventList() {

    $.ajax({
        url: "/api/diabetes/diary/list",
        type: 'get'

    }).done(function(data){

     for(let i=0;i<data.response.length;i++){
         if(!listSet.has(data.response[i].id)){
            listSet.add(data.response[i].id);
            listOfEvent.push(data.response[i]);
         }
     }

     console.log(listSet);
     console.log(listOfEvent);

    }).fail(function(error){
    alert(JSON.stringify(error));
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
           $("#FastingPlasmaGlucose").attr("value","");
           $("#breakFastValue").attr("value","");
           $("#lunchValue").attr("value","");
           $("#dinnerValue").attr("value","");
           $("#modalYear").attr("value","");
           $("#modalMonth").attr("value","");
           $("#modalDay").attr("value","");
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
           writer:{
             name:"tester",
             email:{
               address:"dasd412@naver.com",
               name:"dasd412",
               domainName:"naver.com"
             }

           },
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
          $("#ddModal").attr("style", "display:none;");
          calendarEventList();

        }).fail(function(error){
            swal(JSON.stringify(error));
        });
    }


};

const ddFormSelect={

    init:function(){
       const _this=this;
       const btn_update=document.querySelector("#btn-update");
       const btn_delete=document.querySelector("#btn-delete");

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
           const data={
                fastingPlasmaGlucose:$("#FastingPlasmaGlucose").val(),
                breakfastBloodSugar:$("#breakFastValue").val(),
                lunchBloodSugar:$("#lunchValue").val(),
                dinnerBloodSugar:$("#dinnerValue").val(),
                writer:{
                  name:"tester",
                  email:{
                    address:"dasd412@naver.com",
                    name:"dasd412",
                    domainName:"naver.com"
                  }

                }
           };

        const id=$("#diaryId").val();

         $.ajax({
           type:'PUT',
           url:'/api/diabetes/diary/'+id,
           dataType:'json',
           contentType:'application/json;charset=utf-8',
           data:JSON.stringify(data)
         }).done(function(){
           alert('update success!');
           window.location.href='/';
         }).fail(function(error){
           alert(JSON.stringify(error));
         })



    },

    delete : function(){
       const id=$("#diaryId").val();

       $.ajax({
       type:'DELETE',
       url:'/api/diabetes/diary/'+id,
       dataType:'json',
       contentType:'application/json; charset=utf-8'


       }).done(function(){
         alert('delete success!');
         window.location.href='/';
       }).fail(function(error){
       alert(JSON.stringify(error));

       });

    }


};

$(document).ready(function(){
    calendarEventList();
    screenWriteMonth();
    $('#pre').attr('onclick', 'moveMonthPre()');
    $('#next').attr('onclick', 'moveMonthNext()');
    $('#fastPre').attr('onclick', 'moveFastMonthPre()');
    $('#fastNext').attr('onclick', 'moveFastMonthNext()');
    ddForm.init();
    ddFormSelect.init();
});
