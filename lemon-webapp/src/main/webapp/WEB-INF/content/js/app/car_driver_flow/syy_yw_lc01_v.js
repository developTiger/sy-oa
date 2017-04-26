/**
 * Created by swb on 2016/8/2.
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

    var form =  require("../common/form");
    //初始化 添加验证 及 做提交
    form.init();

    /**
     * 渲染页面
     */
    var loadForm=function(){
        var target;
        var tpl;

        if ($("#syy_yw_lc01_v").length > 0) {
            target = $("#syy_yw_lc01_v");
            tpl = require("text!app/tpl/carflow/syy_yw_lc01_vTpl.html");
        }
        if(!target) return;
        form.render(target,tpl);
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '332px'
            //dropRight:true
        });
        $('.multiselect-container').css("width","100%");
        //initVail();
    }


    exports.pageLoad=function(){
        loadForm();

    };
    $("#appform").validate({
        rules: {
            goStartTime: {
                required: true
            },
            goArriveTime: {
                required: true
            },returnStartDate: {
                required: true
            },
            returnArriveDate: {
                required: true
            }
        },
        messages: {
            goStartTime: {
                required: "必填"
            },
            goArriveTime: {
                required: "必填"
            },
            returnStartDate: {
                required: "必填"
            },
            returnArriveDate: {
                required: "必填"
            }
        },
        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = "ajax_approve";
            var d1 = $("#goStartTime").val();//预计开始时间
            var d2 = $("#goArriveTime").val();//预计结束时间
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            var d3 = $("#returnStartDate").val();//预计开始时间
            var d4 = $("#returnArriveDate").val();//预计结束时间
            var timestamp3 = Date.parse(new Date(d3));
            var timestamp4 = Date.parse(new Date(d4));
            if (timestamp1 > timestamp2) {
                alert("实际前往出发时间不能大于实际前往到达时间！");
                return;
            }
            if (timestamp3 > timestamp4) {
                alert("返程出发时间不能大于返程到达时间！");
                return;
            }
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    }else{
                        alert("处理失败");
                    }
                }
            });
        }

    });

   /* $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    });*/
    $("body").delegate(".aaa","change",function(){
        var current = $(this).val();
        $("#returnRidePeoples").val(current);
    })

    $("body").delegate(".car_sel","change",function(){
        var current = $(this).find("option:selected");
        if ( "" == current.val()){
            $(".driver_sel").html('<option  value="" selected>请选择</option>');
            $(".driver_sel").attr("disabled",true);
            return;
        }
        show_driver(Number(current.val()));

    });

    $("body").delegate(".agrees","click",function(){
        var current = $(this);
        var car_sel = $(".car_sel");
        var driver_sel = $(".driver_sel");
        if ( "agree" == current.val() ){
            car_sel.attr("disabled",false);
            driver_sel.attr("disabled",true);
        }else if( "disagree" == current.val()){
            car_sel.attr("disabled",true);
            driver_sel.attr("disabled",true);
            $("select[class='error']").removeClass("error");
            $("label[class='error']").remove();
        }
    });


    jQuery.validator.addMethod("decimal", function(value, element) {
        //value = parseInt(value);
        var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        if (value == "0"){
            return false;
        }
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "数字或带两位小数的数字且不为负数");


    /*jQuery.validator.addMethod("select_choose", function(value, element) {
        value = parseInt(value);
        if (value == 0){
            return false;
        }
        return true;
    }, "请选择");

    var initVail = function(){
        if ($(".car_sel").length > 0){
            $("#approveForm").validate({
                rules: {
                    carId:{
                        select_choose:true
                    },
                    driverId:{
                        select_choose:true
                    }
                },
                messages: {
                    carId:{
                        select_choose:"请选择车辆"
                    },
                    driverId:{
                        select_choose:"请选择司机"
                    }

                },
                submitHandler: function (f) {
                    form.doSubmit(); //此处自定义验证逻辑 ，最后调用 form.doSubmit 提交签核
                }
            });
        }
    }*/



    /**
     * 选择司机
     */
    function show_driver(num){
        var driver_sel = $(".driver_sel");
        $.ajax({
            url: "ajax_syy_yw_lc01_driver_data?t=" + new Date().getMilliseconds(),
            type: 'get',
            data: {carId:num},
            success: function (data) {
                if (data.common){
                    var common = data.common;
                    var html = '<option value="' + common[0].id +'">'+ common[0].driverName +'</option>';
                    driver_sel.html(html);
                }else{
                    var select = data.select;
                    var html = '<option value="">请选择</option>';
                    for (var i in select){
                        html += '<option value="' + select[i].id +'">'+ select[i].driverName+'</option>';
                    }
                    driver_sel.html(html);
                }
                driver_sel.attr("disabled",false);


            }
        });
    }

});
