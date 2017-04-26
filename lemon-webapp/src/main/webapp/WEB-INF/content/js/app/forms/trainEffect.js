/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("ajaxUpload");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    //var tpl = require("text!app/tpl/forms/syy_rs_lc02_vTpl.html");
    var tpl_1 = require("text!app/tpl/forms/syy_rs_lc02_v_getFormByEmpIdTpl.html");//动态展示页面培训名称所带的4个内容
    var tpl_files = require("text!app/tpl/forms/syy_rs_lc02_v_filesUploadTpl.html");
    require("../common/templeteHelper");

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    //check.niceCheck("input");
    /*$(".checkBtn").each(function(){
        $(this).iCheck('check');//iCheck 系统设置的checkbox样式
    })*/

    /**
     * 通过ajax传输数据，并将所需要显示的数据放到页面的div上
     */

    $("body").delegate("#form_kind_name","change",function(){
        var formNo;
        var name;

        formNo=$(this).find("option:selected").val();//获取选中的下拉列表的值
        name=$(this).find("option:selected").text();//获取选中的下拉列表的文本框的值
        $("#hidTxt").val(name);

        $(document).on()
        $(document).delegate()

        $.ajax({
            url:"get_form_by_formKindName"+"?t="+new Date().getMilliseconds()+"&formNo="+formNo,
            type:'get',
            async:false,
            success:function(data){
                if(data){
                    var html = template.compile(tpl_1)(data);
                    $("#otherData").html(html).show();
                }else{
                    $("#otherData").hide();
                    alert("请选择培训名称！");
                }

                /**
                 * 也可以不用模板加载 就是不需要将其放到外面或者在js中进行拼接
                 可以放到页面上 想将其隐藏 当下拉列表发生变化时，再将其显示出来
                 在显示的时候 直接是：$("#name").val(data.name)..... 直接进行赋值
                 这也是一种做法
                 */
                //下面是第二种方法
                /*if(data) {
                        $("#trainContent").val(data.trainContent);
                        $("#strBeginDate").val(data.trainBeginDate);
                        $("#strEndDate").val(data.trainEndDate);
                        $("#trainPlace").val(data.trainPlace);
                        $("#1").show()
                        $("#2").show()
                        $("#3").show()
                        $("#4").show()
                        alert("获取数据成功");

                } else {
                    $("#1").hide()
                    $("#2").hide()
                    $("#3").hide()
                    $("#4").hide()
                    alert("请选择培训名称")
                }*/


            }
        })
    })



    $("#appform").validate({
        rules:{
            formNo_1:{
                required:true
            },
            suggest:{
                required:true,
                maxlength:100
            }
        },
        messages:{
            formNo_1:{
                required:"必须选择"
            },
            suggest:{
                required:"必填",
                maxlength:"长度不能超过100个字符"
            }
        },

        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#trainEffectSubmit").attr("data-url");
            var fileIds = [];
            $("input[name='fileName']").each(function(){
                fileIds.push($(this).attr("id"));
            })
            $.ajaxFileUpload({

                url: 'ajax_add_update_files',
                secureuri: false,
                fileElementId: fileIds,
                dataType: 'text',
                data: $("#appform").serializeObject(),
                success: function (data, status) {

                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("新增成功！");
                        window.close();
                    } else {
                        alert(result.msg);
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('新增失败，请重试！！');
                }
            });
        }
    })


    /*$(function(){
        if($("#syy_trainEffect_v")){
            var formNo = $("#syy_trainEffect_v").attr("form-no");
            //var formKind = $("#syy_leave_v").attr("form-Kind");
            var _url = $("#syy_trainEffect_v").attr("data-url");
            $.ajax({
                url: _url + "?formNo=" + formNo + "&t=" + new Date().getMilliseconds(),
                type: 'get',

                success: function (data) {
                    if (data) {
                        var html = template.compile(tpl)(data);
                        $("#syy_trainEffect_v").html(html);
                    }
                }
            });
        }
    })*/

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_trainEffect_v").length>0) {
            target=$("#syy_trainEffect_v");
            var tpl = require("text!app/tpl/forms/syy_rs_lc02_vTpl.html");
            form.render(target,tpl);
        }
    }


    exports.pageLoad=function(){
        loadForm();
    };

    var id = 1;
    //多附件上传
    $("body").delegate(".addFileBtn","click",function(){
        id++;
        var d = {num:id};
        var html = template.compile(tpl_files)(d);
        $("#filesHtml").append(html);
    })

    //附件删除
    $("body").delegate(".deleteFileBtn","click",function(){
        $(this).parent().parent().remove();
    })


    $(document).ready(function () {
        if($("#ftrainName").val()!="" && $("#ftrainName").val()!=null){
            var trainName = $("#ftrainName").val();
            $("#form_kind_name option[text='"+trainName.trim()+"']").attr("selected", true);
            $("#form_kind_name").trigger("change");
        }
    })

    $(document).ajaxStart(function () {
        //alert("start");
    }).ajaxComplete(function () {
        //alert("complete");
    }).ajaxSuccess(function () {
        //alert("success");
    })



});