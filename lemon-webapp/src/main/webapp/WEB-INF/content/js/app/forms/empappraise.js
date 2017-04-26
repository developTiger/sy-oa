/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("jquery");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");
    var tableSave = require("../common/tableSave");
    var List = require("../common/pagelist");
    require("ajaxUpload");

    var validate = require("validate");

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });


    //审核页面加载
    var form = require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {

        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })
    $("body").delegate("#table2", "change", function(){
        var step = $("#step").val();
        var complete = $("#complete").val();
        if (step == 2 && complete == "false")
            $(".js_edit").removeClass("hidden");
        var ts = new tableSave({
            targetTable: ".js_tableSave",
            modifyBtn: ".js_update",
            saveBtn: ".js_save",
            postUrl: "ajax_update_confirm_dept_appraise"
        });
        var tsAll = new tableSave({
            targetTable: ".js_tableSave",
            modifyBtn: ".js_update",
            saveBtn: ".js_save",
            postUrl: "ajax_update_confirm_allDdepts_appraise"
        });
        if($("#syy_emp_sub_appraise_v").length > 0) {
            ts.init();
        }else{
            tsAll.init();
        }
    });
    //渲染页面
    var renderForm = function () {
        var tpl;
        var target;



        if ($("#syy_emp_appraise_v").length > 0) {
            tpl = require("text!app/tpl/forms/syy_rs_lc03_vTpl.html");
            target = $("#syy_emp_appraise_v");

        }
        if ($("#syy_emp_sub_appraise_v").length > 0) {
            target = $("#syy_emp_sub_appraise_v");
            tpl = require("text!app/tpl/forms/syy_rs_lc03_01_vTpl.html")
        }
        form.render(target, tpl);

        if ($("#syy_emp_sub_appraise_v").length > 0) {
            var ts = new tableSave({
                targetTable: ".js_tableSave",
                modifyBtn: ".js_update",
                saveBtn: ".js_save",
                postUrl: "ajax_update_confirm_dept_appraise"
            });
            var tsAll = new tableSave({
                targetTable: ".js_tableSave",
                modifyBtn: ".js_update",
                saveBtn: ".js_save",
                postUrl: "ajax_update_confirm_allDdepts_appraise"
            });
            if($("#syy_emp_sub_appraise_v").length > 0) {
                ts.init();
            }else{
                tsAll.init();
            }
        }

    }
    exports.pageLoad = function () {
        renderForm();
        loadData();


    };

    //$(document).ready(function () {
    //    loadData();
    //})

    function loadData() {
        var tpl = require("text!app/tpl/forms/syy_rs_lc03_EmpPages_vTpl.html");
        var url = "ajax_emp_all_pages";
        var formNo = $("#empDetailFormNo").val()
        List("#table2", tpl, url, "formNo="+formNo, 1, 10);

    }


    //$("body").delegate(".self-evaluation","click",function(){
    //    var id=$("#id").val();
    //    var formNo=$("#formNo").val();
    //    var detailNo=$("#detailNo").val();
    //    if(detailNo==null){
    //        window.location.href="sra_s_selfevaluation?id="+id+"&formNo="+formNo;
    //    }else{
    //        window.location.href="sra_s_selfevaluation?id="+id+"&formNo="+formNo+"&detailNo="+detailNo;
    //    }
    //});

    $("body").delegate("#form_send", "click", function () {

        var _url = $("#form_self_evaluation").attr("data-url");
        var empSelfGrade = $("#empSelfGrade").val();
        if(empSelfGrade == "") {
            alert("请选择自评等级！");
            return false;
        }
        $.ajax({
            url: _url,
            type: 'get',
            data: encodeURI($("#form_self_evaluation").serialize()),
            success: function (data) {
                if (data.isSuccess) {
                    //var html = template.compile(tpl)(data);
                    //target.html(html);
                    window.location.href="sra_index";
                }
            }
        });
    });


    $("#appform").validate({
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            $(":submit").button("loading");
            setTimeout(function () {$(":submit").button("reset") }, 10000);
            var _url = $("#qjSubmit").attr("data-url");
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
            url: 'ajax_syy_rs_lc03_upload',
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
                    loadData();
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
