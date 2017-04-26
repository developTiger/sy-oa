/**
 * Created by swb on 2016/12/28.
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
    require("select2");

    loadData();
    $("#failAllQueryBtn0").click(function () {
        loadData();
    })
    function loadData() {
        var url = "ajax_kg05_lotTpl1";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc05_kgTpl1.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

    $('.leaders').select2({
        placeholder: "请选择",
        width: '100%'
    });

    /*$('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });*/

//群审批打回上一级
    $("body").delegate("#lotReject0", "click", function () {
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
            url: "ajax_approval_reject1?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
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
    $("body").delegate("#lotApprove0", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });

        var sid = $("#hidid").val();
        if(sid==""||sid==null){
            alert("请选择审核专家");
            return false;
        }
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        var allContent = $("#allContent").val();
        ajax_lotProjectYES(chk_value.toString(), sid, allContent);
    })

    function ajax_lotProjectYES(prono, sid, allContent) {
        var options = {
            url: "ajax_approval_approve1?t=" + new Date().getMilliseconds() + "&ids=" + prono + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
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

    //单个审回上一级
    $("body").delegate(".projectnoOne", "click", function () {

        var sid = $("#hidid").val();
        /* if(sid.length <= 0||sid==null){
         alert("请选择分管领导");
         return false;
         }*/
        var prono = $(".projectnoOne").attr("data-value");
        var allContent = $("#allContent").val();
        ajax_lotProjectNOONE(prono, sid, allContent);
    })

    function ajax_lotProjectNOONE(prono, sid, allContent) {
        var options = {
            url: "ajax_kygl_approve_pro?t=" + new Date().getMilliseconds() + "&forno=" + prono + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
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

    //单个审批同意
    $("body").delegate(".projectokOne", "click", function () {
        var prono = $(".projectokOne").attr("data-value")
        var sid = $("#hidid").val();
        if (sid.length <= 0 || sid == null) {
            alert("请选择专家");
            return false;
        }
        var allContent = $("#allContent").val();
        ajax_lotProjectYESONE(prono, sid, allContent);
    })

    function ajax_lotProjectYESONE(prono, sid, allContent) {
        var options = {
            url: "ajax_kygl_approve_proyes?t=" + new Date().getMilliseconds() + "&prono=" + prono + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
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

    //多选获取fenguan
    $("body").delegate(".leaders", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidid").val(selectId);
        var sid = $("#hidname").val(selectName);

    })



})