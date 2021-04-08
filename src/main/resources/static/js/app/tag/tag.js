
$(document).ready(function(){
 makePageList();

 const pageForm=$("#pageForm");

 $(".pagination a").click(function(e){
  e.preventDefault();
  pageForm.find('[name="page"]').val($(this).attr('href'));
  pageForm.submit();
 });

});

function makePageList(){
 let pageLength=$(".pagination").children('li').length;
 let start=0;
 let end=pageLength;

 let preId=$("#previousPage").children('a').attr("id");
 if(preId!=undefined){
   $("#previousPage").children('a').text("PREV "+(parseInt(preId)+1));
   $("#previousPage").children('a').css("color","black");
   $("#previousPage").children('a').attr("href",(parseInt(preId)+1));
   start++;
 }

 let nextId=$("#nextPage").children('a').attr("id");
 if(nextId!=undefined){
  $("#nextPage").children('a').text("NEXT "+(parseInt(nextId)+1));
  $("#nextPage").children('a').css("color","black");
  $("#nextPage").children('a').attr("href",(parseInt(nextId)+1));
  end--;
 }

 let currentPage=$("#currentPage").val();

 for(let i=start;i<end;i++){
  const li=$(".pagination").children('li').eq(i);
  const a=li.children('a');

  a.text(parseInt(li.attr("id"))+1);
  a.attr("href",parseInt(li.attr("id"))+1);

  if(li.attr("id")==currentPage-1){
   li.attr('class','page-item active');
  }
  else{
   li.attr('class','page-item');
  }
 }
}