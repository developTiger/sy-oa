/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("jquery");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    //var tpl = require("text!app/tpl/forms/syy_rs_lc05_vTpl.html");
    require("../common/templeteHelper");
    //require("printArea");

    var validate = require("validate");


    $("body").delegate(".printBusiness","click",function(){
        var formKind=$(this).attr("data-num");
        var url=$(this).attr("data-url");
        var dowLoadUrl=$(this).attr("data-url-dowLoad");
        window.location.href="/"+dowLoadUrl+"?formNo="+formKind;
        //$("#divPrint").printArea(formKind,url);
    });

    $("body").delegate(".Wdate1", "click", function () {

        wDatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'strToTime\')}'});
    });
    $("body").delegate(".Wdate2", "click", function () {

        wDatePicker({dateFmt: 'yyyy-MM-dd',minDate:'#F{$dp.$D(\'strFromTime\')}'});
    });

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如

    form.init(function () {
        form.doSubmit(); //此处自定义验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_business_v").length>0) {
            target=$("#syy_business_v");
            var tpl = require("text!app/tpl/forms/syy_rs_lc05_vTpl.html");
            form.render(target,tpl);
        }
    }


    exports.pageLoad=function(){
        loadForm();
    };

/*    if ($("#syy_leave_v")) {
        var formNo = $("#syy_leave_v").attr("form-no");
        //var formKind = $("#syy_leave_v").attr("form-Kind");
        var _url = $("#syy_leave_v").attr("data-url");
        $.ajax({
            url: _url + "?formNo=" + formNo + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            success: function (data) {
                if (data) {
                    var html = template.compile(tpl)(data);
                    $("#syy_leave_v").html(html);
                }
            }
        });
    }*/
    /*var vaLi = function validates() {
        $('#menu-name').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '264px'
        });*/

        $("#appform").validate({
            rules: {
                reason: {
                    required: true
                },
                target: {
                    required: true
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
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url =$("#businessSubmit").attr("data-url");
                var d1 = $("#strFromTime").val();//开始时间
                var d2 = $("#strToTime").val();//结束时间
                var timestamp1 = Date.parse(new Date(d1));
                var timestamp2 = Date.parse(new Date(d2));
                if (timestamp1 > timestamp2) {
                    alert("开始时间不能小于结束时间！");
                    return;
                }
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#appform").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess){
                            alert("新增成功");
                            window.close();
                        }                 else
                            alert(data.msg);
                    }
                });
            }
        });


    $("body").delegate(".js_download","click",function(){
        var formNo=$("#syy_business_v").attr("form-no");
        $.ajax({
            url: "ajax_download_business" + "?formNo=" + formNo + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            success: function (data) {
                if(data.isSuccess){
                    alert("操作成功!");
                }
            }
        });
    });

    $("body").delegate("#strToTime", "blur", function () {

        var d1 = $("#strFromTime").val();//开始时间
        var d2 = $("#strToTime").val();//结束时间
        $("#countTime").val( DateDiffNoWeekDay(d1, d2));

    });

    function DateDiffNoWeekDay(sDate1, sDate2) {

        //日期格式为yyyy-mm-dd
        var oDate1 = StringToDate(sDate1);
        var oDate2 = StringToDate(sDate2);
        /*alert('取整-->'+parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24));
         alert('非取整-->'+(oDate2 - oDate1) / 1000 / 60 / 60 /24);*/
        if (parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) < (oDate2 - oDate1) / 1000 / 60 / 60 / 24) {
            var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) + 1;
        }
        else {
            days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24);
        }

        //var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24);//获取总的天数
        var days1 = parseInt((oDate2 - oDate1) / 1000 / 60 / 60) % 8;  //获取余下的小时
        /*--减去不用上班的时间,即10-19之外的时间--*/
        if (days1 > 8) {
            days1 = 8;
        }
        var tempDate = oDate1;
        while (tempDate.getTime() <= oDate2.getTime()) {
            //tempDate = addDays(tempDate,2);//加一天
            //days>0表示超过1天，防止出现负数days
            if (checkWeekDay(DateToString(tempDate)) & days > 0) {
                //如果是周末,天数减1
                days--;
            }
            tempDate = addDays(tempDate, 2);//加一天
        }
        if (days == 1) {
            if (parseInt((oDate2 - oDate1) / 1000 / 60 / 60 / 24) == 0 & days1 == 0) {
                days = 1;
            }
            else {
                days = 0;
            }
            /*一天或半天加判断解决天数的问题*/
        }

        var result = days;   //+ "天" + days1 + "小时";
        return result;
    }

    /*判断是否含有周末,如果是周末 返回true,没有返回false*/
    function checkWeekDay(sDate) {
        arys = sDate.split('-');
        arys1 = arys[2].split(' ');
        arys2 = arys1[1].split(':');
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

    /*将字符串转换成日期*/
    function StringToDate(sDate) {
        arys = sDate.split('-');
        arys1 = arys[2].split(' ');
        arys2 = arys1[1].split(':');
        if (arys2[0] > 19) {
            arys2[0] = 19;
            arys2[1] = 00;
            arys2[2] = 00;
        }
        if (arys2[0] < 10) {//9
            arys2[0] = 10;//9
            arys2[1] = 0;
            arys2[2] = 0;
        }
        var newDate = new Date(arys[0], parseInt(arys[1], 10) - 1, arys1[0], arys2[0], arys2[1], arys2[2]);
        return newDate;
    }

    /*为一部分月份及日期加前+0*/
    function DateToString(oDate) {
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
            if (hour < 10) { //9
                hour = 10;//9
            }
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


});