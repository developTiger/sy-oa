/**
 * Created by swb on 2016/7/29.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");

    var wDatePicker = require("wdatePicker");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd' });
    });

    require("chained");

    widget.init();
    check.checkAll("body", ".checkAll", ".checkBtn");

    loadData();
    $("#carListQueryBtn").click(function () {
        loadData();
    });

    function loadData() {

        var tpl = require("text!app/tpl/carflow/car_flow_infoTpl.html");
        var url = $("#searchCar").attr("data-url");
        var data = $("#searchCar").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
});
