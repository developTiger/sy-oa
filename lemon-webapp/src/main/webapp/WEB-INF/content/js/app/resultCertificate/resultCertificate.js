/**
 * Created by pxj on 2016/9/29.
 */
define(function (require, exports, module) {
    List = require("../common/pagelist");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    loadData();
    $("#booksQueryBtn").click(function () {
        loadData();
    });
    //时间
/*    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            skin:"default",
            readOnly:true
        });
    });*/
    $("body").delegate("#end_Time", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            minDate:'#F{$dp.$D(\'begin_Time\')}'
        });
    });
    $("body").delegate("#begin_Time", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            maxDate:'#F{$dp.$D(\'end_Time\')}'
        });
    });
    
//加载数据
    function loadData() {
        var tpl = require("text!app/tpl/resultCertificate/resultCertificateTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }


});