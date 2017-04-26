/**
 * Created by wwj on 2016/8/11.
 */
define(function (require, exports, module) {
    require("jquery");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    var tpl = require("text!app/tpl/carflow/syy_yw_lc01_vTpl.html");
    require("../common/templeteHelper");
    var widget = require("../common/widget");

    $("body").delegate("#init_process","click",function(){
        initProcess();
    });

    function initProcess(){
        $.ajax({
            url:"ajax_process_init",
            type:'get',
            data:null,
            success:function(data){
                alert(data);
            }
        });
    }
});
