/**
 * Created by admin on 2016/8/15.
 */
define(function(require,exports,module){

    require('../init');
    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");
    var tpl_repair = require("text!app/tpl/equipForms/syy_ay_lc02_a_repairTpl.html");
    var tpl_maintain = require("text!app/tpl/equipForms/syy_ay_lc02_a_maintainTpl.html");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_equipmentMaintenance_v").length>0) {
            target=$("#syy_equipmentMaintenance_v");
            var tpl = require("text!app/tpl/equipForms/syy_ay_lc02_v_maintainOrRepairTpl.html");
            form.render(target,tpl);
        }
    }

    exports.pageLoad=function(){
        loadForm();

    };





    $body.delegate("#name","change",function(){
        var id = $(this).find("option:selected").val();
        $.ajax({
            url:"ajax_query_equipmentInfo"+"?t="+new Date().getMilliseconds()+"&id="+id,
            type:"get",
            success:function(data){
                if(data){
                    //alert("获取设备信息成功！");
                    $("#propertyNum").val(data.propertyNum);
                    $("#standard").val(data.standard);
                    $("#factory").val(data.factoryName);
                    $("#outFactoryNum").val(data.outFactoryNum);
                    $("#original").val(data.original);
                    $("#wproductTime").val((formatDate(data.productTime)));
                    $("#hidEquipmentNum").val(data.propertyNum);
                    //$("#resId").val(data.id);

                }
            }
        })
    })

    var deptName=$("input[name='deptName']").val()
    $("#unit").val(deptName);
    if($("#name").find("option:selected").text()!="请选择"){
        $("#name").trigger("change");
    }



    $("#appform").validate({
        rules: {
            num: {
                digits: true
            },
            cost:{
                number:true
            }
        },
        messages: {
            num: {
                digits: "请输入一个大于0的正整数！"
            },
            cost: {
                number:"请输入一个大于0的数"
            }

        },


        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#equipmentMaintenanceSubmit").attr("data-url");
            $("#hidEquipmentNum").val($("#measuringName").find("option:selected").text());
            $.ajax({
                url:_url+"?t="+new Date().getMilliseconds(),
                type:"post",
                data:$("#appform").serializeObject(),
                success:function(data){
                    if(data.isSuccess){
                        alert("申请成功！");
                        window.close();
                    }                 else
                        alert(data.msg);
                }
            })
        }
    })

    //js 时间转换
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };




})