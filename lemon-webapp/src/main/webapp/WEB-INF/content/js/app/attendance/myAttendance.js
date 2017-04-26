/**
 * Created by xiazl on 2017/1/17.
 */
define(function (require, exports, module) {
    //require('../init');
    require("calendar");
    require("moment");

    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var validate = require("validate");
    //require("jquery")




    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var started;
    $(function () {
        //var noTime = $.fullCalendar.moment("2016-05-01");
        var calendar = $('#calendar').fullCalendar({
            theme: true,
            header: {
                left: 'title',
                center: '',
                right: 'today prev,next'
            },
            //设置是否显示周六和周日，设为false则不显示
            weekends: true,
            weekNumbersWithinDays:true,
                titleFormat: {
                month: 'YYYY MMMM'
            },
            //fullcalendar本地化
            //timeFormat:{agenda: 'h:mm TT{ - h:mm TT}'}, //默认是{agenda: ‘h:mm{ - h:mm}}, 影响的是添加的具体的日程上显示的时间格式.
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            today: ["今天"],
            firstDay: 1,
            buttonText: {
                today: '当前月',
                prev: '上一月',
                next: '下一月'
            },
            events: //页面数据加载
                function (start, end, timezone, callback) {
                    var sTime = start._d;
                    var startTime = dateFormat(sTime, "yyyy-MM-dd hh:mm:ss");
                    var eTime = end._d;
                    var endTime = dateFormat(eTime, "yyyy-MM-dd hh:mm:ss");
                    $.ajax({
                        url: "ajax_get_myAttendance?t=" + new Date().getMilliseconds(),
                        type: "post",
                        data: {startTime: startTime, endTime: endTime},
                        cache: false,
                        success: function (result) {
                            if (result.length>0) {
                                var events = [];
                                for (var i = 0; i < result.length; i++) {
                                    var obj = new Object();
                                    obj.title = result[i].descript;
                                    obj.start =result[i].time;
                                    obj.allDay = "allDay";
                                        events.push(obj);
                                }
                                callback(events);
                            }
                        },
                        error: function () {
                            alert("数据异常!")
                        }
                    })
                },

            //设置是否可被单击或者拖动选择
            selectable: true,
            //点击或者拖动选择时，是否显示时间范围的提示信息，该属性只在agenda视图里可用
            selectHelper: true,
            //点击或者拖动选中之后，点击日历外的空白区域是否取消选中状态 true为取消 false为不取消，只有重新选择时才会取消
            unselectAuto: true,

            //Event是否可被拖动或者拖拽
            editable: true,
//Event被拖动时的不透明度
            dragOpacity: 0.5,

            eventDrop: function (event, dayDelta, revertFunc) {
                //do something here...
                console.log('eventDrop --- start ---');
                console.log('eventDrop被执行，Event的title属性值为：', event.title);
                if (dayDelta._days != 0) {
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
                } else if (dayDelta._milliseconds != 0) {
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
                } else {
                    console.log('eventDrop被执行，Event的start和end时间没有改变！');
                }
                console.log('eventDrop --- end ---');
            }
        });

        var time = $("#time").val();
        var actuallyTime;
        switch (time) {
            case "1":
                actuallyTime = "01-01";
                break;
            case "2":
                actuallyTime = "02-01";
                break;
            case "3":
                actuallyTime = "03-01";
                break;
            case "4":
                actuallyTime = "04-01";
                break;
            case "5":
                actuallyTime = "05-01";
                break;
            case "6":
                actuallyTime = "06-01";
                break;
            case "7":
                actuallyTime = "08-01";
                break;
            case "9":
                actuallyTime = "09-01";
                break;
            case "10":
                actuallyTime = "10-01";
                break;
            case "11":
                actuallyTime = "11-01";
                break;
            case "12":
                actuallyTime = "12-01";
                break;
        }
        var year = new Date().getFullYear();
        var noTime = $.fullCalendar.moment(year + "-" + actuallyTime);
        calendar.fullCalendar('gotoDate', noTime);
    });


    //日期格式转换
    function dateFormat(date, format) {
        if (!date)return;
        if (!format) {
            format = "yyyy-MM-dd hh:mm:ss";
        }
        date = new Date(date);
        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };

        format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            }
            else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    }

    $("#wtestTime").blur(function () {
        getNextTime();
    });

    $("#checkdate").blur(function () {
        getNextTime();
    });

    function getNextTime() {
        var checkdate = $("#checkdate").val();
        var testTime = $("#wtestTime").val();
        if (testTime != "" && testTime != null && testTime != undefined && checkdate != "" && checkdate != null && checkdate != undefined) {
            var date = new Date(String(testTime));
            date.setDate(date.getDate() + Number(checkdate));
            $("#wnextTestTime").val(dateFormat(date, "yyyy-MM-dd"));
        }
    }


});


