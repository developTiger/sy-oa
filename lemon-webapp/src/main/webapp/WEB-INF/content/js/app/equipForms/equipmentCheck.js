/**
 * Created by admin on 2016/8/15.
 */
define(function (require, exports, module) {

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("jquery")
    require("select2");
    require("../common/jquery.serializeObject");
    var tpl_repair = require("text!app/tpl/equipForms/syy_ay_lc02_a_repairTpl.html");
    var tpl_maintain = require("text!app/tpl/equipForms/syy_ay_lc02_a_maintainTpl.html");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
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
            var tpl = require("text!app/tpl/equipForms/syy_ay_lc03_v_Tpl.html");
            form.render(target, tpl);
        }
    }
    exports.pageLoad = function () {
        loadForm();
    }
    $body.delegate("#equipmentName", "change", function () {
        var id = $(this).find("option:selected").val();
        $.ajax({
            url: "ajax_query_equipmentData" + "?t=" + new Date().getMilliseconds() + "&id=" + id,
            type: "get",
            success: function (data) {
                if (data) {
                    //alert("设备信息获取成功！");
                    $("#unit").val(data.unitName);
                    $("#standard").val(data.standard);
                    //$("#num").val(data.num);

                }
            }
        })
    })


    $("#appform").validate({

        rules: {
            num: {
                digits: true
            }
        },
        messages: {
            num: {
                digits: "请输入大于1的整数！"
            }
        },

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#equipmentCheckSubmit").attr("data-url");
            $("#hidName").val($("#equipmentName").find("option:selected").text());
            var tests = $("#testers").val().join(",");
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds() + "&testers=" + encodeURI(encodeURI(tests)),
                type: "post",
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("提交成功！");
                        window.close();
                    } else
                        alert(data.msg);
                }
            })
        }
    })
    var mutiselect = $(".select2").select2({
        placeholder: "请选择人员",
        allowClear: true,
        multiple: true,
        tags:true
    });

    if ($("#equipmentName").find("option:selected").text() != "请选择") {
        $("#equipmentName").trigger("change");
    }


})