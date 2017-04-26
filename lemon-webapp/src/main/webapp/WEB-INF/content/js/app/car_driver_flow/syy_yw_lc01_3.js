/**
 * Created by swb on 2016/8/2.
 */
define(function (require, exports, module) {
    require("jquery");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    });

    jQuery.validator.addMethod("decimal_vail", function(value, element) {
        value = $.trim(value);
        var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    });


    $("#appform").validate({
        rules: {
            returnStartDate:{
                required: true
            },
            returnArriveDate:{
                required: true
            },
            mileage:{
                required: true,
                decimal_vail:true
            },
            cost:{
                required: true,
                decimal_vail:true
            }
        },
        messages: {
            returnStartDate:{
                required: "请选择返程出发时间"
            },
            returnArriveDate:{
                required: "请选择返程出发时间"
            },
            mileage:{
                required: "请填写里程数",
                decimal_vail:"请正确填写里程数(可包括2小数)"
            },
            cost:{
                required: "请填写费用",
                decimal_vail:"请正确填写费用(可包括2小数)"
            }
        },
        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var d1 = $("#returnStartDate").val();//开始时间
            var d2 = $("#returnArriveDate").val();//结束时间
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            if (timestamp1 > timestamp2) {
                alert("返程出发时间不能大于返程到达时间！");
                return;
            }
            var _url = $("#apply").attr("data-url");
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("处理成功");
                        location.href = "sra_flow_dbquery";
                    }else{
                        alert("处理失败");
                    }
                }
            });
        }
    });


});
