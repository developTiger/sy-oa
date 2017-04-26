/**
 * Created by temp on 2016/6/29.
 */
define(function (require, exports, module) {
    var $= require("jquery");
    require("calendar");
    require("moment");
    require("../common/pagelist");
    var check= require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget= require("../common/widget");
    var validate = require("validate");

    check.checkAll("body",".checkAll",".checkBtn")
    

    

    var date =  new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var started;
  //  var categoryClass;

    $(function(){
        //var noTimenoSee = $.fullCalendar.moment("2016-05-01");
        var calendar= $('#calendar').fullCalendar({

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
            //设置是否显示周六和周日，设为false则不显示
            //weekends:false,

            titleFormat:{
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
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
                prev: '上一月',
                next: '下一月'
            },
            //viewRender: function (view) {//动态把数据查出，按照月份动态查询
            //    var s = moment(view.start);
            //    var start = s.format("YYYY/MM/DD HH:mm:ss");
            //    var e = moment(view.end);
            //    var end = e.format("YYYY/MM/DD HH:mm:ss");
            //    $("#calendar").fullCalendar('removeEvents');
            //    $.ajax({
            //        url:"ajax_get_holiday_info?t="+new Date().getMilliseconds(),
            //        type:"post",
            //        data:{time:$("#time").val()},
            //        cache:false,
            //        success:function(data) {
            //            if(data.isSuccess) {
            //                for (var i = 0; i < data.items.length; i++) {
            //                    var obj = new Object();
            //                    obj.id = data.items[i].id;
            //                    obj.title = data.items[i].holidayType;
            //                    obj.description = data.items[i].description;
            //                    //obj.color = data[i].color;
            //                    //obj.remindertime = $.fullCalendar.parseDate(data[i].remindertime);
            //                    //obj.messagenotice = data[i].messagenotice;
            //                    //obj.description = data[i].description;
            //                    obj.start = data.items[i].startTime;
            //                    obj.end = data.items[i].endTime;
            //                    $("#calendar").fullCalendar('renderEvent', obj, true);
            //                }
            //            }
            //
            //        },
            //        error:function() {
            //
            //        }
            //    })
            //     alert("test" +start+"-----"+end);
            //
            //},

            events:
                //页面数据加载
                function(start, end, timezone, callback) {
                    var sTime = start._d;
                    var startTime = dateFormat(sTime,"yyyy-MM-dd hh:mm:ss");
                    var eTime = end._d;
                    var endTime = dateFormat(eTime,"yyyy-MM-dd hh:mm:ss");
                $.ajax({
                    url:"ajax_get_holiday_info?t="+new Date().getMilliseconds(),
                    type:"post",
                    data:{startTime:startTime,endTime:endTime},
                    cache:false,
                    success:function(data) {
                        if(data.isSuccess) {
                            var events = [];
                            for (var i = 0; i < data.items.length; i++) {
                                var obj = new Object();
                                obj.id = data.items[i].id;
                                obj.title = data.items[i].holidayType;
                                obj.description = data.items[i].description;
                                //obj.color = data[i].color;
                                //obj.remindertime = $.fullCalendar.parseDate(data[i].remindertime);
                                //obj.messagenotice = data[i].messagenotice;
                                //obj.description = data[i].description;
                                obj.start = data.items[i].startTime;
                                obj.end = data.items[i].endTime;
                                obj.allDay="allDay",
                                //$("#calendar").fullCalendar('renderEvent', obj, true);
                                events.push(obj);
                            }
                            callback(events);
                        }

                    },
                    error:function() {

                    }
                })
            },




            //events:k,

            //只能单击一个
            //dayClick : function( date ) {
            //    //do something here...
            //    console.log('dayClick触发的时间为：', date.format());
            //    // ...
            //},

            //eventClick : function( event ){
            //    //do something here...
            //    console.log('eventClick中选中Event的id属性值为：', event.id);
            //    console.log('eventClick中选中Event的title属性值为：', event.title);
            //    console.log('eventClick中选中Event的start属性值为：', event.start.format('YYYY-MM-DD HH:mm'));
            //    console.log('eventClick中选中Event的end属性值为：', event.end.format('YYYY-MM-DD HH:mm'));
            //    console.log('eventClick中选中Event的color属性值为：', event.color);
            //    console.log('eventClick中选中Event的className属性值为：', event.className);
            //    // ...
            //},

            /*eventMouseover : function( event ) {
                //do something here...
                console.log('鼠标经过 ...');
                console.log('eventMouseover被执行，选中Event的title属性值为：', event.title);
                // ...
            },

            eventMouseout : function( event ) {
                //do something here...
                console.log('eventMouseout被执行，选中Event的title属性值为：', event.title);
                console.log('鼠标离开 ...');
                // ...
            },*/

            //设置是否可被单击或者拖动选择
            selectable: true,
            //点击或者拖动选择时，是否显示时间范围的提示信息，该属性只在agenda视图里可用
            selectHelper: true,
            //点击或者拖动选中之后，点击日历外的空白区域是否取消选中状态 true为取消 false为不取消，只有重新选择时才会取消
            unselectAuto: true,

            //可以选择多个
            //select: function( start, end ){
            //    //do something here...
            //    console.log('select触发的开始时间为：', start.format());
            //    console.log('select触发的结束时间为：', end.format());
            //    // ...
            //},

            //Event是否可被拖动或者拖拽
            editable: false,
//Event被拖动时的不透明度
            dragOpacity:0.5,

            eventDrop : function( event, dayDelta, revertFunc ) {
                //do something here...
                console.log('eventDrop --- start ---');
                console.log('eventDrop被执行，Event的title属性值为：', event.title);
                if(dayDelta._days != 0){
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._days+'天！');
                }else if(dayDelta._milliseconds != 0){
                    console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._milliseconds/1000+'秒！');
                }else{
                    console.log('eventDrop被执行，Event的start和end时间没有改变！');
                }
                //revertFunc();
                console.log('eventDrop --- end ---');
                // ...
            },





            select: function(start, end, allDay,event,eventObject) {

                $('#fc_create').click();
                started = start;
                ended = end;

                var objectId="";
                $(".antosubmit").off("click");
                $(".antosubmit").on("click", function() {
                    var title = $("#title").val();
                    var desc = $("#description").val();
                    var holidayId = $("#holidayId").val();
                    var startTime = started._d;
                    var endTime = ended._d;
                    $.ajax({
                        url:"ajax_add_holiday_info",
                        type:"post",

                        data:{title:title,description:desc,holidayId:holidayId,startTime:startTime,endTime:endTime},
                        success: function (data) {
                            if(data.isSuccess){
                                if (title) {
                                    calendar.fullCalendar( 'renderEvent', { //渲染一个新的日程到日程表上：
                                            id:data.id,
                                            title: title,
                                            start: started,
                                            allDay: "allDay",
                                            end:ended,
                                            description:desc
                                        }
                                    );
                                }
                                objectId = data.id;
                                //calendar.fullCalendar('gotoDate', started);
                            }
                        }
                    })

                    calendar.fullCalendar('renderEvent',{id:objectId});



                    $("#title").val("");
                    $("#description").val("");
                   // calendar.fullCalendar('unselect');//清除当前的选择
                    $('.antoclose').click();
                    return false;
                });
            },
            //编辑页面
        eventClick: function(calEvent, jsEvent, view) {
            //alert(calEvent.title, jsEvent, view);
            $('#fc_edit').click();
            $("#description2").val(calEvent.description);
            $('#title2').val(calEvent.title);
            $("#editId").val(calEvent.id);
            //categoryClass = $("#event_type").val();
            $(".antosubmit2").off("click");
            $(".antosubmit2").on("click", function() {
                //calEvent.title = $("#title2").val();
                //calendar.fullCalendar('updateEvent', calEvent);
                $.ajax({
                    url:"ajax_update_holiday_info",
                    type:"post",
                    data:$("#antoform2").serializeObject(),
                    success: function (data) {

                        if(data.isSuccess){
                            $(".antoclose2").click();
                            //重新加载refetchEvents
                            calendar.fullCalendar( 'refetchEvents');
                            //calendar.fullCalendar( 'updateEvent', { //渲染一个新的日程到日程表上：
                            //        id:calEvent.id,
                            //        title: $('#title2').val(),
                            //        start: calEvent.start,
                            //        allDay: "allDay",
                            //        end:calEvent.end,
                            //        description:$("#description2").val()
                            //    }
                            //);
                        }
                    }

                });
            });




            //calendar.fullCalendar('unselect');
        }

        //editable: true
    });
        $("body").on("click",".antodelete2", function () {
            var id = $("#editId").val();
            $.ajax({
                url:"ajax_delete_holiday_info",
                type:"post",
                data:{id:id},
                success: function (data) {
                    if(data.isSuccess){
                        $(".antoclose2").click();
                        //重新加载
                        calendar.fullCalendar('removeEvents',id);
                    }
                }
            })
        })

        //var selectdstr ="2016-01-10";
        //var selectdate = new Date();
        var time = $("#time").val();
        var actuallyTime;
        switch(time) {
            case "1": actuallyTime="01-01";
                    break;
            case "2": actuallyTime="02-01";
                break;
            case "3": actuallyTime="03-01";
                break;
            case "4": actuallyTime="04-01";
                break;
            case "5": actuallyTime="05-01";
                break;
            case "6": actuallyTime="06-01";
                break;
            case "7": actuallyTime="07-01";
                break;
            case "8": actuallyTime="08-01";
                break;
            case "9": actuallyTime="09-01";
                break;
            case "10": actuallyTime="10-01";
                break;
            case "11": actuallyTime="11-01";
                break;
            case "12": actuallyTime="12-01";
                break;
        }
        var year = new Date().getFullYear();
        var noTime = $.fullCalendar.moment(year+"-"+actuallyTime);

        console.log(noTime);

         calendar.fullCalendar('gotoDate', noTime);
    })




    //日期格式转换
    function dateFormat (date, format) {
        if(!date)return;
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

    $("#wtestTime").blur(function(){
        getNextTime();
    });

    $("#checkdate").blur(function(){
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


