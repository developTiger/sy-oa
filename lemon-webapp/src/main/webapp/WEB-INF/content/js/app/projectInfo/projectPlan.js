/**
 * Created by pxj on 2016/9/9.
 */
define(function (require, exports, module) {
    var List;
    List = require("../common/pagelist");
    var wDatePicker = require("wdatePicker");
    require('../init');
    require("ajaxUpload");
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var widget = require("../common/widget");
   // widget.init();
    require("jquery");
    require("select2");
    require('treeTable');

    var mode;
    var manager;
    //时间
 /*   $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });*/
    if(mode !=1){
        loadData();
    }
    $("body").delegate("#endTime_Str", "click", function () {
        wDatePicker({ dateFmt: 'yyyy',
            readOnly:true,
            minDate:'#F{$dp.$D(\'startTime_Str\')}'
        });
    });
    $("body").delegate("#startTime_Str", "click", function () {
        wDatePicker({ dateFmt: 'yyyy',
            readOnly:true,
            maxDate:'#F{$dp.$D(\'endTime_Str\')}'
        });
    });
/*    $("body").delegate("#projectPlan_EndTime", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            minDate:'#F{$dp.$D(\'projectPlan_StartTime\')}'
        });
    });
    $("body").delegate("#projectPlan_StartTime", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            maxDate:'#F{$dp.$D(\'projectPlan_EndTime\')}'
        });
    });*/
/*    //导入计划时间选择
    $("body").delegate("#Inputdate","click",function () {
        wDatePicker({
            dateFmt:'yyyy',
            readOnly:true,
            maxDate:'%y-%M-%d'
        });
    });*/
 //查询数据
    $("#projectResultQueryBtn").click(function () {
        loadData();
    });
//加载数据
    function loadData() {
        var tpl = require("text!app/tpl/projectInfo/projectPlanTpl.html");

        var url = $("#searchForm").attr("data-url");

        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }
    $("#inputPlan").click(
           function(){
            $.ajaxFileUpload({
                url: 'inputProjectPlan',
                secureuri: false,
                fileElementId: ['fileId'],
                dataType: 'text',
               // data: $("#publishNews").serializeObject(),
                success: function (data, status) {

                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("操作成功！");
                        loadData();
                    } else {
                        alert(result.msg);
                        mode=1;
                        $("#myModal").modal("show");
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
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
                        loadData();
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


    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn').each(function () {
            chk_value.push($(this).val());
        });
        // alert(chk_value);
        if(chk_value.toString()==null||chk_value.toString()==""){
            alert("数据为空");
            return false
        }
        else {
            ajax_mainProjectNO(chk_value.toString());
        }
    })

    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_planInfo_down?formNos="+selectids;
    }



    //项目计划导入，弹出对话框初始化
    $("body").delegate("#add_ProjectPlan","click",function(){
        $("#myModal").modal("show");
    });
    //项目计划导入，弹出对话框初始化
    $("body").delegate("#cancelModal_1","click",function(){
        $("#myModal").modal("hide");
    });
    //项目计划导出模板
    $("body").delegate("#outputPlan","click",function(){
        window.location.href="outputProjectPlan";
    });




})