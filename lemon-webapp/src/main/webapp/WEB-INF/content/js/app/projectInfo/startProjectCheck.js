define(function (require, exports, module) {

    require('../init');
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");

    var widget = require("../common/widget");
    require("jquery");
    require("mutiSelect");




    $("#addpeo").click(function () {
        var txt =  $(".txtValue").val()
        if(!confirm("是否确认上述人员进行会签？")){
            window.event.returnValue = false;
        }
        if(txt.length<=0||txt==null){
            alert("请选择人员");
            window.event.returnValue = false;
        }


    $("#startprojectcheck").validate({
        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addpeo").attr("data-url");

            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#startprojectcheck").serializeObject(),
                success: function (data) {
                        alert("会签已启动");
                    window.location.href = "";
                },
                error:function (data) {
                    alert("会签启动失败");
                    window.location.href = "";
                }
            };
            $.ajax(options);
        }

    });
    })
})

