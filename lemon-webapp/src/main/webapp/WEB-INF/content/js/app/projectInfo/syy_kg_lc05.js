/**
 * Created by zhouz on 2016/7/18.
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
    require("mutiSelect");
    require("../common/templeteHelper");//模板加载
    //widget.init();
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
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
    form.init();
    // 无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    //form.init(function () {
    //});
    $('#acceptanceList').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '100%'
    });

    /*获取下拉列表以选择的数据*/
    var mustall="验收申请表，计划任务书（专项合同）及任务、经费调整的相关文件，验收评价报告，项目总结报告";
   // alert(mustall);
    $("#hidAcceptanceList").val(mustall);
    $("body").delegate("#acceptanceList","change",function(){
         var must="验收申请表，计划任务书（专项合同）及任务、经费调整的相关文件，验收评价报告，项目总结报告，";
         var sel=$("#acceptanceList").val();
         var all=must+sel
     //  alert(all) ;
        $("#hidAcceptanceList").val(all);
    });

    //提交按钮触发事件
    $("#appform").validate({

        submitHandler: function (form) {

            var valid= $("#appform").valid();
            if(valid){
                $("#addOrUpdateProjectInfo").attr("disabled","disabled") ;
            }


            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateProjectInfo").attr("data-url");
            var val=$("#projectNo1").val();
            $.ajax({
                url: _url + "?projectNo1=" +val,
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("申请成功");
                        window.history.back();
                        window.close();
                    }

                    if($(window.opener.document).find(".pagination li.active a")[0])
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    else
                        $(window.opener.document).find("#secondForm")[0].click();
                }
            });
        }
    })

    $("body").delegate("#projectNamesel", "change", function () {
        //选中值formNo
         var formNoSel= $("#projectNamesel").val();
        //选中值name
         //var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });

    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_acceptanceId?t=" + new Date().getMilliseconds() + "&projectNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                $("#projectPlanInfoTxt").val(data.projectPlanInfo);
                $("#projectNo1").val(data.projectNo);
                $("#projectName").val(data.projectName);
                $("#deptName").val(data.deptName);
                $("#assumeCompany").val(data.assumeCompany);
                $("#joinComopany").val(data.joinComopany);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.endTime1);
                $("#type_zy").val(data.specialtyType);
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#upload", "click", function () {
        var formNo = $("#projformNo").val();
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc05_resultUpload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {formNo:formNo},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                    $("#myModal").modal("hide");
                    loadData(2);
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
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

    /*$("body").delegate("#approve","click",function(){
        var sid = $("#hidid").val();
        /!* if(sid.length <= 0||sid==null){
         alert("请选择分管领导");
         return false;
         }*!/
        var prono=$("#kgkFormNo").val();
        //var prono = $(".projectnoOne").attr("data-value");
        var allContent = $("#allContent").val();
        ajax_lotProjectNOONE(prono, sid, allContent);
    });
    function ajax_lotProjectNOONE(prono, sid, allContent) {
        var options = {
            url: "kg_lc05_approve20_zj?t=" + new Date().getMilliseconds() + "&forno=" + prono + "&empids=" + sid + "&allContent=" + encodeURI(encodeURI(allContent)),
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("打回成功");
                    window.location.reload();
                } else {
                    alert("打回失败");
                }
                if($(window.opener.document).find(".pagination li.active a")[0])
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                else
                    $(window.opener.document).find("#secondForm")[0].click();
            }
        };
        $.ajax(options);
    }*/
});