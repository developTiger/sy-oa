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
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#allContent").val();
        //var clstep=$("#datastep").val();
        ajax_lotProjectYES(chk_value.toString(),allContent);
    })
    $("body").delegate("#reject", "click", function () {
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#content").val();
        //var clstep=$("#datastep").val();
        var _url = "syy_kg_lc06_approve7";
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds() + "&ids=" + chk_value.toString() +"&allContent=" +encodeURI(encodeURI(allContent))+"&no=no" ,
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
    function ajax_lotProjectYES(prono,allContent) {
        var options = {
            url: "syy_kg_lc06_approve7?t=" + new Date().getMilliseconds() + "&ids=" + prono +"&allContent=" +encodeURI(encodeURI(allContent)),
            type: 'get',
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
    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {
                var chk_value = [];
                chk_value.push($('#deliverId').val());
                var allContent=$("#content").val();
                //var clstep=$("#datastep").val();
                ajax_lotProjectYES(chk_value.toString(),allContent);
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
            url: "ajax_deliverNOONE6?t=" + new Date().getMilliseconds() + "&prono=" + selectids  +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("否决成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                }else{
                    alert("否决失败");
                }
            }
        };
        $.ajax(options);
    }
});
