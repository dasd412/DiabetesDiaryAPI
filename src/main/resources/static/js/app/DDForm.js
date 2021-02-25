const ddForm={

    init:function(){
       const _this=this;
       const btn_save=document.querySelector("#btn-save");
       const btn_cancel=document.querySelector("#btn-cancel");

       $("#btn-save").on('click',function(){
           _this.save();
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

ddForm.init();