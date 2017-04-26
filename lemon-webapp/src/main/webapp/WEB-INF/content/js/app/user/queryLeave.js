/**
 * Created by xiazl on 2017/4/18.
 */
define(function (require, exports, module) {
    require("../init");
    var list = require("../common/pagelist")
    var wDatePicker = require("wdatePicker");


    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    loadDate();
    $("#queryFormLeaveBtn").click(function () {
        loadDate();
    });
    /**
     * 数据加载
     */
    function loadDate() {
        var url = $("#searchForm").attr("data-url");
        var tpl = require("text!app/tpl/uauth/queryFormLeaveTpl.html");
        var data = $("#searchForm").serialize();
        list("#table1", tpl, url, data, 1, 10);
    }

    /**
     * 点击查看的窗口
     */
    $("body").delegate(".viewForm", "click", function () {
        var url = $(this).attr("data-url");
        //sra_fm_form_viewonly?formNo=8312360&formKind=SYY_RS_LC06_00&viewurl=forms
        window.open(encodeURI(encodeURI(url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
    });
    /**
     * 下载请假统计
     */
    $(".js_leave_form_download").on("click", function () {
        var url = "js_leave_form_download";
        location.href = url + "?t=" + new Date().getMilliseconds() + $("#searchForm").serialize();
    })

})
