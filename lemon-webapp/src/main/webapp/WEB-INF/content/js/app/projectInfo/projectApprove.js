/**
 * Created by user on 2016/8/5.
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

    loadData();
    $("#failAllQueryBtn").click(function () {
        loadData();
    })
    function loadData() {
        var url = "ajax_kg01_lot";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc01_lotTpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);



    }
})