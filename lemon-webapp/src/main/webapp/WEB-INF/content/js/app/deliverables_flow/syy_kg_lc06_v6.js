/**
 * Created by swb on 2016/8/30.
 */
define(function (require, exports, module) {
    var List;
    List = require("../common/pagelist");
    require("jquery");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    require("select2");
    require("../init");
    $(".select_like").select2({});

    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd HH:mm:ss',
            readOnly:true
        });
    });
    var form = require("../common/form");
    //群审批同意

    $("body").delegate("#lotApprove", "click", function () {
        //var chk_value = [];
        //chk_value.push($('#deliverId').val());
        ////if(chk_value.toString().length <= 0||chk_value.toString()==null){
        ////    alert("请勾选项目");
        ////    return false;
        ////}
        //var allContent=$("#allContent").val();
        //var instructions=$("#instructions").val();
        //ajax_lotProjectYES(chk_value.toString(),allContent,instructions);
    })
    $("body").delegate("#reject", "click", function () {
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#content").val();
        var instructions=$("#instructions").val();
        var zgld = $("#zgld").val();
        var _url = "syy_kg_lc06_approve6";
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds() + "&prono=" + chk_value.toString() + "&empids=" +zgld+"&allContent=" +encodeURI(encodeURI(allContent))+"&no=no",
            type: 'get',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if (data=="success") {
                    alert("操作成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                }
            }
        });
    });
    function ajax_lotProjectYES(prono,allContent,sid,rank,grade,issuing_unit) {
        var options = {
            url: "syy_kg_lc06_approve6?t=" + new Date().getMilliseconds() + "&prono=" + prono + "&empids=" +sid+"&allContent=" +encodeURI(encodeURI(allContent))+"&rank="+rank+"&grade="+grade+"&issuing_unit="+encodeURI(encodeURI(issuing_unit)),
            type: 'get',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                }else{
                    alert("签核失败");
                }
            }
        };
        $.ajax(options);
    };
    $(".select2_multiple").select2({
    });
    $("body").delegate(".leaders","change",function() {
        $(".leaders").children("option").each(function(){
            if($(this).prop('selected')){
                var groupMemberList=$(".leaders").val();
                $("#zgld").val(groupMemberList);
            }
        })
    });
    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {
                var chk_value = [];
                chk_value.push($('#deliverId').val());
                var allContent=$("#content").val();
                var instructions=$("#instructions").val();
                var zgld = $("#zgld").val();
                var issuing_unit=$("#issuing_unit").val();
                var rank=$("#rank").val();
                var grade=$("#grade").val();
                ajax_lotProjectYES(chk_value.toString(),allContent,zgld,rank,grade,issuing_unit);
            }
        });
    });

    //单个审回上一级
    $("body").delegate("#goBack", "click", function () {
        var prono=$("#deliverId").val();
        var allContent=$("#content").val();
        ajax_lotProjectNOONE(prono,allContent);
    });

    function ajax_lotProjectNOONE(selectids,allContent) {
        var options = {
            url: "ajax_deliverNOONE5?t=" + new Date().getMilliseconds() + "&prono=" + selectids  +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("打回成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                }else{
                    alert("打回失败");
                }
            }
        };
        $.ajax(options);
    }

});
