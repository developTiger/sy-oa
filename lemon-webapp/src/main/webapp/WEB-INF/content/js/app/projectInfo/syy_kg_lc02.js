/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {
    require("select2");
    require("mutiSelect");
    require("jquery");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    //widget.init();
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");

    $(".select2_group").select2({
        placeholder: "请选择",
        width:'100%'
    });
    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
/*    if($("*[name='clStep']").val()==4){
        if($(".specialist_group").val()==null||$(".specialist_group").val().length<=0){
            alert("请选择专家");
            return false;
        }
    }else if($("*[name='clStep']").val()==6){
        if($(".leader_name").val()==null||$(".leader_name").val().length<=0){
            alert("请选择专家");
            return false;
        }
    }else{
        $("#employeeId").val($(".specialist_group").val());//判断专家
    }
    form.init();*/

    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {
              var chk_value = [];
                var specialist_group=[];
                var list_projectNo=[];
            /*    $('.checkBtn:checked').each(function () {
                    chk_value.push($(this).val());
                    list_projectNo.push($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
                });

                if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
                    alert("请勾选项目");
                    return false;
                }*/
                chk_value.push($("*[name='formNo']").val());
                list_projectNo.push($("*[name='projectNo']").val());
                $("#formNo_OpenFlow").val(chk_value);
                $("#list_projectNo").val(list_projectNo);
                if($("*[name='clStep']").val()==4){
                    if($(".specialist_group").val()==null||$(".specialist_group").val().length<=0){
                        alert("请选择专家");
                        return false;
                    }
                }else if($("*[name='clStep']").val()==7){
                    if($(".leader_name").val()==null||$(".leader_name").val().length<=0){
                        alert("请选择领导");
                        return false;
                    }
                }
                $("#employeeId").val($(".specialist_group").val());//判断专家
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#approveUrl").val();

                    ajaxUp(_url);



            }
        });
    });
    form.doSubmit = function () {
        var chk_value = [];
        var specialist_group=[];
        var list_projectNo=[];
    /*    $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
            // console.info($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
            list_projectNo.push($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
        });

        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }*/
      /*  $("#formNo_OpenFlow").val($("*[name='formNo']").val());
        $("#list_projectNo").val($("*[name='projectNo']").val());*/
        chk_value.push($("*[name='formNo']").val());
        list_projectNo.push($("*[name='projectNo']").val());
        $("#formNo_OpenFlow").val(chk_value);
        $("#list_projectNo").val(list_projectNo);

        $("#employeeId").val($(".specialist_group").val());//判断专家


        var appUrl = $("#approveUrl").val();
        $.ajax({
            url: appUrl+"?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#approveForm").serialize(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");

                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else {
                        $(window.opener.document).find("#secondForm")[0].click();
                        //  $(window.opener.document).find(".pagination li.active a")[0].click();

                    }
                    //window.opener.document.getElementsByClassName()
                    window.close();

                }
                else{
                    alert(data.msg);

                }
            }
        });

    };
    function ajaxUp(_url) {
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else {
                        $(window.opener.document).find("#secondForm")[0].click();
                        //  $(window.opener.document).find(".pagination li.active a")[0].click();

                    }
                    window.close();

                }else{
                    alert(data.msg);
                }
            }
        });
    }


    //培训申请单 提交按钮触发事件
    $("#appform").validate({

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateProjectInfo").attr("data-url");

            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {

                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    }
                }
            });
        }

    });

    $("body").delegate("#unit_Leadership_Summary","click",function () {
       //
  /*      $.ajax({
            url: 'unit_Leadership_Summary',
            type: 'post',
            data: $("#appform").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("新增成功");
                    window.close();
                }
            }
        });*/

        window.location.href="unit_Leadership_Summary?clstep="+$("*[name='clStep']").val()+"&&formNo="+$("#formNo").val();

    });


    $("body").delegate(".detial", "click", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        var projectNo =$("#projectNo").val();
       // alert(projectNo);
        window.location.href="ajax_Project_Plan?projectNo="+projectNo;
    });
    $("body").delegate("#projectNamesel", "change", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });

    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_openId?t=" + new Date().getMilliseconds() + "&formNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                //var myData = eval("(" + data+")");
                //  console.log(data);
                // alert(data.projectPlanInfoTxt);
                $("#projectPlanInfoTxt").val(data.projectPlanInfo);
                $("#projectNo").val(data.projectNo);
                $("#projectName").val(data.projectName);
               /* $("#deptName").val(data.deptName);*/
                $("#assumeCompany").val(data.assumeCompany);
                $("#joinComopany").val(data.joinComopany);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.beginTime1);
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#upload", "click", function () {
       var formNo = $("#formNo").val();
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc02_resultUpload',
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
    

});