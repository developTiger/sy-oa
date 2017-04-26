/**
 * Created by MJ005 on 2016/12/28.
 */
define(function (require, exports, module) {
    require('../init');
    require("jquery");
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    widget.init();
    var validate = require("validate");
    check.checkAll("body", ".checkAll", ".checkBtn");
    require("mutiSelect");
    require("select2");
    require("ajaxUpload");

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
    loadData();
    close_window();
/*    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        multiple: true

    });*/
    $(".select2_group").select2({
        placeholder: "请选择",
        width:'100%'
    });
    $("#failAllQueryBtn0").click(function () {
        loadData();
    });
    var form = require("../common/form");
    function loadData() {
        var url = "ajax_Unit_Leadership_Summary";
        var tpl = require("text!app/tpl/projectInfo/unit_Leadership_Summary.html");
        var data = $("#approveForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }
    function  reloadData() {
        var url = "ajax_Unit_Leadership_Summary";
        var tpl = require("text!app/tpl/projectInfo/unit_Leadership_Summary.html");

        if($("*[name='clStep']").val()>1){
            $("*[name='clStep']").val($("*[name='clStep']").val() - 1);
        }
            // $("*[name='clStep']").val();
        var data = $("#approveForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
       // window.location.href="unit_Leadership_Summary?clstep="+$("*[name='clStep']").val()+"&&formNo="+$("#formNo").val();
    }

/*    $("body").delegate(".specialist_group","change",function() {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }

    });*/
    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {

                var chk_value = [];
                var specialist_group=[];
                var list_projectNo=[];
       /*         var  object = document.getElementsByName( 'list_projectNo' );//获取项目编号
                for ( var  i = 0 ;i < object.length;i ++ ){
                   // console.info(object[i].innerText);
                    list_projectNo.push(object[i].innerText);
                }*/
                //console.info($('.checkBtn.checkBtn'));
                $('.checkBtn:checked').each(function () {
                    chk_value.push($(this).val());
                   // console.info($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
                   list_projectNo.push($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
                });

                if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
                    alert("请勾选项目");
                    return false;
                }
               $("#formNo_OpenFlow").val(chk_value);
                $("#list_projectNo").val(list_projectNo);
           /*     $("#specialist").children("option").each(function(){
                    if($(this).prop('selected')) {
                        alert($("#specialist").val());
                        specialist_group.push($("#specialist").val());
                    }
                   });*/
              /*  alert($(".specialist_group").val());
                alert($(".specialist_group").val().length);*/

          /*      $(".specialist_group option:selected").each(function(){
                    alert($(".specialist_group").val());
                    specialist_group.push($(".specialist_group").val());
                });*/
              // console.info($(".specialist_group").select2("data"));
                   /* }*///每一个option
               /* });*/
               /*  if(specialist_group.toString().length<=0||specialist_group.toString()==null){
                     alert("请选择专家");
                     return false;
                 }*/
              //  alert(chk_value);
               // alert(list_projectNo);
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
                /*               $.ajax({
                 url: _url + "?t=" + new Date().getMilliseconds(),
                 type: 'post',
                 data: $("#approveForm").serializeObject(),
                 success: function (data) {
                 if (data.isSuccess) {
                 alert("操作成功");
                 window.close();
                 }
                 }
                 });*/
/*                $.ajax({
                    //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                    url: _url,
                    secureuri: false,                       //是否启用安全提交,默认为false
                    fileElementId: ['upFile'],           //文件选择框的id属性
                    dataType: 'text',                       //服务器返回的格式,可以是json或xml等
                    data: $("#approveForm").serializeObject(),
                    success: function (data) {        //服务器响应成功时的处理函数

                    }
                });*/
                if($("*[name='clStep']").val()==11){
                    fileupload(_url);
                }else{
                    ajaxUp(_url);
                }
                if($(window.opener.document).find(".pagination li.active a")[0]) {
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                }
                else {
                    $(window.opener.document).find("#secondForm")[0].click();
                }

            }
        });
    });
    form.doSubmit = function () {
        var chk_value = [];
        var specialist_group=[];
        var list_projectNo=[];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
            // console.info($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
            list_projectNo.push($(this).parentsUntil().children(".list_projectNo1")[0].innerText);
        });

        if (chk_value.toString().length <= 0 || chk_value.toString() == null) {
            alert("请勾选项目");
            return false;
        }
        $("#formNo_OpenFlow").val(chk_value);
        $("#list_projectNo").val(list_projectNo);
/*        if($("*[name='clStep']").val()==4){
            if($(".specialist_group").val()==null||$(".specialist_group").val().length<=0){
                alert("请选择专家");
                return false;
            }
        }else if($("*[name='clStep']").val()==6){
            if($(".leader_name").val()==null||$(".leader_name").val().length<=0){
                alert("请选择专家");
                return false;
            }
        }*/
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
                    if(confirm("是否退出")){
                        //window.location.reload();

                        window.close();
                    }
                    else{
                        reloadData();
                        close_window();
                    }

                }
                else
                    alert(data.msg)
            }
        });
        if($(window.opener.document).find(".pagination li.active a")[0]) {
            $(window.opener.document).find(".pagination li.active a")[0].click();
        }
        else {
            $(window.opener.document).find("#secondForm")[0].click();
        }
    };
    function ajaxUp(_url) {
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                   alert("操作成功");
                    if(confirm("是否退出")) {
                        //  window.location.reload();
                        if($(window.opener.document).find(".pagination li.active a")[0]) {
                            $(window.opener.document).find(".pagination li.active a")[0].click();
                        }
                        else {
                            $(window.opener.document).find("#secondForm")[0].click();
                            //  $(window.opener.document).find(".pagination li.active a")[0].click();

                        }
                        window.close();

                    }else{
                        reloadData();
                        close_window();
                    }

                }else{
                    alert(data.msg);
                }
            }
        });
    }
    
    function  fileupload(url) {
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: url,
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: $("#approveForm").serializeObject(),
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("操作成功！");
                   /* $("#myModal").modal("hide");
                    loadData(2);*/
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else {
                        $(window.opener.document).find("#secondForm")[0].click();
                        //  $(window.opener.document).find(".pagination li.active a")[0].click();

                    }
                    if(confirm("是否退出")){
                       // window.location.reload();
                        window.close();
                    }
                    else{
                        reloadData();
                        close_window();
                    }
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    }

    function close_window(){
        if   (event.clientY   <   0   &&   event.clientX> document.body.scrollWidth)   {
            //event.returnValue   =   'Are   you   sure   you   want   to   leave   the   page? ';
            window.alert('欢迎光临爱智旮旯的博客！')
        }
    }
    window.onunload   =   function   (e)
    {

        if   (e.clientX   <   0   &&   e.clientX> document.body.scrollWidth)   {
            //event.returnValue   =   'Are   you   sure   you   want   to   leave   the   page? ';
            //window.alert('')
        }else{
            if($(window.opener.document).find(".pagination li.active a")[0]) {
                $(window.opener.document).find(".pagination li.active a")[0].click();
            }
            else {
                $(window.opener.document).find("#secondForm")[0].click();
                //  $(window.opener.document).find(".pagination li.active a")[0].click();

            }
        }
    }

});