/**
 * Created by user on 2016/8/5.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");
    check.checkAll("body", ".checkAll", ".checkBtn");
    require("mutiSelect");
    require("ajaxUpload");
    loadData();
    $("#failAllQueryBtn0").click(function () {
        loadData();
    })
    function loadData() {
      var datastep= $("#datastep").val();
        var url = "ajax_kg01_lotTpl5?datastep="+datastep+"&d=1";
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc01_lotTpl5.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }

    $('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });

//群审批否决上一级
    $("body").delegate("#lotReject", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
       /* var sid = $("#hidid").val();
        if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }*/
        var allContent=$("#allContent").val();
       /* ||( $("#hid1").val()==null||($("#hid1").val()).length<=0)*/

        ajax_lotProjectNO(chk_value.toString(),allContent);
    })

    function ajax_lotProjectNO(selectids,allContent) {
        var options = {
            url: "ajax_lotProjectNO5?t=" + new Date().getMilliseconds() + "&ids=" + selectids +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("否决成功");
                    window.location.reload();
                }else{
                    alert("否决失败");
                }
                if($(window.opener.document).find(".pagination li.active a")[0])
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                else
                    $(window.opener.document).find("#secondForm")[0].click();
            }
        };
        $.ajax(options);
    }


//群审批同意
    $("body").delegate("#lotApprove", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });

       /* var sid = $("#hidid").val();
        if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }*/

        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
        var allContent=$("#allContent").val();
        var clstep=$("#datastep").val();
        ajax_lotProjectYES(chk_value.toString(),allContent,clstep);
    })

    function ajax_lotProjectYES(prono,allContent,clstep) {
        var options = {
            url: "ajax_lotProjectYES5?t=" + new Date().getMilliseconds() + "&ids=" + prono +"&allContent=" +encodeURI(encodeURI(allContent))+ "&clstep=" + clstep ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    window.location.reload();
                }else{
                    alert("签核失败");
                }
                if($(window.opener.document).find(".pagination li.active a")[0])
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                else
                    $(window.opener.document).find("#secondForm")[0].click();
            }
        };
        $.ajax(options);
    }



    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }

        ajax_mainProjectNO(chk_value.toString());
    })

    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_kg01_lot_down?formNos="+selectids;
    }


//导入
    $("body").delegate("#upload", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
       /* var fm = $("#upFile").attr("form_no");*/
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'ajax_syy_kg01_lot_upload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data:{formNo:chk_value.toString()},
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


})