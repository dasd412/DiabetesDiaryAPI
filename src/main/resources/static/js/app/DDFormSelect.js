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

ddFormSelect.init();