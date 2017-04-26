/**
 * Created by admin on 2016/9/3.
 */
define(function(require,exports,module){

    require("ajaxUpload");
    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//使用dateFormat日期格式转换
    require("mutiSelect");
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    widget.init();//摸态框初始化

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    loadData();
    $("#failAllQueryBtn").click(function () {
        loadData();
    })
    function loadData(){
        var url = "ajax_query_failAll_workersProposal";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc07_a_failAll_tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }

    $("h3").hide();
    $(".x_title").hide();

    check.checkAll("body", ".checkAll", ".checkBtn")


    //T3专业评审委员会汇总审批节点 单个审核
    $("body").on("click",".approveSingle", function () {
        var _url = "ajax_approve_singleOrAll_workersProposal";
        var formNo = $(this).next().next().val();
        var formKind = $(this).next().next().next().val();
        var clStep = $(".searchStep").val();
        var content = $(this).parent().prev().children().val();//意见
        //var approveScore = $(this).parent().prev().prev().children().val();//评分
        var undertakeDept_id =  $(this).parent().prev().prev().children("#undertakeDept").val();
        $(this).parent().prev().prev().children("input[name='hiddenDeptName']").val( $(this).parent().prev().prev().children("#undertakeDept").find("option:selected").text());
        var hiddenDeptName = $(this).parent().prev().prev().children("input[name='hiddenDeptName']").val();


        //if(approveScore == ""){
        //    alert("请填写评分！");
        //    return false;
        //}

        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:1,isStepComplete:false,clStep:clStep,content:content,
                undertakeDept_id:undertakeDept_id,hiddenDeptName:hiddenDeptName},
            async:false,
            success: function (data) {
                if(data.isSuccess){
                    alert("审核通过");
                    loadData();
                }else{
                    alert(data.msg);
                }
            }
        })
    })

    //T3专业评审委员会汇总审批节点 批量审核
    $(".allApprove").click(function () {
        var formNos = [];
        var formKinds= [];
        $('.checkBtn:checked').each(function () {
            formNos.push($(this).val());
            formKinds.push($(this).attr("data-value"));
        });

        approveAll(formNos.toString(),formKinds.toString());
    })

    function approveAll(formNos,formKinds) {
        var content =  $("#allContent").val();
        //var approveScore = $("#approveScore").val();
        var clStep = $(".searchStep").val();

        if(formKinds=="" || formNos==""){
            alert("请选择提案流程！");
            return false;
        }

        //if(approveScore == ""){
        //    alert("请填写提案评分！");
        //    return false;
        //}

        var deptId = $("select[name='adviseDeptmentId']").val();//建议承办部门id
        var deptName = $("select[name='adviseDeptmentId']").find("option:selected").text();//建议承办部门name



        var options = {
            url: "ajax_workersProposal_approve_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:1,isStepComplete:false,clStep:clStep,
                undertakeDept_id:deptId,hiddenDeptName:deptName},
            success: function (data) {
                if (data.isSuccess) {
                    alert("审核成功");
                    $(".modal").modal("hide");//摸态框 隐藏
                    loadData();
                }
                else
                    alert("审核失败");
            }
        };
        $.ajax(options);
    }


    //T3专业评审委员会汇总审批节点 单个否决
    $("body").on("click",".rejectSingle", function () {
        var _url = "ajax_approve_rejectSingle_workersProposal";
        var formNo = $(this).next().val();
        var formKind = $(this).next().next().val();
        var content = $(this).parent().prev().children().val();//意见

        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:3,isStepComplete:false,clStep:2,content:content,approveScore:approveScore},
            async:false,
            success: function (data) {
                if(data){
                    alert("操作通过");
                    loadData();
                }else{
                    alert("操作失败");
                }
            }
        })
    })

    //T3专业评审委员会汇总审批节点 批量否决
    $("#allReject").click(function () {
        var formNos = [];
        var formKinds= [];
        $('.checkBtn:checked').each(function () {
            formNos.push($(this).val());
            formKinds.push($(this).attr("data-value"));
        });
        rejectAll(formNos.toString(),formKinds.toString());
    })

    function rejectAll(formNos,formKinds) {
        var content =  $("#allContent").val();

        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        var options = {
            url: "ajax_workersProposal_reject_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:3,isStepComplete:false,clStep:2},
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");
                    loadData();
                }
                else
                    alert("操作失败");
            }
        };
        $.ajax(options);
    }


})
