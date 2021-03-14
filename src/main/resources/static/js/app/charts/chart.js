const options = {
    type: 'bar',
    data: {
        labels: [],
        datasets: [{
            label: "",
            backgroundColor: [],
            borderColor: [],
            data: []
        }]
    },

    options: {
        title: {
            display: true,
            text: 'Diabetes Diary Charts',
            fontSize: 30,
            fontColor: 'rgba(46, 49, 49, 1)'
        },
        legend: {
            labels: {
                fontColor: 'rgba(83, 51, 237, 1)',
                fontSize: 15
            }
        },
        scales:
        {
            xAxes:
                [{
                    ticks: {
                        fontColor: 'rgba(27, 163, 156, 1)',
                        fontSize: '15'
                    }
                }],
            yAxes: [{
                ticks:
                {
                    beginAtZero: true,
                    fontColor: 'rgba(246, 36, 89, 1)',
                    fontSize: '15'
                }
            }]
        }
    }
};

const backgroundColor = ['rgba(255, 99, 132, 0.5)',
    'rgba(54, 162, 235, 0.5)',
    'rgba(255, 206, 86, 0.5)',
    'rgba(75, 192, 192, 0.5)',
    'rgba(153, 102, 255, 0.5)',
    'rgba(255, 159, 64, 0.5)'];

const borderColor = ['rgb(255, 99, 132,1.5)',
    'rgba(54, 162, 235, 1.5)',
    'rgba(255, 206, 86, 1.5)',
    'rgba(75, 192, 192, 1.5)',
    'rgba(153, 102, 255, 1.5)',
    'rgba(255, 159, 64, 1.5)'];

let chartArray=[];

const ctx = document.getElementById('chart').getContext('2d');
const chart = new Chart(ctx, options);

let chartOfMonth = 0;
let chartOfYear = 0;

function formatNumber(number) {// 5-> "05"
    let str = "" + number;
    if (number < 10 && str.indexOf('0') == -1 || str.length == 1) {
        str = "0" + number;
    }
    return str;
}


function monthDayIndex(month, day) {
    for (let i = 0; i < month.length; i++) {
        if (month[i] == day) {
            return i;
        }
    }
}

function moveMonthPre() {
    chartOfMonth--;
    drawCharts();
}

function moveMonthNext() {
    chartOfMonth++;
    drawCharts();
}

function moveFastMonthPre() {
    chartOfYear--;
    drawCharts();
}

function moveFastMonthNext() {
    chartOfYear++;
    drawCharts();
}

function drawCharts() {

    let date = new Date();
    let month = date.getMonth() + 1 + chartOfMonth;

    if (month == 0) {
        chartOfYear--;
        chartOfMonth = 12 - Math.abs(chartOfMonth);
        month = date.getMonth() + 1 + chartOfMonth;
    }
    else if (month == 13) {
        chartOfYear++;
        chartOfMonth = chartOfMonth - 12;
        month = date.getMonth() + 1 + chartOfMonth;
    }

    let months = [month - 1, month, month + 1];

    if (month == 1) {
        months = [12, month, month + 1];
    }
    else if (month == 12) {
        months = [month - 1, month, 1];
    }

    let year = date.getFullYear() + chartOfYear;
    let monthDay = calendar.convertCalendarToArray(year, months[1]);

    let firstDayIndex = 0;

    for (let i = 0; i < monthDay.length; i++) {
        if (monthDay[i] == 1) {
            firstDayIndex = i;
            break;
        }
    }

    let lastDay = monthDay[monthDay.length - 1];

    for (let i = firstDayIndex + 1; i < monthDay.length; i++) {
        if (monthDay[i - 1] > monthDay[i]) {
            lastDay = monthDay[i - 1];
            break;
        }
    }

    resetChart(chart);

    for (let i = 1; i <= lastDay; i++) {
         addData(chart, i, 0);
         drawBackgroundColor(chart, i);
    }


    const start=year+"-"+formatNumber(months[1])+"-01T00:00:00";
    const end=year+"-"+formatNumber(months[1])+"-"+lastDay+"T00:00:00";

    const between={
      startDate:start,
      endDate:end
    };

    $.ajax({
      url:"/api/diabetes/charts/list",
      type:'GET',
      contentType:'application/json; charset=utf-8',
      data: between
    }).done(function(data){

      if(data.response==null||data.response.length==undefined){
      return;
      }

      for(let i=0;i<data.response.length;i++){
        const e=data.response[i];
        chartArray.push(e);
      }

/*
 label : chartArray[i].writtenTime (2021-03-02T00:00:00)-> 2 //format
 data : charArray[i].<fastingPlasmaGlucose||breakfastBloodSugar||lunchBloodSugar||dinnerBloodSugar)

@ExampleCode

 label=2=getLabel(chartArray[i].writtenTime);

    chart.data.datasets.forEach((dataset) => {
         dataset.data[label]=data;
     });
     chart.update();

*/
      for(let i=0;i<chartArray.length;i++){
         const label=getLabel(chartArray[i].writtenTime);
         chart.data.datasets.forEach((dataset) => {
                  dataset.data[label-1]=chartArray[i].fastingPlasmaGlucose;//<-switch(chartArray[i].sugarType) code needed
         });
         chart.update();
      }

    });

    $("#yearMonth").text(year + "." + formatNumber(months[1]));

}//screen write month()

function getLabel(str){//"2020-02-01:T00:00:00"->1
  const strArr=str.split('T');
  const arr=strArr[0].split('-');

  return formatString(arr[2]);
}

function formatString(str){// "05"->5 , duplicated code, refactoring needed
    if(str.indexOf(0)=='0'){
      return  parseInt(str.substring(1,2));
    }
    else{
      return parseInt(str);
    }
}

function resetChart(chart){
    chartArray=[];
    chart.data.labels=[];
    chart.data.datasets.forEach((dataset) => {
        dataset.data=[];
        dataset.backgroundColor=[];
        dataset.borderColor=[];
    });
}

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function popData(chart) {//가장 오른쪽 그래프 하나 삭제
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.pop();
    });
    chart.update();
}

function drawBackgroundColor(chart, index) {
    chart.data.datasets.forEach((dataset) => {
        dataset.backgroundColor.push(backgroundColor[index % 6]);
        dataset.borderColor.push(borderColor[index % 6]);
    });

    chart.update();
}



$(document).ready(function () {
    drawCharts();
    $('#chartPre').attr('onclick', 'moveMonthPre()');
    $('#chartNext').attr('onclick', 'moveMonthNext()');
    $('#chartFastPre').attr('onclick', 'moveFastMonthPre()');
    $('#chartFastNext').attr('onclick', 'moveFastMonthNext()');
});


