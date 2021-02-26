let listOfEvent={};
let locationOfMonth=0;
let locationOfYear=0;


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
console.log("y"+year+"m"+month+"d"+day);
const calendarInfo={
 "year":year,
 "month":month,
 "day":day
};

$(".ddModal").attr("style", "display:block;");

}

//check validation 

function calendarScheduleAdd(){
    let summary=$("#summary").val();
    let startTime=$("#startTime").val().split(":");
    let endTime=$("#endTime").val().split(":");

    if(summary.trim()==""||summary.trim().length==0){

        swal("title","write down your title");
        return false;

    }
    else if($('#startTime').val()==""){

        swal("startTime", "write down your startTime");
        return false;
    }
    else if($('#endTime').val()==""){

        swal('endTime',"write down your endTime");
        return false;

    }else if(new Date(0,0,0,endTime[0],endTime[1]).getTime() - new Date(0,0,0,startTime[0],startTime[1]).getTime() < 0) {

        swal('time','startTime is later than endTime');
        return false;

    }else if($('#endDate').val() == '') {

        swal('endDate','write down your endDate');
        return false;

    }else if(new Date($('#endDate').val()).getTime() - new Date($('#startDate').val()).getTime() < 0) {
       
        swal('date','startDate is later than endDate');
        return false;

    }
    $("#ddForm").modal('hide');
    swal('calendar',"you need google token!");

    $.ajax({
        url:"/api/diabetes/calendar/post",
        type:'post',
        async:false,
        data:$("#frmSchedule").serialize(),
        success:function(msg){
            if(msg.isCreated){
                swal('save',"success");
            }
            else{
                swal('save',"fail");
            }
        }
    });

    calendarEventList();
    screenWriteMonth();

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
            td.html("<a onclick='scheduleAdd("+year+","+monthForSchedule+","+monthDay[i]+")'>"+formatNumber((monthDay[i])+"</a>"));
    }


    $("#yearMonth").text(year+"."+formatNumber(months[1]));

    if(listOfEvent.check){
        for(let i=0;i<listOfEvent.count;i++){
            let itemMonth=listOfEvent.start[i].getMonth()+1;
            let itemYear=listOfEvent.start[i].getFullYear();

            if((itemMonth == months[1] || itemMonth == months[0] || itemMonth == months[2])
            && (itemYear == year || itemYear == year-1 || itemYear == year+1)) {
        $('#'+itemYear+itemMonth+listOfEvent.start[i].getDate()).append(eventTagFormat(getTime(listOfEvent.start[i]), listOfEvent.title[i], listOfEvent.eventId[i], listOfEvent.description[i]));
        }

        }
    }
}//screen write month()

function eventTagFormat(time,title, eventId, description){
    let tag = new StringBuffer();

    tag.append("<p>");
    tag.append('<a data-toggle="collapse" data-target="#collapseExample'+eventId+'" aria-expanded="false" aria-controls="collapseTarget" onclick="collapse(\''+eventId+'\')">');
    tag.append(time+"  "+title);
    tag.append('</a>');
    tag.append('<div class="collapse" id="collapseTarget'+eventId+'">');

    if(description == null) {
        tag.append('<div class="well">no content!</div>');
    }else {
        tag.append('<div class="well">'+description+'</div>');
    }

    tag.append('<div style="text-align: right;"><input type="button" class="btn btn-sm btn-warning" value="modify" onclick="modifyEventModal(\''+title+'\',\''+eventId+'\',\''+description+'\')"/> ');
    tag.append('<input type="button" class="btn btn-sm btn-warning" value="delete" onclick="removeEventOne(\''+eventId+'\')"/></div>');
    tag.append('</div>');
    tag.append("</p>");

    return tag.toString();

}

function collapse(eventId) {
    $('.collapse').not('#collapseTarget'+eventId).each(function(){
        $(this).attr('class', 'collapse collapse');
    });    
}

function modifyEventModal(title, eventId, description) {

    $('#modifySummary').val(title);

    if(description != 'undefined') {
        $('#modifyDescription').val(description);
    }else {
        $('#modifyDescription').val('');
    }

    $('#modifyEventId').val(eventId);
    $('#ddFormModify').modal();

}

function modifyEvent() {

    var summary = $('#modifySummary').val();

    if(summary.trim() == '' || summary.trim().length == 0) {
        swal('title','write down your title');
        return false;    
    }

    $("#ddFormModify").modal('hide');

    $.ajax({
        url:"/api/diabetes/calendar",
        type: "put",
        async: false,
        data: $('#frmSchduleModify').serialize(),
        success: function(msg) {
            if(msg.isUpdated) {
                swal('update', 'success');
            }else {
                swal('update', 'fail');
            }
        }
    });
    calendarEventList();    
}

function removeEventOne(eventId) {

    $.ajax({
        url: "/api/diabetes/calendar",
        type: 'delete',
        async: false,

        data : {
            "eventId" : eventId,
            "calendarId" : $('#calendarId').val()
        },

        success: function(msg) {
            if(msg.isDeleted) {
                swal('delete', 'success');
            }else {
                swal('delete', 'fail');
            }
        }
    });

    calendarEventList();
}

function calendarEventList() {

    $.ajax({
        url: "/api/diabetes/calendar/eventList",
        type: 'get',
        data: {
            "calendarId" : $('#calendarId').val()
        },

        async: false,
        success: function(lists) {

            if(lists.length != 0) {

                listOfEvent.check = true;
                listOfEvent.count = lists.length;
                listOfEvent.title = new Array();
                listOfEvent.description = new Array();
                listOfEvent.start = new Array();
                listOfEvent.end = new Array();
                listOfEvent.eventId = new Array();

                $.each(lists, function(i, item){

                    listOfEvent.title[i] = item.summary;
                    listOfEvent.description[i] = item.description;
                    listOfEvent.start[i] = new Date(item.start.dateTime.value);
                    listOfEvent.end[i] = new Date(item.end.dateTime.value);
                    listOfEvent.eventId[i] = item.id;

                });

            }else {
                listOfEvent.check = false;
            }
        }
    });
}

const ddForm={

    init:function(){
       const _this=this;
       const btn_save=document.querySelector("#btn-save");
       const btn_cancel=document.querySelector("#btn-cancel");

       $("#btn-save").on('click',function(){
           _this.save();
       });

       $("#btn-cancel").on('click',function(){
           $(".ddModal").attr("style", "display:none;");
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

           }
        };

        $.ajax({
           type:'POST',
           url:'/api/diabetes/diary/post',
           dataType:'json',
           contentType:'application/json; charset=utf-8',
           data: JSON.stringify(data)
        }).done(function(){
            alert('save success!');

        }).fail(function(error){
            alert('blood sugar must be positive number !');
        });
    }


};



$(document).ready(function(){

   // calendarEventList();
    screenWriteMonth();
    $('#pre').attr('onclick', 'moveMonthPre()');
    $('#next').attr('onclick', 'moveMonthNext()');
    $('#fastPre').attr('onclick', 'moveFastMonthPre()');
    $('#fastNext').attr('onclick', 'moveFastMonthNext()');

    $(".ddModal").attr("style", "display:none;");
    ddForm.init();
});
