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
    //form.init();


    //群审批否决
    $("body").delegate("#goBack", "click", function () {
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#content").val();
        ajax_lotProjectNO(chk_value.toString(),allContent);
    });
    function ajax_lotProjectNO(selectids,allContent) {
        var options = {
            url: "ajax_deliverNO5?t=" + new Date().getMilliseconds() + "&ids=" + selectids +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data.isSuccess){
                    alert("否决成功");
                    window.location.reload();
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


    //审批同意
    $("body").delegate("#approve", "click", function () {
        var awardTime=$('#awardTime').val();
        if(awardTime==""){
            alert("请选择获得时间");
            return;
        }
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#content").val();
        ajax_lotProjectYES(chk_value.toString(),allContent,awardTime);
    });

    function ajax_lotProjectYES(prono,allContent,awardTime) {
        var fileIds = [];
        $("input[name='fileName']").each(function(){
            fileIds.push($(this).attr("id"));
        });
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: "syy_kg_lc06_approve9?t=" + new Date().getMilliseconds() + "&ids=" + prono +"&allContent=" +encodeURI(encodeURI(allContent))+"&awardTime="+ awardTime,
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: fileIds,
            type:'post',//文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            //data: {formNo:formNo},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("操作成功")
                    window.location.reload();
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                } else {
                    // alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    };
    $("body").delegate("#reject", "click", function () {
        var awardTime=$('#awardTime').val();
        //if(awardTime==""){
        //    alert("请选择获得时间");
        //    return;
        //}
        var chk_value = [];
        chk_value.push($('#deliverId').val());
        var allContent=$("#content").val();
        var clstep=$("#datastep").val();
        var _url = "syy_kg_lc06_approve9";
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds() + "&ids=" + chk_value.toString() +"&allContent=" +encodeURI(encodeURI(allContent))+ "&clstep=" + clstep+"&awardTime="+ awardTime+"&no=no",
            type: 'post',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.close();
                }
            }
        });
    });
});
