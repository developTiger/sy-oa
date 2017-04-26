/**
 * Created by temp on 2016/6/30.
 */


/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("../common/templeteHelper");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    var validate = require("validate");
    check.checkAll("body", ".checkAll", ".checkBtn")
    widget.init();

    //var attSum = require("text!app/tpl/attend/takeAttendSummeryTpl.html");
    var nearSeven = require("text!app/tpl/attend/attendSituationTableTpl.html");

    //loadSumData();
    //function loadSumData() {
    //    var tpl = attSum;
    //    var url = "ajax_attendSum_Query_list";
    //    var data = $("#attendSumForm").serialize().toString();
    //    List("#table", tpl, url, data, 1, 10);
    //}
    //
    //$("#attendSumForm").click(function () {
    //    loadSumData();
    //});
    loadAttendOver();
    function loadAttendOver() {
        var url = $("#searchForm").attr("data-url");
        if (url != "ajax_playAttend_Query_list") {
            var tpl = require("text!app/tpl/attend/takeOrOverAttendTimeStatisticsTableTpl.html");
            var data = $("#searchForm").serialize().toString();
            List("#table", tpl, url, data, 1, 10);
        }
    };
    $("#attendOver").click(function () {
        loadAttendOver();
    });

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    $("#QueryBtn").click(function () {
        var name = $("#loginName").val();
        var attDate = $("#attDate").val();
        window.location.href = "sra_a_playAttend?t=" + new Date().getMilliseconds() + "&name=" + encodeURI(encodeURI(name)) + "&attDate=" + attDate;
    });


    /**
     * 修改个人考勤
     *
     */
    $("body").on("click", "#update_attend", function () {
        var _url = $("#update_attend").attr("data-url");
        var attdate = $("#attDate").val();
        var options = {
            url: _url,
            type: 'post',
            data: $("#addOrUpdateAttend").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("修改成功");
                    window.location.href = "sra_a_playAttend?t=" + new Date().getMilliseconds() + "&attDate=" + attdate;
                }
                else
                    alert(data.msg);
                $("#myModal").modal("hide");
            }
        }
        $.ajax(options);
    });
    /**
     * 个人打考勤
     */
    $(".doPlayOneAttend").click(function () {
        var attdate = $("#attDate").val();
        var _url = $(this).attr("data-href");
        var type = $(this).parent().prev().prev().find("option:selected").val();
        var options = {
            url: _url + type,
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("确认成功");
                    window.location.href = "sra_a_playAttend?t=" + new Date().getMilliseconds() + "&attDate=" + attdate;
                }
                else
                    alert(data.msg);
            }

        };
        $.ajax(options);
    });
    /**
     * 集体打考勤
     */
    $("#surePalyAttend").click(function () {

        var attdate = $("#attDate").val();
        var attIds = [];
        var empIds = [];
        $(".empName").each(function () {
            empIds.push($(this).attr("data-pid"));
            attIds.push($(this).attr("data-id"));
        })
        var types = [];
        $(".attendType").each(function () {
            types.push($(this).val());
        });

        var sdateTime = $("#attDate").val();//时间的获取

        var options = {
            url: "ajax_doPlayAttend" + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: {attIds: attIds, empIds: empIds, att_type: types, sdateTime: sdateTime},
            success: function (data) {
                if (data.isSuccess) {
                    alert("确认成功");
                    window.location.href = "sra_a_playAttend?t=" + new Date().getMilliseconds() + "&attDate=" + attdate;
                }
                else
                    alert(data.msg);
            }

        };
        $.ajax(options);
    });

    /**
     * 下载统计
     */
    $("body").delegate("#DowLoadSum", "click", function () {
        var _url = "downloadAttendSum_statistics";
        var name = $("#loginName").val();
        var begin = $("#beginTime").val();
        var end = $("#endTime").val();

        window.location.href = _url + "?name=" + encodeURI(encodeURI(name)) + "&begin=" + begin + "&end=" + end + "&t=" + new Date().getMilliseconds();
    });

    loadNearSeven();
    function loadNearSeven() {
        var tpl = nearSeven;
        var url = "ajax_attendSituation_Query_list";
        List("#tableSevenDays", tpl, url, null, 1, 10);

    }

    /**
     * 下载近七日考勤状况
     */
    $("body").delegate("#DowLoadSevenDays", "click", function () {
        var _url = "ajax_attendSituation_down_list";
        window.location.href = _url + "?t=" + new Date().getMilliseconds();
    });

    /**
     * 休假、加班、补休统计表
     * 下载统计
     */
    $("body").delegate("#DowLoadAttend", "click", function () {
        var _url = "ajax_download_attend";
        var name = $("#loginName").val();
        var begin = $("#begin").val();
        var end = $("#end").val();
        var deptId = $("#dpt").find("option:selected").val();
        if (deptId == undefined)deptId = "";
        window.location.href = _url + "?empName=" + encodeURI(encodeURI(name)) + "&begin=" + begin + "&end=" + end + "&deptId=" + deptId + "&t=" + new Date().getMilliseconds();
    });

});

