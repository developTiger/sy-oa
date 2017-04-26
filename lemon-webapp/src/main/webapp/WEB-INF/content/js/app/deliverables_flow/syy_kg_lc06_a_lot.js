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

    loadData();

    $("#failAllQueryBtn").click(function () {
        loadData();
    });

    function loadData() {
        var datastep= $("#datastep").val();
        var url = "syy_kg06_plTpl";
        var tpl = require("text!app/tpl/delivers/syy_kg_lc06_lotTpl5.html");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);
    }


    $('.leaders').multiselect({
        includeSelectAllOption: true,
        maxHeight: 400,
        buttonWidth: '264px'
    });

//群审批打回上一级
    $("body").delegate("#lotReject", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
      var sid = $("#hidid").val();
      /*  if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }*/
        var allContent=$("#allContent").val();
       /* ||( $("#hid1").val()==null||($("#hid1").val()).length<=0)*/

        ajax_lotProjectNO(chk_value.toString(),sid,allContent);
    })

    function ajax_lotProjectNO(selectids,sid,allContent) {
        var options = {
            url: "ajax_lotProjectNO?t=" + new Date().getMilliseconds() + "&ids=" + selectids +"&empids=" +sid +"&allContent=" +allContent ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("打回成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.location.reload();
                }else{
                    alert("打回失败");
                }

            }
        };
        $.ajax(options);
    }

    //单个审回上一级
    $("body").delegate(".projectnoOne", "click", function () {

        var sid = $("#hidid").val();
        if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }
        var prono=$(".projectnoOne").attr("data-value");
        var allContent=$("#allContent").val();
        ajax_lotProjectNOONE(prono,sid,allContent);
    })

    function ajax_lotProjectNOONE(selectids,sid,allContent) {
        var options = {
            url: "ajax_deliverNOONE?t=" + new Date().getMilliseconds() + "&prono=" + selectids +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("打回成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.location.reload();
                    window.close();
                }else{
                    alert("打回失败");
                }

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

        var sid = $("#hidid").val();
        if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }

        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }
        var allContent=$("#allContent").val();
        ajax_lotProjectYES(chk_value.toString(),sid,allContent);
    })

    function ajax_lotProjectYES(prono,sid,allContent) {
        var options = {
            url: "ajax_lotProjectYES?t=" + new Date().getMilliseconds() + "&ids=" + prono  + "&empids=" +sid +"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.location.reload();
                    window.close();
                }else{
                    alert("签核失败");
                }

            }
        };
        $.ajax(options);
    }

    //单个审批同意
    $("body").delegate(".projectokOne", "click", function () {
        var prono=$(".projectokOne").attr("data-value");
        var sid = $("#hidid").val();
        if(sid.length <= 0||sid==null){
            alert("请选择分管领导");
            return false;
        }
        /* ||( $("#hid1").val()==null||($("#hid1").val()).length<=0)*/
        var allContent=$("#allContent").val();
        ajax_lotProjectYESONE(prono,sid,allContent);
    })

    function ajax_lotProjectYESONE(prono,sid,allContent) {
        var options = {
            url: "ajax_deliverYESONE?t=" + new Date().getMilliseconds() + "&prono=" + prono + "&empids=" +sid+"&allContent=" +encodeURI(encodeURI(allContent)) ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核成功");
                    $(window.opener.document).find(".pagination li.active a")[0].click();
                    window.location.reload();
                    window.close();
                }else{
                    alert("签核失败");
                }

            }
        };
        $.ajax(options);
    }


     $("body").delegate(".leaders", "change", function () {
         var htmlname = "";
         var htmlid = "";
         htmlname =  $(".leaders option:selected").text();
         htmlid =  $(".leaders option:selected").val();
         var sname = $("#hidname").val(htmlname);
         var sid = $("#hidid").val(htmlid);
     })

   /* $("body").delegate("#allContent", "change", function () {
      var allContent=$("#allContent").val();
    })*/
})