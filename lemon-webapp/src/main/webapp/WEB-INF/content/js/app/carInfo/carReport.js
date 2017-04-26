/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    widget.init();
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var myWidGet = require("../common/myWidGet");


    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#carReportQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/carInfo/carReportTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }







    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });







    /*$("body").delegate("#addOrUpdateCar", "click", function () {
     var _url = $("#addOrUpdateCar").attr("data-url");

     var options = {
     url: _url + "?t=" + new Date().getMilliseconds(),
     type: 'post',
     data: $("#addCarInfoForm").serializeObject(),
     success: function (data) {
     if (data == "success")
     alert("新增成功");
     $("#myModal").modal("hide")
     loadData();
     }
     };
     $.ajax(options);
     });*/


});