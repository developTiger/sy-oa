/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("jquery");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    //require("printArea");
    //不知为何注释掉
    //var tpl = require("text!app/tpl/forms/syy_rs_lc06_vTpl.html");
    $("body").delegate(".downLoadPref", "click", function () {
        var url = $(this).attr("data-url");
        var formNo = $(this).attr("data-rurl");
        window.location.href = url + "?formNo=" + formNo;
    });


    require("../common/templeteHelper");
    $("body").delegate(".Wdate", "click", function () {

        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("body").delegate(".Wdate1", "click", function () {

        wDatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'strToTime\')}'});
    });
    $("body").delegate(".Wdate2", "click", function () {

        wDatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'strFromTime\')}'});
    });

    $("body").delegate(".Wdate3", "click", function () {
        var beginTime = $.trim($("#time").text());
        wDatePicker({dateFmt: 'yyyy-MM-dd', minDate: beginTime});
    });


    var widget = require("../common/widget");
    var form = require("../common/form")


    //初始化 添加验证 及 做提交
    form.init(function () {
        var actualTime = $("#actualTime").val();
        var time = $("#time").text();
        var timestamp1 = Date.parse(new Date(time));
        var timestamp2 = Date.parse(new Date(actualTime));
        var count = $("#timeCount").val();
        if (count != undefined && isNaN(count)) {
            alert("仅填数字!");
            return;
        }
        ;
        if (!isNaN(count) && Number(count) < 0) {
            alert("不小于0的数字！");
            return;
        }
        if (timestamp1 > timestamp2) {
            alert("实际返岗时间不能早于请假开始时间！");
            return;
        }
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm = function () {
        var target;
        if ($("#syy_leave_v").length > 0) {
            target = $("#syy_leave_v");
            var tpl = require("text!app/tpl/forms/syy_rs_lc06_vTpl.html");
            form.render(target, tpl);
        }
    }


    exports.pageLoad = function () {
        loadForm();
        var fromTime = $(".js_fromTime").html();
        var toTime = $("#js_toTime").html();
        var d = DateDiffNoWeekDay(fromTime, toTime);
        $("#daySum").text("请假天数：" + d);
    };

    //基层领导审核，计算实际请假天数
    $("body").on("blur", "#actualTime", function () {
        var actualTime = $(this).val();
        var time = $("#time").text();

        if (actualTime != "") {
            $("#timeCount").val(DateDiffNoWeekDay(time, actualTime) - 1);//当天返岗就是为0天

        }

    });


    //当流程步骤为第3步时，申请人销假，隐藏退回按钮，只能选择同意，不能退回
    //当流程为院领导申请时，同上
    $(document).ready(function () {
        var clStep = $("input[name='clStep']").val();
        var formKind = $("#formKind").val();
        if (clStep == "4" && formKind == "SYY_RS_LC06_00") {
            $("#goBack").hide();
        }
        if (clStep == "4" && formKind == "SYY_RS_LC06_01") {
            $("#goBack").hide();
        }
        if (clStep == "4" && formKind == "SYY_RS_LC06_04") {
            $("#goBack").hide();
        }
        if (clStep == "4" && formKind == "SYY_RS_LC06_05") {
            $("#goBack").hide();
        }
    })

    //表单申请流程 提交
    $("#appform").validate({
        rules: {
            leaveType: {
                required: true
            },
            reason: {
                required: true
            },
            target: {
                required: true
                //digits:true
            },
            strFromTime: {
                required: true
            },
            strToTime: {
                required: true
            },
            countTime: {
                required: true
            }
        },
        messages: {
            leaveType: {
                required: "必须选择"
            },
            reason: {
                required: "必填"
            },
            target: {
                required: "必填"
            },
            strFromTime: {
                required: "必填"
            },
            strToTime: {
                required: "必填"
            },
            countTime: {
                required: "必填"
            }

        },

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#qjSubmit").attr("data-url");


            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {

                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    } else
                        alert(data.msg);
                }
            });
        }

    });


    $("body").delegate("#strToTime", "blur", function () {
        dayfunction();
    });
    $("body").delegate("#strFromTime", "blur", function () {
        dayfunction();
    });

    function dayfunction() {
        var d1 = $("#strFromTime").val();//开始时间
        var d2 = $("#strToTime").val();//结束时间
        if (d1 == "" || d2 == "") return;
        $("#countTime").val(DateDiffNoWeekDay(d1, d2));
    }

    function DateDiffNoWeekDay(sDate1, sDate2) {


        var oDate1 = new Date(sDate1);
        var oDate2 = new Date(sDate2);
        //查看日期格式是否相等 是，那么请假就是0天,实际上也没必要这样做
        if (sDate1 == sDate2) return 1;//相同时间的时候代表1天
        else {
            days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) + 1;//两头包括

            var tempDate = oDate1;
            while (tempDate.getTime() <= oDate2.getTime()) {

                if (checkWeekDay(DateToString(tempDate)) & days > 0) {
                    //如果是周末,天数减1
                    days--;
                }
                tempDate = addDays(tempDate, 2);//加一天
            }

            return days;
        }
    }

    /*判断是否含有周末,如果是周末 返回true,没有返回false*/
    function checkWeekDay(sDate) {
        arys = sDate.split('-');
        arys1 = arys[2].toString().split(' ');
        arys2 = arys1[1].toString().split(':');
        oDate = new Date(arys[0], parseInt(arys[1], 10) - 1, arys1[0], arys2[0], arys2[1], arys2[2]);
        day = oDate.getDay();//判断是否周末
        if (day == 0 || day == 6) {
            return true;
        }
        return false;
    }

    /*增加天数*/
    function addDays(oDate, days) {
        if (days > 0) {
            days = days - 1;
        }
        if (days < 0) {
            days = days + 1;
        }
        var result = new Date(oDate.getTime() + (days * 24 * 60 * 60 * 1000));
        return result;
    }

    /*为一部分月份及日期加前+0*/
    function DateToString(oDate) { //这里又对上下班的时刻做处理了
        var month = oDate.getMonth() + 1;
        var day = oDate.getDate();
        var hour = oDate.getHours();
        var mi = oDate.getMinutes();
        var second = oDate.getSeconds();
        //如果月份小于10月则在前面加0
        if (month < 10) {
            month = "0" + month;
        }
        //如果日期小于10号则在前面加0
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            //如果小于10点 设置为10点
            //9
            hour = 10;//9

            hour = "0" + hour;
        }
        //如果大于19点，让他等于19点
        if (hour > 19) {
            hour = 19;
        }
        if (mi < 10) {
            mi = "0" + mi;
        }
        if (second < 10) {
            second = "0" + second;
        }
        return oDate.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + mi + ":" + second;
    }
    ;

});