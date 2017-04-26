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
    check.checkAll("body", ".checkAll", ".checkBtn");


    require("text!app/tpl/attendance/addOrUpdateHolidayTableTpl.html");
    require("text!app/tpl/attendance/adSituationTableTpl.html");
    require("text!app/tpl/attendance/attendanceStatisticsTableTpl.html");
    require("text!app/tpl/attendance/empDynamicsTableTpl.html");
    require("text!app/tpl/attendance/takeOrOverTimeStatisticsTableTpl.html");
    require("text!app/tpl/attendance/playAttendanceTableTpl.html");
    require("text!app/tpl/attendance/allAttendanceTableTpl.html");

    loadData();

    $("#empQueryBtn").click(function () {
        var selectMonth = $(".selectMonth").find("option:selected").val();
        $("#month").html(selectMonth);
        loadData();
    });

    $("#eqBtn").click(function(){
        var selectMonth = $(".selectMonth").find("option:selected").val();

        var sBeginDate=$(".beginDate").val();
        var sEndDate=$(".endDate").val();
        var begin=new Date(sBeginDate);
        var end=new Date(sEndDate);
        var beginYear=begin.getFullYear();
        var beginMonth=begin.getMonth();
        var endYear=end.getFullYear();
        var endMonth=end.getMonth();
        if(begin > end){
            alert("结束日期小于开始日期");
            return false;
        }
        var monthReduce=(endYear-beginYear)*12 +endMonth-beginMonth;
        if(monthReduce>3) {
            alert("您只可以查看近三个月的考勤信息");
            return false;
        }

        $("#month").html(selectMonth);
        loadData();
    });

    /* ?id=<%=items[i].empId%>&date=<%=dateFormat(items[i].attDate)%>
     &deptId=<%=items[i].deptId%>&deptName=<%=items[i].deptName%>&empName=<%=items[i].empName%>*/

    $("body").delegate("#surePalyAttendance", "click", function () {
        var url = $("#doSure").attr("data-url");
        var data = $("#doSure").serialize().toString();
        var options = {
            url: encodeURI(url + "?" + data + "&t=" + new Date().getMilliseconds()),
            type: 'get',
            success: function (data) {
                if (data.isSuccess) {
                    alert("确认成功");
                    loadData();
                }
                else
                    alert(data.msg);
            }
        };
        $.ajax(options);
    });

    $("body").delegate(".Js-single-confirm", "click", function () {

        var url = $(this).attr("data-href");
        var options = {
            url: url +"&"+ new Date().getMilliseconds(),
            type: 'get',
            success: function (data) {
                if (data.isSuccess) {
                    alert("确认成功");
                    loadData();
                }
                else
                    alert(data.msg);
            }
        };
        $.ajax(options);
    });
    /*   $("#selectMonth").bind("change",function(){

     var selectMonth = $(this).val();
     var options = {
     url: "ajax_takeOrOverTimeStatistics_Query_list?selectMonth="+selectMonth+"&t=" + new Date().getMilliseconds(),
     type: 'get',
     success: function (data) {
     if (data== "success")
     alert("删除成功");
     $("#myModal").modal("hide");
     loadData();
     }
     };
     $.ajax(options);

     });*/




    function loadData() {
        var ajaxTemp = $("#ajax_attend").val();
        var tpl = require("text!app/tpl/attendance/" + ajaxTemp + "TableTpl.html");
        var url = "ajax_" + ajaxTemp + "_Query_list";
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

        var tempDay = $("#day").html();
        var selectDay = ".selectDay" + tempDay;
        var selectK_ = ".k_" + tempDay;
        $(selectDay).css("backgroundColor", "#ffd851");
        $(selectK_).each(function () {
            $(this).css("backgroundColor", "#ffd851");
        });
        var z_vaction = 0, s3_vaction = 0, h_vaction = 0, s1_vaction = 0, b2_vaction = 0, s23_vaction = 0, t_vaction = 0, g_vaction = 0, b1_vaction = 0;
        for (var t = 0; t < $(selectK_).length; t++) {
            switch ($(selectK_).get(t)) {
                case 'Z':
                    z_vaction += 1;//出差
                    break;
                case 'S3':
                    s3_vaction += 1;//事假
                    break;
                case 'H':
                    h_vaction += 1;//婚丧
                    break;
                case 'S1':
                    s1_vaction += 1;//休假
                    break;
                case 'B2':
                    b2_vaction += 1;//补休
                    break;
                case 'S23':
                    s23_vaction += 1;//产假
                    break;
                case 'T':
                    t_vaction += 1;//探亲
                    break;
                case 'G':
                    g_vaction += 1;//疗养
                    break;
                case 'B1':
                    b1_vaction += 1;//病假
                    break;
                default :
                    break;//其他，暂不处理
            }
        }
        $("#business_travel").html(z_vaction);
        $("#compassionate_leave").html(s3_vaction);
        $("#marriage_funeral").html(h_vaction);
        $("#vacation").html(s1_vaction);
        $("#take_holidays").html(b2_vaction);
        $("#maternity_leave").html(s23_vaction);
        $("#visit_family").html(t_vaction);
        $("#convalescence").html(g_vaction);
        $("#sick_leave").html(b1_vaction);
    }

    $("body").delegate("#js_attendDowload","click",function(){
        var beginDate=$(".beginDate").val();
        var endDate=$(".endDate").val();
        var loginName=$("#loginName").val();
        var deptId=$("#ew option:selected").val();
        $("input[name=sBeginDate]").val(beginDate);
        $("input[ name=sEndDate]").val(endDate);
        $("input[name=empName]").val(loginName);
        $("input[name=deptId]").val(deptId);
        $("#js_attend_dowload_forms").submit();
    });

    $("body").delegate("#DowLoadOOTS","click",function(){
        var depId=$("#dpt option:selected").val();
        var selectMonth=$("#selectMonth option:selected").val();
        var loginName=$("#loginName").val();
        $("input[name=deptId1]").val(depId);
        $("input[ name=mh]").val(selectMonth);
        $("input[name=LName]").val(loginName);
        $("#sHidden").submit();
    });

    $("body").delegate("#DowLoadADS","click",function(){
        var depId=$("#ew option:selected").val();
        var date=$(".js_date").val();
        var loginName=$("#loginName").val();
        $("input[name=deptId1]").val(depId);
        $("input[ name=date1]").val(date);
        $("input[name=LName]").val(loginName);
        $("#submitHidden").submit();
    });


    $("body").delegate(".Wdate2", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM'});
    });

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });
    $("#deleteMenu").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        setUserStaus(chk_value.toString());
    })
    function setUserStaus(selectids) {
        var options = {
            url: "ajax_deleteHoliday?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: {ids: selectids},
            success: function (data) {
                if (data == "success") {
                    alert("删除成功");
                    loadData();
                }
                else
                    alert(data);
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#deleteRole", "click", function () {
        var _url = $("#deleteRole").attr("data-url");
        var options = {
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data == "success")
                    alert("删除成功");
                $("#myModal").modal("hide");
                loadData();
            }
        };
        $.ajax(options);
    });

    var vaLi = function validates() {
        $("#addOrUpdate").validate({
            rules: {
                roleName: {
                    required: true
                },
                idCode: {
                    required: true
                },
                description: {
                    required: true
                },
                hmonth: {
                    digits: true
                }
            },
            messages: {
                roleName: {
                    required: "必填"
                },
                idCode: {
                    required: "必填"
                },
                description: {
                    required: "不能为空"
                },
                hmonth: {
                    digits: "请输入1到12之间的整数！"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addUpdateInfo").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addOrUpdate").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            $("#myModal").modal("hide")
                            loadData();
                        } else {
                            alert("操作失败");
                        }
                    }
                };
                $.ajax(options);
            }
        })
    }
    widget.init(vaLi);


});

