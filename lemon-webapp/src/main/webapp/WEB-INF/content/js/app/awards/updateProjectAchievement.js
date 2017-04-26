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
    //var widget = require("../common/widget");
    //widget.init();
    require("jquery");
    require("select2");
    require('treeTable');

    $("body").delegate(".time", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });
    //导入计划时间选择
    $("body").delegate("#nianduTime","click",function () {
        wDatePicker({
            dateFmt:'yyyy',
            readOnly:true,
            maxDate:'%y-%M-%d'
        });
    });
    $("body").delegate("#nianduDate","click",function () {
        wDatePicker({
            dateFmt:'yyyy',
            readOnly:true,
            maxDate:'%y-%M-%d'
        });
    });
    //$("body").delegate("#endTime_Str", "click", function () {
    //    wDatePicker({
    //        dateFmt: 'yyyy',
    //        readOnly: true,
    //        minDate: '#F{$dp.$D(\'startTime_Str\')}'
    //    });
    //});
    //$("body").delegate("#startTime_Str", "click", function () {
    //    wDatePicker({
    //        dateFmt: 'yyyy',
    //        readOnly: true,
    //        maxDate: '#F{$dp.$D(\'endTime_Str\')}'
    //    });
    //});
    $("body").delegate("#EndTime", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly: true,
            minDate: '#F{$dp.$D(\'BeginTime\')}'
        });
    });
    $("body").delegate("#BeginTime", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly: true,
            maxDate: '#F{$dp.$D(\'EndTime\')}'
        });
    });
    ////新增
    //var callb= function callback() {
        $("#updateProjectAchievement").validate({
            rules: {
                nianduTime: {
                    required: true
                },
                projectNo: {
                    required: true
                },
                projectName: {
                    required: true
                },
                projectPlanInfo: {
                    required: true
                },
                assumeCompany: {
                    required: true
                },
                joinComopany: {
                    required: true
                },
                BeginTime: {
                    required: true
                },
                EndTime: {
                    required: true
                },
                leaders: {
                    required: true
                }
            },
            messages: {
                nianduTime: {
                    required: "请填写项目年度"
                },
                projectNo: {
                    required: "请填写项目编号"
                },
                projectName: {
                    required: "请填写项目名称"
                },
                projectPlanInfo: {
                    required: "请填写研究内容"
                },
                assumeCompany: {
                    required: "请填写承担单位"
                },
                joinComopany: {
                    required: "请填写参加单位"
                },
                BeginTime: {
                    required: "请填写项目开始时间"
                },
                EndTime: {
                    required: "请填写项目终止时间"
                },
                leaders: {
                    required: "请选择项目长"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdatePatent").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#updateProjectAchievement").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
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
    //}
    //widget.init(callb);
    $(".select2_group").select2({
    });
    $("body").delegate(".ename", "change", function () {
        $("#leaderName").val($(".select2_group").find("option:selected").text());
        $("#leader").val($(".select2_group").val());
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
