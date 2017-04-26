/**
 * Created by admin on 2016/8/13.
 */
define(function(require,exports,module){
    require('../init');
    var List = require("../common/pagelist");
    var template = require("template");
    var check = require("../common/checkbox");
    var wDatePicker = require("wdatePicker");
    require("../common/templeteHelper2");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });
    loadData();
    check.checkAll("body", ".checkAll", ".checkBtn")


    $("#equipmentQueryBtn").click(function(){
        loadData();
    });

    function loadData(){
        var tpl = require("text!app/tpl/equipment/equipmentTpl.html");
        var url=$("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }



})
