/**
 * Created by admin on 2016/8/15.
 */
define(function (require, exports, module) {

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper2");

    var tpl_addContent = require("text!app/tpl/equipForms/syy_ay_lc05_v_addContent_Tpl.html");
    var check = require("../common/checkbox");
    var List = require("../common/pagelist");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    //checkbox样式
    check.niceCheck("input");
    //多选框 全选
    //check.checkAll("body", ".checkAll", ".checkBtn");

    $body.delegate("#measuringName", "change", function () {
        $("#hidName").val($(this).find("option:selected").text());
        if ($(this).find("option:selected").text() == "请选择") {
            $("#propertyNum").val('');
            $("#standard").val('');
            $("#factoryName").val('');
            $("#outFactoryNum").val('');
            $("#savePersonName").val('');
            $("#unitName").val('');
            $("#systemNumber").val('');
            $("#buyTime").val('');
            $("#productTime").val('');
            $("#original").val('');
            $("#netValue").val('');
            $("#checkdate").val('');
            $("#minValue").val('');
            $("#maxValue").val('');
            $("#currentStation").val('');
            $("#nextTestTime").val('');
        }
        var id = $(this).find("option:selected").val();
        $.ajax({
            url: "ajax_query_technologyJudgement" + "?t=" + new Date().getMilliseconds() + "&id=" + id,
            type: "get",
            success: function (data) {
                if (data) {
                    //alert("获取数据成功");
                    $("#propertyNum").val(data.propertyNum);
                    //$("#measuringName").val(data.measuringName);
                    $("#standard").val(data.standard);
                    $("#factoryName").val(data.factoryName);
                    $("#outFactoryNum").val(data.outFactoryNum);
                    $("#savePersonName").val(data.savePersonName);
                    $("#unitName").val(data.unitName);
                    $("#systemNumber").val(data.systemNumber);
                    $("#buyTime").val(formatDate(data.buyTime));
                    $("#productTime").val(formatDate(data.productTime));
                    $("#original").val(data.original);
                    $("#netValue").val(data.netValue);
                    $("#checkdate").val(data.checkdate);
                    $("#minValue").val(data.minValue);
                    $("#maxValue").val(data.maxValue);
                    if (data.currentStation == "Normal")
                        $("#currentStation").val("正常");
                    else if (data.currentStation == "applied")
                        $("#currentStation").val("已申请维修");
                    else
                        $("#currentStation").val("维修中");
                    $("#nextTestTime").val(formatDate(data.nextTestTime));

                }
            }
        })
    })

    function loadData() {
        var url = "ajax_query_addJudgementCon_info";
        var formNo = $("#hidFormNo").val();
        if (!formNo)
            formNo = "";
        var tpl_judgementContent = require("text!app/tpl/equipForms/syy_ay_lc05_v_judgementContent_Tpl.html");
        List("#judgementContent", tpl_judgementContent, url, "formNo=" + formNo, 1, 10);//
    }

    $("document").ready(function () {
        loadData();

        if ($("#measuringName").find("option:selected").text() != "请选择") {
            $("#measuringName").trigger("change");
        }
    })

    //window.onload = loadData();


    //设备技术评估内容新增 点击事件
    $body.delegate("#addOrUpdateJudgementContent", "click");
    $body.delegate("#addOrUpdateJudgementContent", "click", function () {
        var id = $("#hidId").val();
        if ($("#measuringName").find("option:selected").val() == "") {
            alert("请先选择设备！");
            return false;
        }
        if ($("#parameterName").val().trim() == "") {
            alert("请填写项目/参数名称");
            return false
        }
        if ($("#parameterRange").val().trim() == "") {
            alert("请填写技术参数标准值或参数范围");
            return false
        }
        if ($("#testValue").val().trim() == "") {
            alert("请填写实际试验值/技术状况");
            return false
        }
        if ($("#conform").val().trim() == "") {
            alert("请填写符合程度");
            return false
        }
        if ($("#testCrew").val().trim() == "") {
            alert("请填写评估或试验人员");
            return false
        }
        if ($("#implement").val().trim() == "") {
            alert("请填写验证实施");
            return false
        }
        if ($("#suggest").val().trim() == "") {
            alert("请填写建议或措施");
            return false
        }
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds(),
            type: "post",
            async: false,//同步传输，默认异步
            data: $("#appform").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    $("#hidFormNo").val(data.id);
                    if (id)
                        alert("修改评估内容成功！");
                    else
                        alert("新增评估内容成功！");
                    loadData();
                    $("#addJudCon").html("");
                }
            }
        })
    })

    //设备技术评估内容 修改和新增的点击事件
    $body.delegate(".editJudgementContent", "click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: "get",
            success: function (data) {
                if (data) {
                    $("#addJudCon").html("");
                    //alert("获取数据成功！");
                    //window.close();
                    var html = template.compile(tpl_addContent)(data);
                    $("#addJudCon").html(html);
                } else {
                    $("#addJudCon").html("");
                    var html = template.compile(tpl_addContent)(data);
                    $("#addJudCon").html(html);
                }
            }
        })
    })

    //设备技术评估内容 删除
    $body.delegate(".deleteJudgementContent", "click", function () {
        var _url = $(this).attr("data-url");
        var formNo = $("#hidFormNo").val();
        $.ajax({
            url: _url + "&formNo=" + formNo,
            type: "get",
            success: function (data) {
                if (data)
                    alert("删除成功！");
                loadData();
            }
        })
    })


    //设备评估单 表单提交
    $("#appform").validate({

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form

            var _url = $("#technologyJudgementSubmit").attr("data-url");
            if ($("input[name='assessType']:checked").val() == undefined) {
                alert("请选择评估类型");
                return;
            }
            if ($("td[class='num']").val() == undefined) {
                alert("请增加评估内容");
                return;
            }

            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: "post",
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("新增成功！");
                        //history.go(0);
                        window.close();
                    } else
                        alert(data.msg);
                }
            })
        }
    });

    var form = require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm = function () {
        var target;
        if ($("#syy_technologyJudgement_v").length > 0) {
            target = $("#syy_technologyJudgement_v");
            var tpl = require("text!app/tpl/equipForms/syy_ay_lc05_v_Tpl.html");
            form.render(target, tpl);
        }
    }

    exports.pageLoad = function () {
        loadForm();
    };


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


})