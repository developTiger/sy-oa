/**
 * Created by zy on 2016/8/1.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    widget.init();
    var check = require("../common/checkbox");

    var wDatePicker = require("wdatePicker");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });

    require("chained");


    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#driveringListQueryBtn").click(function () {
        loadData();
    });

    function loadData() {
        var tpl = require("text!app/tpl/carflow/car_drivering_flow_Tpl.html");
        var url = $("#searchDrivering").attr("data-url");
        var data = $("#searchDrivering").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    //导出
    $("body").delegate("#daochu", "click", function () {

        var item=  $('#item').val();
        if(item>0){
            ajax_mainProjectNO();
        }else{
            alert("数据为空");
        }

    })


    function ajax_mainProjectNO() {
        window.location.href="ajax_driver_flowTpl_down";
    }
});
