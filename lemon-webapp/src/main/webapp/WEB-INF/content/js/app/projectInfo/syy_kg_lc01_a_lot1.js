/**
 * Created by user on 2016/8/5.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");
    check.checkAll("body", ".checkAll", ".checkBtn");
    require("mutiSelect");

    loadData();
    $("#failAllQueryBtn0").click(function () {
        loadData();
    })
    function loadData() {
        var url = "ajax_kg01_lotTpl1";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc01_lotTpl1.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

    $('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });

//群审批否决上一级
    $("body").delegate("#lotReject1", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }

        var sid = $("#hidid").val();
        var allContent = $("#allContent").val();
        ajax_lotProjectNO(chk_value.toString(), sid, allContent);
    })

    function ajax_lotProjectNO(selectids, sid, allContent) {
        var options = {
            url: "ajax_lotProjectNO_1?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("打回成功");
                    window.location.reload();
                } else {
                    alert("打回失败");
                }
                if($(window.opener.document).find(".pagination li.active a")[0])
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                else
                    $(window.opener.document).find("#secondForm")[0].click();
            }
        };
        $.ajax(options);
    }

//群审批同意
    $("body").delegate("#lotApprove1", "click", function () {
      //获取formno
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
      //获取项目id
        var chk_id = [];
        $('.checkBtn:checked').each(function () {
            chk_id.push($(this));
        });

        var instructions=$("#instructions").val();
        if (instructions==""){
            alert("请填写批示");
            return false;
        }

        var sid = $("#hidid").val();
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        var allContent = $("#allContent").val();
        ajax_lotProjectYES(chk_value.toString(), sid, allContent,instructions);
    })

    function ajax_lotProjectYES(prono, sid, allContent,instructions) {
        var options = {
            url: "ajax_lotProjectYES_1?t=" + new Date().getMilliseconds() + "&ids=" + prono + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent))+"&instructions="+ encodeURI(encodeURI(instructions)),
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("签核成功");
                    window.location.reload();
                } else {
                    alert("签核失败");
                }
                if($(window.opener.document).find(".pagination li.active a")[0])
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                else
                    $(window.opener.document).find("#secondForm")[0].click();
            }
        };
        $.ajax(options);
    }

})
