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

       $("#btn-delete").on('mouseover',function(){
         _this.buttonHover(btn_delete);
       });
       $("#btn-delete").on('mouseout',function(){
         _this.buttonOut(btn_delete);
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
    }

};

ddFormSelect.init();