/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("jquery");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");

    require("../common/templeteHelper");
    require("ajaxUpload");
    require("validate");

    //require("kindeditor");
    //require("kindeditor_all");
    //
    //KindEditor.create('textarea[name="description"]',{
    //    resizeType:2,
    //    height:'450px',
    //    width:'100% !important',
    //    allowPreviewEmoticons:true,
    //    allowImageUpload:true,
    //    allowUpload:true,
    //    fontSizeTable:['100px','16px','250px'],
    //    cssData:'body{}',
    //    items:[
    //        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
    //        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
    //        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
    //        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
    //        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
    //        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
    //        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
    //        'anchor', 'link', 'unlink', '|', 'about'
    //    ],
    //    extraFileUploadParams : {
    //        item_id : 1000,
    //        category_id : 1
    //    },
    //    afterCreate:(function() {
    //        this.sync();
    //    }),
    //    allowFileManager:true
    //
    //});


    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    form.init()

    var loadForm = function () {
        var target;
        var tpl;
        if ($("#syy_dept_appraise_v").length > 0) {
            tpl = require("text!app/tpl/forms/syy_rs_lc04_vTpl.html");
            target = $("#syy_dept_appraise_v");
        }
        if ($("#syy_dept_sub_appraise_v").length > 0) {
            target = $("#syy_dept_sub_appraise_v");
            tpl = require("text!app/tpl/forms/syy_rs_lc04_01_vTpl.html")
        }
        if (!target) return;
        form.render(target, tpl);
    }


    exports.pageLoad = function () {
        loadForm();
    };


    /*var vaLi = function validates() {
     $('#menu-name').multiselect({
     includeSelectAllOption: true,
     maxHeight: 400,
     buttonWidth: '264px'
     });*/

    $("#appform").validate({
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            $(":submit").button("loading");
            setTimeout(function () {$(":submit").button("reset") }, 10000);
            var _url = $("#qjSubmit").attr("data-url");
            //alert(_url);
            var d1 = $("#beginTime").val();//开始时间
            var d2 = $("#endTime").val();//结束时间
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            if (timestamp1 > timestamp2) {
                alert("开始时间不能小于结束时间！");
                return;
            }
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    } else
                        alert(data.msg);
                }
            });
        }
    });

    $("body").delegate("#upload", "click", function () {
        var fm = $("#upFile").attr("form_no");
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'ajax_syy_rs_lc04_upload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {formNo: fm},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                    loadForm();
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    })
});
