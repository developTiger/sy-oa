/**
 * Created by Administrator on 2016/7/5 0005.
 */
define(function (require, exports, module) {
    var List;
    require('../init');
    List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    require("chained");
    var template = require("template");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var CG = require("../common/resultCG");
    /* var widget = require("../common/widget");*/

    require('treeTable');
    var myWidGet = require("../common/myWidGet");

    check.checkAll("body", ".checkAll", ".checkBtn")

    //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            skin:"default",
            readOnly:true
        });
    });

    $(document).ready(function(){
        $("textarea").focus(function(){

        });
    });

    $("#addPatentInfoForm").validate({
        rules: {
            patent_Name:{
                required: true,
                rangelength:[1,50]
            },
            app_No:{
                required: true,
                rangelength:[1,50]
            },
            patent_Type:{
                required: true
            },
            apply_Date:{
                required: true
            },
            valid_Date:{
                required: true
            },
            winner_vali:{
                required: true
            }
        },
        messages: {
            patent_Name:{
                required:"请填写公司名称",
                rangelength:""
            },
            app_No:{
                required:"请填写专利申请号",
                rangelength:"不能超过50个字符"
            },
            patent_Type:{
                required: "请选择类型"
            },
            apply_Date:{
                required: "请填写专利申请时间"
            },
            valid_Date:{
                required: "请填写专利有效期"
            },
            winner_vali:{
                required: "请添加专利发明人"
            }
        },
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdatePatent").attr("data-url");
            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#addPatentInfoForm").serializeObject(),
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
    })


    //添加人员，弹出对话框初始化
    $("body").delegate("#add_p","click",function(){
        var win_main = (new Function("","return " + $("#sa").val()))();
        var data = $.trim($("#hiden_in").val());
        CG.init(win_main,$(".staff-rows"),data);
        $("#myModal").modal("show");
    });

    //提交按钮
    $("body").delegate("#getval", "click", function () {

        var obj = CG.vail($(".special-sele"));
        if(obj.err){
            CG.concat_data($(".special-sele"),$("#hiden_in"),$("#winner_Info"),$("#hiden_in_sb"));
            $("#myModal").modal("hide");
        }else{
            alert("第 " + obj.info + " 获奖人数据未选择或者填写或者获奖人姓名不能超过20个字符!");
        }

    });

    //取消按钮
    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal").modal("hide");
    });

    //新增专利人员按钮
    $("body").delegate("#addStaff", "click", function () {
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.add_staff(win_main,$(".staff-rows"));
    });

    //删除专利人员按钮
    $("body").delegate(".delStaff", "click", function () {
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.del_staff(win_main,$(this));
    });


    //选择事件selected
    $("body").delegate(".special-sele","change",function(){
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.change_s(win_main,$(this));
    });

})