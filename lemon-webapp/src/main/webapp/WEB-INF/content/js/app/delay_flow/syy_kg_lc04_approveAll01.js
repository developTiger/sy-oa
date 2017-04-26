/**
 * Created by swb on 2016/12/21.
 */
define(function (require, exports, module) {

    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");
    require("mutiSelect");
    widget.init();
    require("chained");

    exports.pageLoad = function () {
        $('#leaders').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '264px'
        });
    };

    loadData();
    $("#formQueryBtn").click(function () {
        loadData();
    });
    function loadData() {

        var tpl = require("text!app/tpl/delay/formChiefTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
    $("body").delegate("#showHeader","click",function(){
        //var url=$("#formHeaderUrl").val();
        var formnumber=$("#formnumber").val();
        var formkind=$("#formkind").val();
        /*var html="";
        html+='<font color="aqua">延期项目：</font><br/>'+
            '<font color="aqua">延期理由：</font><br/>'+
            '<font color="aqua">延期时间：</font><br/>'+
            '<font color="aqua">申请人：</font><br/>'+
            '<font color="aqua">所属部门：</font><br/>';*/

        $.ajax({
            url:'syy_show_delay_project?formnumber='+formnumber+"&formkind="+formkind,
            type:'post',
            success:function(data){
                alert("申请人：\t"+data.applyerName+"\n延迟原因：\t"+data.delayReason+"\n延迟时间：\t"+data.delayTime+"\n延迟项目名称：\t"+data.delayNamesel+"\n所属部门：\t"+data.deptName);

            }
        });
    });
//同意
    $("body").delegate("#approveChiefBtn","click",function(){
        var formnumber=$(".f_number").val();
        var formkind=$(".f_kind").val();
        var chk=$(".chk");
        var chk_str="";
        chk.each(function(){
            var str=$(this).closest("div").attr("class");
            if(str.indexOf("checked")>0){
                chk_str+=$(this).val()+"@";
            }
        });
        chk_str=chk_str.substring(0,chk_str.length-1);

        var chkAll=chk_str.split("@");


        for(var i=0;i<chkAll.length;i++){
            var chk=chkAll[i].split(",");
            $.ajax({
                url:'syy_kg_lc04_v2All01_approve?formNo='+chk[0]+"&formKind="+chk[1]+"&appValue="+1,
                type:'post',
                success:function(data){
                    if(data){
                        alert("审批成功！");
                        window.history.back();
                        window.close();
                    }
                }
            });

        }


    });
    //退回
    $("body").delegate("#goBackChief","click",function(){
        var formnumber=$(".f_number").val();
        var formkind=$(".f_kind").val();
        var chk=$(".chk");
        var chk_str="";
        chk.each(function(){
            var str=$(this).closest("div").attr("class");
            if(str.indexOf("checked")>0){
                chk_str+=$(this).val()+"@";
            }
        });
        chk_str=chk_str.substring(0,chk_str.length-1);

        var chkAll=chk_str.split("@");


        for(var i=0;i<chkAll.length;i++){
            var chk=chkAll[i].split(",");
            $.ajax({
                url:'syy_kg_lc04_v2All01_approve?formNo='+chk[0]+"&formKind="+chk[1]+"&appValue="+3,
                type:'post',
                success:function(data){
                    if(data){
                        alert("审批（退回）成功！");
                        window.history.back();
                        window.close();
                    }
                }
            });

        }


    });

    $("body").delegate("#addChief","click",function(){
        var sel_str="";
        var formNo=$("#formNo").val();
        alert(formNo);
        var sel=document.getElementsByName("leader");
        for(var i=0;i<sel.length;i++){
            if(sel[i].checked){
                sel_str+=sel[i].value+",";
            }
        }
        sel_str=sel_str.substr(0,sel_str.length-1);
        $.ajax({
            url:'ajax_get_leaderIdValue?sel_str='+sel_str+'&formNo='+formNo,
            type:'post',
            success:function(data){
                alert("添加审核领导成功！");
            }
        });
    });

});