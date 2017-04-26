/**
 * Created by zhouz on 2016/5/15.
 */
//define(function (require, exports, module) {
//    require('../init');
//    require("ajaxUpload");
//    require("jquery");
//    require("../common/jquery.serializeObject");
//    var widget = require("../common/widget");
//    var check = require("../common/checkbox");
//    var validate = require("validate")
//    var wDatePicker = require("wdatePicker");
//    require("mutiSelect");
//    $("body").delegate(".Wdate", "click", function () {
//        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
//    });
//
//    require("kindeditor");
//    require("kindeditor_all");
//
//    window.content = KindEditor.create('textarea[name="content"]',{
//        //resizeType:1,
//        height:'450px',
//        themeType:'default',
//        width:'100%!important',
//        allowPreviewEmoticons:true,
//        allowImageUpload:true,
//        allowUpload:true,
//        fontSizeTable:['100px','16px','250px'],
//        cssData:'body{}',
//        //items:[
//        //    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
//        //    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
//        //    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
//        //    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
//        //    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
//        //    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
//        //    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
//        //    'anchor', 'link', 'unlink', '|', 'about'
//        //],
//        extraFileUploadParams : {
//            item_id : 1000,
//            category_id : 1
//        },
//        afterCreate:(function() {
//            this.sync();
//        }),
//        allowFileManager:true
//    });
//
//
//    $("#publishNews").validate({
//        submitHandler: function (form) {
//            var kk=  $("iframe").contents().find("body").html();
//            kk=kk.replace(/\"/g,"'");
//            $("#tempKindEditor").val(kk);
//            var sel=$("#noticeType").find("option:selected").val();
//            if(sel=="Train" ) {
//                $("#tdept").val($("#toDept").val().toString());
//                $("#toDeptName").val($(".multiselect").attr("title"));
//            }
//            /*var str= $("#tempKondEditor").val();*/
//            $.ajaxFileUpload({
//                url: 'ajax_add_update_news',
//                secureuri: false,
//                fileElementId: ['fileId'],
//                dataType: 'text',
//                data: $("#publishNews").serializeObject(),
//                success: function (data, status) {
//
//                    var resultStart = data.indexOf("{");
//                    var resultEnd = data.indexOf("}");
//                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
//
//                    if (result.isSuccess) {
//                        alert("操作成功！");
//
//                    } else {
//                        alert(result.msg);
//                    }
//                },
//                error: function (data, status, e) { //服务器响应失败时的处理函数
//                    $('#result').html('文件上传失败，请重试！！');
//                }
//            });
//        }
//    });
//    $('#toDept').multiselect({
//        includeSelectAllOption: true,
//        maxHeight: 400,
//        buttonWidth: '100%'
//    });
//    widget.init();
//});

//上面的在其他地方不可用
/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    require("ajaxUpload");
    require("jquery");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate")
    var wDatePicker = require("wdatePicker");
    require("mutiSelect");
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    require("kindeditor");
    require("kindeditor_all");

    KindEditor.create('textarea[name="content"]',{
        resizeType:2,
        height:'450px',
        width:'100% !important',
        allowPreviewEmoticons:true,
        allowImageUpload:true,
        allowUpload:true,
        fontSizeTable:['100px','16px','250px'],
        cssData:'body{}',
        items:[
            'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
            'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
            'anchor', 'link', 'unlink', '|', 'about'
        ],
        extraFileUploadParams : {
            item_id : 1000,
            category_id : 1
        },
        afterCreate:(function() {
            this.sync();
        }),
        allowFileManager:true

    });
    loadData();
    $("#RoleQueryBtn").click(function () {
        loadData();
    });

    function loadData() {
        var tpl = require("text!app/tpl/publishnews/publishnewsTpl.html");
        var url = $("#searchForm").attr("data-url");
        if(url == undefined) return;
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
        $(".ke-container").css("cssText","width:80%!important");
    }


    /* editor('#image').click(function() {
     editor.loadPlugin('image', function () {
     editor.plugin.imageDialog({
     imageUrl: K('#url').val(),
     clickFn: function (url, title, width, height, border, align) {
     K('#url').val(url);
     editor.hideDialog();
     }
     });
     });
     });*/



    $("body").delegate("#addOrUpdateDept","click",function(){
        alert($("#addOrUpdateDept").attr("data-url"));
        $.ajax({
            url: "_ajax_delete_notice" + "?id="+$("#addOrUpdateDept").attr("data-url")+"&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功");
                }else{
                    alert("删除失败");
                }
            }
        });
    });

    check.checkAll("body", ".checkAll", ".checkBtn")

    $("#noticeType").change(function(){
        var val=$("#noticeType").find("option:selected").val();
        if(val=="News"){
            $(".showTrain").addClass("hidden");
            $(".deptCt").addClass("hidden");
            $(".showEx").addClass("hidden");
        }
        if(val=="Train"){
            $(".showEx").addClass("hidden");
            $(".deptCt").removeClass("hidden");
            $(".showTrain").removeClass("hidden");
        }
        if(val=="Institution"){
            $(".showTrain").addClass("hidden");
            $(".deptCt").addClass("hidden");

            $(".showEx").addClass("hidden");
        }
        if(val=="DeptExamine"){
            $(".showTrain").addClass("hidden");
            $(".deptCt").addClass("hidden");
            $(".showEx").removeClass("hidden");
        }
        if(val=="EmpExaming"){
            $(".showTrain").addClass("hidden");
            $(".deptCt").addClass("hidden");
            $(".showEx").removeClass("hidden");
        }
    });

    $("#publishNews").validate({
        submitHandler: function (form) {
            var kk=  $("iframe").contents().find("body").html();
            kk=kk.replace(/\"/g,"'");
            $("#tempKindEditor").val(kk);
            var sel=$("#noticeType").find("option:selected").val();
            if(sel=="Train" ) {
                $("#tdept").val($("#toDept").val().toString());
                $("#toDeptName").val($(".multiselect").attr("title"));
            }
            /*var str= $("#tempKondEditor").val();*/
            $.ajaxFileUpload({
                url: 'ajax_add_update_news',
                secureuri: false,
                fileElementId: ['fileId'],
                dataType:"html",
                data: $("#publishNews").serializeObject(),
                success: function (data, status) {

                    var result = JSON.parse(data);

                    if (result.isSuccess) {
                        alert("操作成功！");
                        window.location.href='sra_p_showNewsList';

                    } else {
                        alert(result.msg);
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });
        }
    });
    $('#toDept').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '100%'
    });

    /* $("body").delegate("#submitPublish","click",function(){
     var kk=  $("iframe").contents().find("body").html();
     kk=kk.replace(/\"/g,"'");
     $("#tempKindEditor").val(kk);
     *//*var str= $("#tempKondEditor").val();*//*
     $.ajaxFileUpload({
     url: 'ajax_add_update_news',
     secureuri: false,
     fileElementId: ['fileId'],
     dataType: 'text',
     data: $("#publishNews").serializeObject(),
     success: function (data, status) {

     var resultStart = data.indexOf("{");
     var resultEnd = data.indexOf("}");
     var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

     if (result.isSuccess) {
     alert("上传成功！");

     } else {
     alert(result.msg);
     }
     },
     error: function (data, status, e) { //服务器响应失败时的处理函数
     $('#result').html('文件上传失败，请重试！！');
     }
     });

     });*/

    widget.init();
});