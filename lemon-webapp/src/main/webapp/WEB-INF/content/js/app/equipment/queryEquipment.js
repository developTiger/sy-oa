/**
 * Created by admin on 2016/8/13.
 */
define(function (require, exports, module) {
    require('../init');
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper2");//使用dateFormat日期格式转换
    var List = require("../common/pagelist");
    var wDatePicker = require("wdatePicker");
    var template = require("template");
    var check = require("../common/checkbox");
    var $body = $("body");
    check.checkAll("body", ".checkAll", ".checkBtn")
    var tpl_equipmentMaintenance = require("text!app/tpl/equipment/queryEquipmentMaintenance.html");
    var validate = require("validate");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    /*$("#equipmentQueryBtn").click(function(){
     loadData();
     });*/

    /*function loadData(){
     var tpl = require("text!app/tpl/equipment/equipmentTpl.html");
     var url=$("#searchForm").attr("data-url");
     var data = $("#searchForm").serialize().toString();
     List("#table",tpl,url,data,1,10);
     }*/

    /*$body.delegate(".queryData","click",function(){
     var url = $(this).attr("data-url");
     if(url==("ajax_query_equipment")){

     var tpl = require("text!app/tpl/equipment/queryEquipmentTpl.html");
     }
     if(url == "ajax_query_equipmentMaintenance"){
     var tpl = require("text!app/tpl/equipment/queryEquipmentMaintenance.html");
     }
     if(url =="ajax_query_equipmentResult"){
     var tpl = require("text!app/tpl/equipment/queryEquipmentResult.html");
     }
     if(url == "ajax_query_equipmentStatus"){
     var tpl = require("text!app/tpl/equipment/queryEquipmentStatus.html");
     }
     List("#table",tpl,url,"",1,10);
     })*/

    function loadPages(target) {
        var _url = target.attr("data-url");
        if (_url == "ajax_query_equipmentMaintenance") {
            var tpl = require("text!app/tpl/equipment/queryEquipmentMaintenance.html");
        }
        if (_url == "ajax_query_technologyJudgementInfo") {
            var tpl = require("text!app/tpl/equipment/queryTechnologyJudgementTpl.html");
        }
        if (_url == "ajax_query_equipmentWorking") {
            var tpl = require("text!app/tpl/equipment/queryEqupmentWorkingRecordTpl.html");
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentWorkingRecordTpl_1.html");
        }
        if (_url == "ajax_query_equipmentHealth") {
            var tpl = require("text!app/tpl/equipment/queryEqupmentHealthRecordTpl.html");
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentHealthRecordTpl_1.html");
        }
        if (_url == "ajax_query_equipmentDateCheck") {
            var tpl = require("text!app/tpl/equipment/queryEqupmentDateCheckTpl.html");
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentDateCheckTpl_1.html");
        }
        var id = $("#hidId").val();
        var beginTime = $("#begTime").val();
        var endTime = $("#enTime").val();

        var url = _url;
        //var data = {id:id,begTime:beginTime,enTime:endTime};
        if (tpl_1 != null) {
            $("#displayData").html("");
            var html = template.compile(tpl)("");
            $("#displayData").html(html);

            List("#table", tpl_1, url, "id=" + id + "&begTime=" + beginTime + "&enTime=" + endTime, 1, 10);

        } else {
            $("#table").html("");
            $("#displayData").html("");
            //var html = tpl;
            List("#table", tpl, url, "id=" + id + "&begTime=" + beginTime + "&enTime=" + endTime, 1, 10);
            //$("#displayData").html(html);
        }
        //控制页面数据的新增功能
        var save = $("#save").val();
        if (save == 1) {
            $("#addEquipmentHealth").removeClass("hidden");
            $("#addEquipmentDateCheck").removeClass("hidden");
            $("#addEquipmentWorking").removeClass("hidden");
        }else{//防止缓存的作用
            $("#addEquipmentHealth").addClass("hidden");
            $("#addEquipmentDateCheck").addClass("hidden");
            $("#addEquipmentWorking").addClass("hidden");
        }
        $(".dataTables_paginate").show();//显示分页样式
    }

    function loadPages_1(target) {

        var _url = target.attr("data-url");
        if (_url == "ajax_query_equipmentWorking") {
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentWorkingRecordTpl_1.html");
        }
        if (_url == "ajax_query_equipmentHealth") {
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentHealthRecordTpl_1.html");
        }
        if (_url == "ajax_query_equipmentDateCheck") {
            var tpl_1 = require("text!app/tpl/equipment/queryEqupmentDateCheckTpl_1.html");
        }
        var id = $("#hidId").val();
        var beginTime = $("#begTime").val();
        var endTime = $("#enTime").val();
        var canSave = $("#save").val();
        var url = _url;
        //var data = {id:id,begTime:beginTime,enTime:endTime};
        List("#table", tpl_1, url, "id=" + id + "&begTime=" + beginTime + "&enTime=" + endTime +"&save=" + canSave, 1, 10);
        $(".dataTables_paginate").show();//显示分页样式
    }

    $body.delegate(".queryData", "click", function () {
        loadPages($(this));
    })

    $body.delegate(".queryData_1", "click", function () {
        loadData($(this));
    })

    function loadData(target) {
        var _url = target.attr("data-url");
        if (_url == "ajax_query_equipment") {
            var tpl = require("text!app/tpl/equipment/queryEquipmentTpl.html");
        }
        var id = $("#hidId").val();
        var beginTime = $("#begTime").val();
        var endTime = $("#enTime").val();
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds() + "&id=" + id + "&begTime=" + beginTime + "&enTime=" + endTime,
            type: "get",
            success: function (data) {
                if (data) {
                    $("#table").html("");
                    $("#displayData").html("");
                    var html = template.compile(tpl)(data);
                    $("#displayData").html(html);
                    $(".dataTables_paginate").hide();//隐藏分页样式
                }
            }
        })
    }

    $("#home-tab").click();


    function checkall(table, btn, callback) {
        this.table = table;
        this.btn = btn;
        this.check = callback;
    }//这是一个构造函数

    checkall.prototype.check = function (callback) {//查找属性，javascript向上遍历原型链，找到返回对象属性，一直到原型链的顶部:object.prototype,没有找到属性就返回undefined
        var that = this;
        $(that.btn).click(function () {
            $(that.table).find("input[type='checkbox']").prop("checked", $(this).prop("checked"));
            callback && typeof callback == "function" && callback();//这一段完全不明白
        })
    }
    module.exports = checkall;


    //var foo = function(a,b){
    //    this.a=a;
    //    this.b=b;
    //}
    //foo.prototype = function(){
    //    add = function(x,y){
    //        return x+y;
    //    }
    //    subtract = function(x,y){
    //        return x-y;
    //    }
    //    return {
    //        add:add,
    //        subtract:subtract
    //    }
    //}
    //var boo = new foo();
    //boo.prototype().add(4,3);
    //


    $("body").on("click", "#add", function () {
        $(".addWorkingRecord").removeClass("hidden");
    })


    $("body").off("click", ".addOrUpdateWorking");
    $("body").on("click", ".addOrUpdateWorking", function () {

        $("#saveWorking").validate({

            rules: {
                count: {
                    required: true,
                    min: 0,
                    digits: true
                },
                workingTime: {
                    required: true,
                    min: 0,
                    number: true
                }
            },
            messages: {
                count: {
                    required: "请输入正整数",
                    min: "请输入正整数",
                    digits: "请输入正整数"
                },
                workingTime: {
                    required: "请输入大于0的数",
                    min: "请输入大于0的数",
                    number: "请输入大于0的数"
                }

            },

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form

                $("#equipmentId").val($("#hidId").val());

                var _url = "ajax_addOrUpdate_working";
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: "post",
                    data: $("#saveWorking").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("保存成功！");
                            $(".addWorkingRecord").removeClass("hidden");
                            loadPages_1($("#searchForm"));

                        } else {
                            alert("保存失败！");
                        }
                    }
                })
            }
        })
    })

    $("body").on("click", ".updateWorkingRecord", function () {
        var id = $(this).next().val();
        $.ajax({
            url: "ajax_update_working_record",
            type: "post",
            data: {id: id},
            success: function (data) {
                if (data) {
                    $("#workContent").val(data.workContent);
                    $("#count").val(data.sampleCount);
                    $("#workTime").val(data.workingTime);
                    $("#checkAndWorkingSituation").val(data.checkAndWorkingSituation);
                    $("#remark").val(data.remark);
                    $("#hidWorkingId").val(data.id)

                    $(".addWorkingRecord").removeClass("hidden");
                }
            }
        })
    })

    $("body").on("click", ".deleteWorking", function () {
        var id = $(this).next().val();
        $.ajax({
            url: "ajax_delete_working_record" + "?t=" + new Date().getMilliseconds() + "&id=" + id,
            type: "get",
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功!");
                    loadPages_1($("#searchForm"));
                }
            }
        })
    })

    $("body").on("click", "#queryAllForm", function () {
        $(".addWorkingRecord").addClass("hidden");
        loadPages_1($("#searchForm"));
    })
    $("body").on("click", "#dateCheck", function () {
        $(".addDateCheck").addClass("hidden");
        loadPages_1($("#searchForm"));
    })


    $("body").on("click", "#addEquipmentHealth", function () {
        $(".addWorkingRecord").removeClass("hidden");
    })
    $("body").on("click", "#addEquipmentDateCheck", function () {
        $(".addDateCheck").removeClass("hidden");
    })


    $("body").off("click", ".addOrUpdateHealth");
    $("body").on("click", ".addOrUpdateHealth", function () {

        $("#saveWorking").validate({

            rules: {
                stopMachineTime: {
                    required: true,
                    min: 0,
                    number: true
                }
            },
            messages: {
                stopMachineTime: {
                    required: "请输入大于0的数",
                    min: "请输入大于0的数",
                    number: "请输入大于0的数"
                }

            },

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form

                $("#equipmentId").val($("#hidId").val());
                var _url = "ajax_addOrUpdate_equipmentHealth";
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: "post",
                    data: $("#saveWorking").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("保存成功！");
                            $(".addWorkingRecord").removeClass("hidden");
                            loadPages_1($("#searchForm"));

                        } else {
                            alert("保存失败！");
                        }
                    }
                })
            }
        })
    })

    $("body").on("click", ".updateHealthRecord", function () {
        var id = $(this).next().val();
        $.ajax({
            url: "ajax_update_working_health",
            type: "post",
            data: {id: id},
            success: function (data) {
                if (data) {
                    $("#position").val(data.position);
                    $("#changeSituation").val(data.changeSituation);
                    $("#stopMachineTime").val(data.stopMachineTime);
                    $("#result").val(data.result);
                    $("#question").val(data.question);
                    $("#hidWorkingId").val(data.id)

                    $(".addWorkingRecord").removeClass("hidden");
                }
            }
        })
    })

    $("body").on("click", ".deleteHealth", function () {
        var id = $(this).next().val();
        $.ajax({
            url: "ajax_delete_health_record" + "?t=" + new Date().getMilliseconds() + "&id=" + id,
            type: "get",
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功!");
                    loadPages_1($("#searchForm"));
                }
            }
        })
    });


    $("body").on("click", ".cancelBtn", function () {
        $(".addWorkingRecord").addClass("hidden");
    })


    //js 时间转换
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };


    $("body").off("click", ".addOrUpdateDateCheck");
    $("body").on("click", ".addOrUpdateDateCheck", function () {
        $("#saveDateCheck").validate({
            rules: {
                days: {
                    required: true,
                    min: 0,
                    digits: true
                },
                checkWay: {
                    required: true
                },
                checkPerson: {
                    required: true
                },
                scheckTime: {
                    required: true
                },
                headPerson: {
                    required: true
                },
                sheadTime: {
                    required: true
                },
                content: {
                    required: true
                },
                checkDate: {
                    required: true
                },
                date: {
                    required: true
                },
                headContent: {
                    required: true
                }

            },
            messages: {
                days: {
                    required: "必填",
                    min: "请输入正整数",
                    digits: "请输入正整数"
                },
                checkWay: {
                    required: "必填"
                },
                checkPerson: {
                    required: "必填"
                },
                scheckTime: {
                    required: "必填"
                },
                headPerson: {
                    required: "必填"
                },
                sheadTime: {
                    required: "必填"
                },
                content: {
                    required: "必填"
                },
                checkDate: {
                    required: "必填"
                },
                date: {
                    required: "必填"
                },
                headContent: {
                    required: "必填"
                }

            },

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form

                $("#equipmentId").val($("#hidId").val());

                var _url = "ajax_addOrUpdate_dateCheck";
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: "post",
                    data: $("#saveDateCheck").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("保存成功！");
                            $(".addDateCheck").addClass("hidden");
                            loadPages_1($("#searchForm"));

                        } else {
                            alert("保存失败！");
                        }
                    }
                })
            }
        })
    });
    $("body").on("click", ".updateDateCheck", function () {
        var id = $(this).next().val();
        $.ajax({
            url: "ajax_update_date_check",
            type: "post",
            data: {id: id},
            success: function (data) {
                if (data) {
                    $("#days").val(data.days);
                    $("#checkWay").val(data.checkWay);
                    $("#headPerson").val(data.headPerson);
                    $("#sheadTime").val(data.sheadTime);
                    $("#content").val(data.content);
                    $("#checkDate").val(data.checkDate);
                    $("#date").val(data.date);
                    $("#headContent").val(data.headContent);
                    $("#scheckTime").val(data.scheckTime)
                    $("#checkPerson").val(data.checkPerson)
                    $("#hiddenDateCheckId").val(data.id)

                    $(".addDateCheck").removeClass("hidden");
                }
            }
        })
    })
})
