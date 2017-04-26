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
    require("../common/templeteHelper");//模板加载
    //widget.init();
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });


    $('.leaders,.leaderss').select2({
        placeholder: "请选择",
        width: '100%'
    });


    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");


    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    form.init()



    //培训申请单 提交按钮触发事件
    $("#appform").validate({
        rules: {

            /*projectName: {
             required: true,
             maxlength: 100
             },*/
            executionNamesel:{
                required: true
            },
            projectActualComplete: {
                required: true
            },
            chieveResult:{
                required: true
            },
            economicAnalysis:{
                required: true
            },
            projectEvaluate:{
                required: true
            },
            projectProblem:{
                required: true
            }
        },
        messages: {
            /*projectName: {
             required: "必填",
             maxlength: "长度不能超过50个字符"
             },*/
            executionNamesel:{
                required: "必填"
            },
            projectActualComplete: {
                required: "必填"
            },
            chieveResult: {
                required: "必填"
            },
            economicAnalysis:{
                required: "必填"
            },
            projectEvaluate:{
                required: "必填"
            },
            projectProblem:{
                required: "必填"
            }
        },

        submitHandler: function (form) {
            var valid= $("#appform").valid();
            if(valid){
                $("#addOrUpdateProjectInfo").attr("disabled","disabled") ;
            }
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateProjectInfo").attr("data-url");
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url:_url + "?projectNoSel1=" + $("#projectNo1").val(),
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
            /*$.ajax({
             url: _url + "?projectNoSel1=" + $("#projectNo1").val(),
             type: 'post',
             data: $("#appform").serializeObject(),
             success: function (data) {
             if (data.isSuccess) {
             alert("新增成功");
             $("#addOrUpdateProjectInfo").removeAttr("disabled") ;
             window.history.back();
             window.close();

             }
             }
             });*/
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

    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_executionId?t=" + new Date().getMilliseconds() + "&formNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                //var myData = eval("(" + data+")");
                console.log(data);
                // alert(data.projectPlanInfoTxt);
                $("#projectPlanInfoTxt").val(data.projectPlanInfoTxt);
                $("#projectNo").val(data.projectNo);
                $("#projectName").val(data.projectName)
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#executionNamesel", "change", function () {
        //选中值formNo
        var formNoSel= $("#executionNamesel option:selected").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });

    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_executionId?t=" + new Date().getMilliseconds() + "&projectNoSel1=" + formNoSel ,
            type: 'get',
            success: function (data) {
                $("#projectNo1").val(data.projectNo);
                $("#projectName").val(data.projectName);
                $("#assumeCompany").val(data.assumeCompany);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.endTime1);
                $("#leaderName").val(data.leaderName);
                $("#leader").val(data.leaderId);
                $("#projectPlanInfoTxt").val(data.projectPlanInfoTxt);
                $("#specialtyType").val(data.specialtyType);

            }
        };
        $.ajax(options);
    }
    //多选获取fenguan model
    $("body").delegate(".leaders", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidid").val(selectId);
        var sid = $("#hidname").val(selectName);

    })

    $("body").delegate(".leaderss", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaderss option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidids").val(selectId);
        var sid = $("#hidnames").val(selectName);

    })
    $("#projectName").val($("#executionNamesel option:selected").text());

    var template = require("template");

    $("body").delegate("#upload", "click", function () {
        var formNo = $("#formNo").val();
        var content=$("#content").val();
        var fileIds = [];
        $("input[name='fileName']").each(function(){
            fileIds.push($(this).attr("id"));
        });
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc03_executionUpload?t='+ new Date().getMilliseconds() ,
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: fileIds,           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {formNo:formNo},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                    //window.location.reload();
                // $("#file_List").load("file_reload_data.html?formNo="+$("#formNo").val()+"&formKind="+$("#formKind").val()+"&viewOnly=false"+"&data="+Math.random()+  " #filed_alone");
                 //console.info(a);
                       $.ajax({
                       url:"file_reload_data.html?t="+ new Date().getMilliseconds(),
                        type:"post",
                           async:false,
                           data:{
                               formNo: $("#formNo").val(),
                               formKind: $("#formKind").val(),
                               viewOnly:false,
                               content:content
                           },//服务器返回的格式,可以是json或xml等success fileList
                        success:function(data){
                          //  var fileList =data;
                          //  var array=new Array();
                          //  console.info(fileList.size);
                          //  $("#filed_alone").hide();
                        /*    var list=  document.getElementById("file_List");
                           for(array in fileList){
                                var a= document.createElement("a");
                               a.href="downloadFile?id="+array.fileId1;
                               a.innerHTML=array.fileName1;
                               list.appendChild(a);
                            }*/
                          //  var html = template.compile(tpl_files)(data);
                          //  $("#file_List").append(html);
                            /*alert(document.getElementById("file_List"));
                            document.getElementById("file_List").append(data);*/
                          //  window.location.href="file_reload_data"
                            $("#file_List").html(data);


                           }
                    });

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