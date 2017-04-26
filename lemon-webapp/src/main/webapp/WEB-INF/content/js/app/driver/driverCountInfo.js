/**
 * Created by user on 2016/6/21.
 */
define(function (require, exports, module) {
    var List;
    require('../init');
    List = require("../common/pagelist");

    require("../common/jquery.serializeObject");
    var template = require("template");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");


    loadData();
    //查询
    $("#driverCountQueryBtn").click(function () {
        loadData();
    });
    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/driver/driverCountInfoTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

})