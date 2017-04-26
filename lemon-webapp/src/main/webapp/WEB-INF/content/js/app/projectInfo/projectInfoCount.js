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

    loadData1();
    //加载数据
    function loadData1() {
        var tpl = require("text!app/tpl/projectInfo/projectCountInfoTpl.html");
        var url = $("#searchForm1").attr("data-url");
        var data = $("#searchForm1").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

   /* var  i=  $("#innum").val()
    $("#tp"+ i).click(function () {
        var selected = $(this).attr("data-value");
     /!*   $.post('ajax_project_query_list1',"projectStatus:a", function(){

        })*!/

        $.get("","",function(){

        });
    })*/



})