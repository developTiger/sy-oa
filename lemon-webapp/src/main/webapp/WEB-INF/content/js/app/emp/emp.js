/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var template = require("template");

    loadData();
    $("#empQueryBtn").click(function(){
        loadData();
    });

    function loadData(){
        var tpl = require("text!app/tpl/testTpl.html");
        var url=$("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }
});