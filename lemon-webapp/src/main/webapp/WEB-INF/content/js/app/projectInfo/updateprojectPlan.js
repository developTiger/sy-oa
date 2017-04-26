/**
 * Created by MJ005 on 2016/9/21.
 */
define(function (require, exports, module) {
    var List;
    List = require("../common/pagelist");
    var wDatePicker = require("wdatePicker");
    require('../init');
    require("ajaxUpload");
    require("../common/jquery.serializeObject");
    var validate = require("validate");
   // var widget = require("../common/widget");
    // widget.init();
    require("jquery");
    require("select2");
    require('treeTable');
    $(".top_nav").hide();
    //导入计划时间选择
    $("body").delegate("#Inputdate","click",function () {
        wDatePicker({
            dateFmt:'yyyy',
            readOnly:true,
            maxDate:'%y-%M-%d'
        });
    });
    $("body").delegate("#endTime_Str", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy',
            readOnly: true,
            minDate: '#F{$dp.$D(\'startTime_Str\')}'
        });
    });
    $("body").delegate("#startTime_Str", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy',
            readOnly: true,
            maxDate: '#F{$dp.$D(\'endTime_Str\')}'
        });
    });
    $("body").delegate("#projectPlan_EndTime", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly: true,
            minDate: '#F{$dp.$D(\'projectPlan_StartTime\')}'
        });
    });
    $("body").delegate("#projectPlan_StartTime", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly: true,
            maxDate: '#F{$dp.$D(\'projectPlan_EndTime\')}'
        });
    });
    $("#addProjectPlanInfoForm").validate({
        rules: {
            /*            projectPlan_No:{
             required: true,
             rangelength:[1,50]
             },*/
            projectPlan_InputYear_Str:{
                required: true,
                rangelength:[1,50]
            },
            projectPlan_Number:{
                required: true,
                rangelength:[1,50]
            },
            projectPlan_Name:{
                required: true,
                rangelength:[1,50]
            },
            projectPlan_Type:{
                required: true,
                rangelength:[1,50]
            },
            projectPlan_Content:{
                required: true,
                rangelength:[1,500]
            },
            projectPlan_BearUnit:{
                required: true,
                rangelength:[1,50]
            },
            projectPlan_ParticipatingUnit:{
                required: true,
                rangelength:[1,50]
            },
            startTime_Str:{
                required: true
            },
            endTime_Str:{
                required: true
            },
            projectPlan_Manager1:{
                required: true
            },
            projectPlan_Remark:{
                required: true,
                rangelength:[1,500]
            }

        },
        messages: {
            /*            projectPlan_No:{
             required: "请填写项目计划号",
             rangelength:"计划号长度不能超过50"
             },*/
            projectPlan_InputYear_Str:{
                required: "请填写项目年度"
            },
            projectPlan_Number:{
                required: "请填写项目计划编号",
                rangelength:"项目计划编号长度不能超过50"
            },
            projectPlan_Name:{
                required: "请填写项目计划名称",
                rangelength:"项目计划名称长度不能超过50"
            },
            projectPlan_Type:{
                required: "请填写项目专利类别",
                rangelength:"项目专利类别长度不能超过50"
            },
            projectPlan_Content:{
                required: "请填写项目专利内容",
                rangelength:"项目专利内容长度不能超过500"
            },
            projectPlan_BearUnit:{
                required: "请填写项目承担单位",
                rangelength:"项目承担单位长度不能超过50"
            },
            projectPlan_ParticipatingUnit:{
                required: "请填写项目参加单位",
                rangelength:"项目参加单位长度不能超过50"
            },
            startTime_Str:{
                required: "请填写项目计划开始时间"
            },
            endTime_Str:{
                required: "请填写项目计划结束时间"
            },
            projectPlan_Manager1:{
                required: "请选择项目负责人"
            },
            projectPlan_Remark:{
                required: "请填写项目计划备注",
                rangelength:"项目计划备注长度不能超过500"
            }
        },
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdatePatent").attr("data-url");
            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#addProjectPlanInfoForm").serializeObject(),
                success: function (data) {
                    if (data == "success") {
                        alert("提交成功");
                        //提交返回
                        window.history.go(-1);
                        $("#myModal").modal("hide")
                        //loadData();
                    }
                }
            };
            $.ajax(options);
        }
    });
    $(".select2_group").select2({
        width:'100%'
    });
    $("body").delegate(".ename", "change", function () {
        $("#Manager").val($(".select2_group").find("option:selected").text());
        $("#Email").val($(".select2_group").val());
    });
    judge();
    function  judge() {
        if($(".select2_group").find("option:selected").text()=="请选择"){
            $("#Manager").val($("#winner_Info").val());
        }else{
            $("#Manager").val($(".select2_group").find("option:selected").text());
        }
    }


});
