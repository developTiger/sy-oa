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

    loadData();
    $("#failAllQueryBtn0").click(function () {
        loadData();
    })
    function loadData() {
        var url = "ajax_kg05_lotTplzj";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc05_kgTplzj.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

    $('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });

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
            url: "ajax_approval_reject_zj?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
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
        var proficientOpinion = $("#proficientOpinion").val();
        if (proficientOpinion.length <= 0 || proficientOpinion == null) {
            alert("请填写专家意见");
            return false;
        }
        var sid = $("#hidid").val();
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        var allContent = $("#allContent100").val();
        var up_role=$("#up_role").val();
        var up_formNo=$("#up_formNo").val();
        ajax_lotProjectYES(chk_value.toString(), sid, allContent,up_role,up_formNo,proficientOpinion);
    })

    function ajax_lotProjectYES(prono, sid, allContent,up_role,up_formNo,proficientOpinion) {
        var options = {
            url: "ajax_approval_approvezj?t=" + new Date().getMilliseconds() + "&ids=" + prono + "&empids=" + sid + "&allContent100=" + encodeURI(encodeURI(allContent))+"&up_role="+encodeURI(encodeURI(up_role))+"&up_formNo="+up_formNo+"&proficientOpinion="+encodeURI(encodeURI(proficientOpinion)),
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