define(function (require, exports, module) {
    var List;

    List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();//摸态框初始化
    require('treeTable');
    var myWidGet = require("../common/myWidGet");

    check.checkAll("body", ".checkAll", ".checkBtn");


    loadData();
    $("#ProductionDate").click(function () {
        loadData();
    });
//时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly: true
        });
    });

//加载数据
    function loadData() {
        var tpl = require("text!app/tpl/productionDate/productionDateTpl.html");

        var url = $("#searchForm").attr("data-url");

        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }



    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString()==null||chk_value.toString()==""){
            alert("数据为空");
            return false
        }
        else {
            ajax_mainProjectNO(chk_value.toString());
        }
    })


    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_productDate_down?formNos="+selectids;
    }



})