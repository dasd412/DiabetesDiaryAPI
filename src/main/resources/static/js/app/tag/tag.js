
$(document).ready(function(){
 let pageLength=$(".pagination").children('li').length;
 let start=0;
 let end=pageLength;

 let preId=$("#previousPage").children('a').attr("id");
 if(preId!=undefined){
   $("#previousPage").children('a').text("PREV "+(parseInt(preId)+1));
   $("#previousPage").children('a').css("color","black");
   start++;
 }

 let nextId=$("#nextPage").children('a').attr("id");
 if(nextId!=undefined){
  $("#nextPage").children('a').text("NEXT "+(parseInt(nextId)+1));
  $("#nextPage").children('a').css("color","black");
  end--;
 }

 console.log(start);
 console.log(end);

 let currentPage=$("#currentPage").val();

 for(let i=start;i<end;i++){
  const li=$(".pagination").children('li').eq(i);
  console.log(li);
  const a=li.children('a');
  a.text(parseInt(li.attr("id"))+1);
  if(li.attr("id")==currentPage-1){
   li.attr('class','page-item active');
  }
  else{
   li.attr('class','page-item');
  }
 }
});