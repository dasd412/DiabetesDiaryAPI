const obj={

    init:function(){
       const _this=this;
       const btn_save=document.querySelector("#btn-save");
       $("#btn-save").on('click',function(){
           _this.save();
       });
       $("#btn-save").on('mouseover',function(){
           _this.buttonHover(btn_save);
       });
       $("#btn-save").on('mouseout',function(){
         _this.buttonOut(btn_save);
       });






    },

    buttonHover:function(btn_save){
      if(btn_save.classList.contains('normal')){
          btn_save.classList.remove('normal');
          btn_save.classList.add('onmouseover');

      }
    },

    buttonOut:function(btn_save){
        if(btn_save.classList.contains('onmouseover')){
            btn_save.classList.remove('onmouseover');
            btn_save.classList.add('normal');
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
    },

    update:function(){

    },

    delete:function(){

    }

};

obj.init();