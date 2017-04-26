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
   // require("../init");
    $(".select_like").select2({});
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd HH:mm:ss',
            readOnly:true
        });
    });

    var form = require("../common/form");

    //form.init();

    $(".select2_multiple").select2({
    });

    $("body").delegate(".leaders","change",function() {
        $(".leaders").children("option").each(function(){
            if($(this).prop('selected')){
                var groupMemberList=$(".leaders").val();
                $("#zj").val(groupMemberList);
            }
        })
    });

    $("body").delegate("#reject", "click", function () {
        var prono=$("#deliverId").val();
        var sid = $("#zj").val();
        var allContent=$("#content").val();
        var _url = "syy_kg_lc06_approve4";
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds() + "&prono=" + prono + "&empids=" +sid+"&allContent=" +encodeURI(encodeURI(allContent))+"&no=no",
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

    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {
                var prono=$("#deliverId").val();
                var sid = $("#zj").val();
                var allContent=$("#content").val();
                ajax_lotProjectYESONE(prono,sid,allContent);
            }
        });
    });
    function ajax_lotProjectYESONE(prono,sid,allContent) {
        var options = {
            url: "syy_kg_lc06_approve4?t=" + new Date().getMilliseconds() + "&prono=" + prono + "&empids=" +sid+"&allContent=" +encodeURI(encodeURI(allContent)),
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




    //单个审回上一级
    $("body").delegate("#goBack", "click", function () {
        var prono=$("#deliverId").val();
        var allContent=$("#content").val();
        ajax_lotProjectNOONE(prono,allContent);
    })

    function ajax_lotProjectNOONE(selectids,allContent) {
        var options = {
            url: "ajax_deliverNOONE?t=" + new Date().getMilliseconds() + "&prono=" + selectids  +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("打回成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.close();
                }else{
                    alert("打回失败");
                }
            }
        };
        $.ajax(options);
    }
});
