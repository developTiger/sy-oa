 define(function (require, exports, model) {
    require("template");
    template.helper('dateFormat', function (date, format) {
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
    });





    var data = {
        time: (new Date).toString()
    };

    template.helper('enumsSelect', function (data) {
        //alert(data);
        if(data==null ||data=="")return;
        var map = {
            0:"出勤",
            1:"公休加班",
            2:"节日加班",
            3:"野外",
            4:"出差",
            5:"学历学习",
            6:"脱产学习",
            7:"带薪休假",
            8:"疗养",
            9:"探亲假",
            10:"婚丧假",
            11:"工伤假",
            12:"产假",
            13:"产后休长假",
            14:"护理假，陪护假，计划生育假",
            15:"病假",
            16:"事假",
            17:"旷工",
            18:"拘留",
            19:"戒毒",
            20:"劳保",
            21:"补休"
        };
        return map[data];
    });


    // exports.template=template;


});

