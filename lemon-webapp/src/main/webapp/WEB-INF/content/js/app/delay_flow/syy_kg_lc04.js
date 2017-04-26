/**
 * Created by swb on 2016/8/25.
 */
define(function (require, exports, module) {

    require("jquery");
    require("select2");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    require("mutiSelect");
    //widget.init();
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            skin:"default",
            readOnly:true
        });
    });

    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");

    $('.leaders').select2({
        placeholder: "请选择",
        width: '100%'
    });

    /*$('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });*/
    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    form.init()

    jQuery.validator.addMethod("_character", function(value, element) {
        var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$|\、|\，|\,|\?|\。|\!|\！|\(|\)|\（|\）|\"|\"|\;|\“|\”|\；|\>|\<|\《|\》/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "只能为字母，数字，中文");

    //培训申请单 提交按钮触发事件
    $("#appform").validate({

        submitHandler: function (form) {

            var valid= $("#appform").valid();
            if(valid){
                $("#addOrUpdateDelayInfo").attr("disabled","disabled") ;
            }

            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateDelayInfo").attr("data-url");

            /*$.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {

                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    }
                }
            });*/
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url: _url + "?projectNo" + $("#projectNo").val(),
                secureuri: false,                       //是否启用安全提交,默认为false
                fileElementId: ['upFile'],           //文件选择框的id属性
                dataType: 'text',                       //服务器返回的格式,可以是json或xml等
                //data: {formNo:formNo},
                data: $("#appform").serializeObject(),
                success: function (data, status) {        //服务器响应成功时的处理函数
                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                    if (result.isSuccess) {
                        alert("新增成功！");
                        window.history.back();
                        window.close();
                    } else {
                        //alert(result.msg);
                        alert("新增失败");
                    }

                    if($(window.opener.document).find(".pagination li.active a")[0])
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    else
                        $(window.opener.document).find("#secondForm")[0].click();

                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });
        }

    });

    $("body").delegate("#delayNamesel", "change", function () {
        //选中值formNo
        var formNoSel= $("#delayNamesel option:selected").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });

    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_scientificId?t=" + new Date().getMilliseconds() + "&projectNoSel1=" + formNoSel ,
            type: 'get',
            success: function (data) {
                $("#projectNo").val(data.projectNo);
                $("#projectName").val(data.projectName);
                $("#assumeCompany").val(data.assumeCompany);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.endTime1);
                $("#leaderName").val(data.leaderName);
                $("#type_zy").val(data.specialtyType);
                $("#delayname2").val(data.projectName);
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#approveAllShow","click",function(){
        $.ajax({
            url:'approve_all_show',
            type:'post',
            success:function(data){
                if(data){
                    location.href="approve_all_show";
                }
            }
        });
    });

    $("body").delegate("#approveChiefShow","click",function(){
        $.ajax({
            url:'approve_chief_show',
            type:'post',
            success:function(data){
                if(data){
                    location.href="approve_chief_show";
                }
            }
        });
    });



    //选择专家审核
    $("body").delegate(".leaders", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidid").val(selectId);
        var sid = $("#hidname").val(selectName);
    });
});
