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
    require("select2");
    require("ajaxUpload");


    loadData();
    $("#failAllQueryBtn").click(function () {
        loadData();
    })
    function loadData() {
        var url = "ajax_kg03_lotTpl";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc03_lotTpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

    $('.leaders,.leaderss').select2({
        placeholder: "请选择",
        width: '100%'
    });
//群审批否决上一级
    $("body").delegate("#lotReject", "click", function () {
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
        var projectNo=$("#projectNo").val();
        var clstep=$(".clStep").val();
        ajax_lotProjectNO(chk_value.toString(), sid, allContent,projectNo,clstep);
    })

    function ajax_lotProjectNO(selectids, sid, allContent,projectNo,clstep) {
        var options = {
            url: "syy_lotProjectNO?t=" + new Date().getMilliseconds() + "&ids=" + selectids + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent))
                + "&projectNo="+projectNo + "&clstep="+clstep,
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("否决成功");
                    window.location.reload();
                } else {
                    alert("否决失败");
                }
                ajaxnext();
            }
        };
        $.ajax(options);
    }

//群审批同意
    $("body").delegate("#lotApprove", "click", function () {
       //勾选项目
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        //签核意见
        var allContent = $("#allContent").val();
        //批示
        var clstep=$(".clStep").val();
        if(clstep==2){
            var instructions=$("#instructions").val();
            /*if (instructions==""){
                alert("请填写批示");
                return false;
            }*/
        }
        //专家
        if(clstep==4) {
            var zjid = $("#hidid").val();
            if (zjid.length <= 0 || zjid == null) {
                alert("请选择专家");
                return false;
            }
        }
        //专家意见
        if(clstep==6) {
            var proficientOpinion = $("#proficientOpinion").val();
            if (proficientOpinion.length <= 0 || proficientOpinion == null) {
                alert("请填写专家意见");
                return false;
            }
        }
        if(clstep==7) {
            var zgid = $("#hidids").val();
            if (zgid.length <= 0 || zgid == null) {
                alert("请选择主管领导");
                return false;
            }
        }
        var projectNo=$("#projectNo").val();
        //if(clstep<=10){
            ajax_lotProjectYES(chk_value.toString(),allContent,instructions,zjid,zgid,clstep,projectNo,proficientOpinion);
        //}
        //if(clstep==11){
        //
        //}
    })

    function ajax_lotProjectYES(prono,allContent,instructions,zjid,zgid,clstep,projectNo,proficientOpinion) {
        var options = {
            url: "syy3_lotProjectYES?t=" + new Date().getMilliseconds() + "&ids=" + prono + "&allContent=" + encodeURI(encodeURI(allContent))
                  + "&instructions=" + encodeURI(encodeURI(instructions))+ "&zjid="+ zjid + "&zgid="+zgid + "&clStep=" + clstep +"&projectNo=" + projectNo
                  +"&proficientOpinion="+encodeURI(encodeURI(proficientOpinion)),
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("签核成功");
                    window.location.reload();
                } else {
                    alert("签核失败");
                }
                ajaxnext();
            }
        };
        $.ajax(options);
    }
//刷新审核列表
    function ajaxnext(){
        if($(window.opener.document).find(".pagination li.active a")[0])
            $(window.opener.document).find(".pagination li.active a")[0].click();
        else
            $(window.opener.document).find("#secondForm")[0].click();
    }


    //多选获取fenguan model
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

    $("body").delegate(".leaderss", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaderss option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidids").val(selectId);
        var sid = $("#hidnames").val(selectName);

    })
    $("body").delegate("#upload", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        //var formNo = $("#formNo").val();
        //var content=$("#content").val();
        var fileIds = [];
        $("input[name='fileName']").each(function(){
            fileIds.push($(this).attr("id"));
        });
        $.ajaxFileUpload({
            url: 'kg_lc03_executionUpload_lot?t='+ new Date().getMilliseconds()+"&ids="+chk_value.toString(),
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: fileIds,           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    })

})
