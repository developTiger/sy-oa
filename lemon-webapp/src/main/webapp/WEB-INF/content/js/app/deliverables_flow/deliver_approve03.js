/**
 * Created by swb on 2016/9/12.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");
    check.checkAll("body", ".checkAll", ".checkBtn");

    loadData1();
    $("#formHeaderQueryBtn").click(function () {
        loadData1();
    });
    //加载数据
    function loadData1() {
        var tpl = require("text!app/tpl/delivers/deliverApproveTpl03.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }

    //点击按钮将数据传给后台  审批同意
    $("#deliverApproveOk").click(function () {
        var ideas1=  $("#idea1").val();
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
        if(ideas1.length <= 0||ideas1==null){
            alert("请填写意见");
            return false;
        }
        approveProjectOK(chk_value.toString(),ideas1);
    })

    function approveProjectOK(selectids,ideas1) {
        var options = {
            url: "ajax_approveDeliver03?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&ideas=" + ideas1,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    loadData1();
                }else{
                    alert("签核失败");
                }
            }
        };
        $.ajax(options);
    }


    //点击按钮将数据传给后台  审批拒接
    $("#deliverNo").click(function () {
        var ideas2=  $("#idea2").val();
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
        if(ideas2.length <= 0||ideas2==null){
            alert("请填写意见");
            return false;
        }
        approveProjectNO(chk_value.toString(),ideas2);
    })

    function approveProjectNO(selectids,ideas2) {
        var options = {
            url: "ajax_rejectDeliver03?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&ideas=" + ideas2,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    loadData1();
                }else{
                    alert("签核失败");
                }
            }
        };
        $.ajax(options);
    }

    //取消按钮退出模态
    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal_1").modal("hide");
    });
    $("body").delegate("#cancelModal_2", "click", function () {
        $("#myModal_2").modal("hide");
    });


})