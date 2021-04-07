const pagination=document.querySelector(".pagination");

$(document).ready(function(){
 let preId=$("#pre").children('a').attr("id");
 if(preId!=undefined){
   $("#pre").children('a').text("PREV "+(parseInt(preId)+1));
   $("#pre").children('a').css("color","black");
 }

 let nextId=$("#next").children('a').attr("id");
 if(nextId!=undefined){
  $("#next").children('a').text("NEXT "+(parseInt(nextId)+1));
  $("#next").children('a').css("color","black");
 }
 let pages=$("#page").children('a');

 for(let i=0;i<pages.length;i++){
   $("#page").children('a').eq(i).text(parseInt(pages[i].id)+1);
 }
});