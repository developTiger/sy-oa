/**
 * Created by zy on 2016/7/12 0012.
 */
define(function (require, exports, module) {
    require("jquery");
    require("mutiSelect");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    });

        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '492px'
            //dropRight:true
        });

    //check.checkAll("body", ".checkAll", ".checkBtn");
    $("#appform").validate({
       rules: {
           predictGoStartTime: {
                required: true
            },
           predictGoArriveTime: {
                required: true
            },
           carUseInfo: {
                required: true
            }
        },
        messages: {
            predictGoStartTime: {
                required: "必填"
            },
            predictGoArriveTime: {
                required: "必填"
            },
            carUseInfo: {
                required: "必填"
            }
        },
        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#qjSubmit").attr("data-url");
            var d1 = $("#predictGoStartTime").val();//预计开始时间
            var d2 = $("#predictGoArriveTime").val();//预计结束时间
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            if (timestamp1 > timestamp2) {
                alert("出发时间不能大于到达时间！");
                return;
            }
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("新增成功");
                        //if($(window.opener.document).find(".pagination li.active a")[0]) {
                        //    $(window.opener.document).find(".pagination li.active a")[0].click();
                        //}
                        //else{
                        //    $(window.opener.document).find("#secondForm")[0].click();
                        //}
                        window.close();
                    }else{
                        alert("处理失败");
                    }
                }
            });
        }

    });





});