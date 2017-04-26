/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

   // require("jquery");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    var template = require("template");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    //widget.init();
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");


    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
    form.init();
    //培训申请单 提交按钮触发事件
    $("#appform").validate({

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form


/*            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                secureuri: false,                       //是否启用安全提交,默认为false
                //fileElementId: ['upFile'],           //文件选择框的id属性
                //dataType: 'text',
                success: function (data, status) {

                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    }

                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });*/
            $(":submit").button("loading");
            setTimeout(function () {$(":submit").button("reset") }, 10000);
            var fileIds = [];
            $("input[name='fileName']").each(function(){
                fileIds.push($(this).attr("id"));
            });
            var already_FileId=[];
            $("input[name='already_upFileId']").each(function () {
                //alert($(this).attr("value"));
                already_FileId.push($(this).attr("value"));
            });
            var already_FileName=[];
            $("input[name='already_upFileName']").each(function () {
                //alert($(this).attr("value"));
                already_FileName.push(encodeURI(encodeURI($(this).attr("value"))));
            });
            //console.info(already_FileName);
            //console.info(already_FileId);
            var pname =   $("#projectNamesel_back").find("option:selected").text();
            var _url = $("#addOrUpdateProjectInfo").attr("data-url")+"?already_FileName="+already_FileName+"&already_FileId="+already_FileId
                +"&pname="+encodeURI(encodeURI(pname));
            var judge=$("#beans_back_judge").val();

            $.ajaxFileUpload({
                url: _url,
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
                        if(judge){
                            history.go(-1);
                        }else {
                            window.close();
                        }
                    } else {
                        alert(result.msg);
                    }
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else {
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('新增失败，请重试！！');
                }
            });


        }

    });
    $("body").delegate("#projectNamesel", "change", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });
    $("body").delegate("#projectNamesel_back", "change", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel_back").val();
        //选中值name
       // var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectApprove_Back(formNoSel);
    });
    function projectApprove_Back(formNoSel) {
        var options = {
            url: "ajax_openId?t=" + new Date().getMilliseconds() + "&formNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                //var myData = eval("(" + data+")");
                /*  console.log(data);
                 alert(data.beginTime1);*/
                $("#projectName_back").val(data.projectName);//项目名称
                $("#leaderName_back").val(data.leaderName);//项目长
                $("#projectNo_back").val(data.projectNo);//项目计划编号
                $("#niandu_back").val(data.year_Str);//项目计划年度
                $("#assumeCompany_back").val(data.assumeCompany);//承担单位
                $("#joinComopany_back").val(data.joinComopany);//参加单位
                $("#beginTime_back").val(data.beginTime1);//开始时间
                $("#endTime_back").val(data.endTime1);//结束时间
                $("#atherPeople_back").val(data.groupMembers);
                $("#special_Type").val(data.specialtyType);
            }
        };
        $.ajax(options);
    }
    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_openId?t=" + new Date().getMilliseconds() + "&formNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                //var myData = eval("(" + data+")");
                /*  console.log(data);
                alert(data.beginTime1);*/
                $("#projectName").val(data.projectName);//项目名称
                $("#leaderName").val(data.leaderName);//项目长
                $("#projectNo").val(data.projectNo);//项目计划编号
                $("#niandu").val(data.year_Str);//项目计划年度
                $("#assumeCompany").val(data.assumeCompany);//承担单位
                $("#joinComopany").val(data.joinComopany);//参加单位
                $("#beginTime").val(data.beginTime1);//开始时间
                $("#endTime").val(data.endTime1);//结束时间
                $("#atherPeople").val(data.groupMembers);
                $("#special_Type").val(data.specialtyType);
               /* $("#projectPlanInfoTxt").val(data.projectPlanInfo);
               /!* $("#deptName").val(data.deptName);*!/
                $("#assumeCompany").val(data.assumeCompany);
                $("#joinComopany").val(data.joinComopany);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.beginTime1);*/

            }
        };
        $.ajax(options);
    }

    $("body").delegate("#project_detial", "click", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name

        var pname =   $("#projectName").find("option:selected").text();
       var projectNo =$("#projectNo").val();
        window.location.href="ajax_Project_Plan?projectNo="+projectNo;
/*        alert(projectNo);
        var options = {
            url: "ajax_Project_Plan?t=" + new Date().getMilliseconds() + "&projectNo=" + projectNo ,
            type: 'get',
            success: function (data) {
          /!*      $("#projectName").val(data.projectName);//项目名称
                $("#leaderName").val(data.leaderName);//项目长
                $("#projectNo").val(data.projectNo);//项目计划编号
                $("#niandu").val(data.year_Str);//项目计划年度
                $("#assumeCompany").val(data.assumeCompany);//承担单位
                $("#joinComopany").val(data.joinComopany);//参加单位
                $("#beginTime").val(data.beginTime1);//开始时间
                $("#endTime").val(data.endTime1);//结束时间*!/
            }
        };
       $.ajax(options);*/

    });
    $("body").delegate("#project_detial_back", "click", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        var projectNo =$("#projectNo_back").val();
        window.location.href="ajax_Project_Plan?projectNo="+projectNo;
    });

    $("body").delegate("#upload", "click", function () {
       var formNo = $("#projformNo").val();
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


    var id = 1;
    var tpl_files = require("text!app/tpl/projectInfo/projectInfoFileUpload.html");
    //多附件上传
    $("body").delegate(".addFileBtn","click",function(){
        id++;
        var d = {num:id};
        var html = template.compile(tpl_files)(d);
        $("#filesHtml").append(html);
    });
    //附件删除
    $("body").delegate(".deleteFileBtn","click",function(){
        $(this).parent().parent().remove();
    });
    //已上传附件删除
    $("body").delegate(".deleteupFileBtn","click",function(){
        $(this).parent().remove();
    });
});