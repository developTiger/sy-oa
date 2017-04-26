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

    //多选下拉 初始化
    $(document).ready(function() {
        $('.example-dropUp_f').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '270px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();
    });

    loadData();
    $("#failAllQueryBtn").click(function () {
        loadData();
    })
    function loadData(){
        var url = "ajax_query_failAll_workAchievement";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc02_a_failAll_tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);

        //多选下拉 初始化
        $(document).ready(function() {
            $('.example-dropUp').multiselect({
                includeSelectAllOption: true,
                maxHeight: 400,
                buttonWidth: '200px',

                enableClickableOptGroups: true,
                enableCollapsibleOptGroups: true,
                enableFiltering: true
            });
            $(".input-group-addon").hide();
            $(".input-group-btn").hide();
        });

    }

    $("h3").hide();
    $(".x_title").hide();

    check.checkAll("body", ".checkAll", ".checkBtn")


    //T3专业评审委员会汇总审批节点 单个审核
    $("body").on("click",".approveSingle", function () {
        var _url = "ajax_approve_singleOrAll_workAch";

        var step = $("input[name='step']").val();

        var achi_members = "";
        var hiddenJudgeMembers = "";
        var achi_member_ids = [];
        var leaderId = "";
        var leaderName = "";
        if(step=="2") {
            achi_members = $(this).parent().prevAll().slice(8,9).find("select").val()
            if (achi_members != null) {
                hiddenJudgeMembers = achi_members.toString();//评审小组成员id
            }
            else {
                alert("请选择评审小组成员！");
                return false;
            }



            $(this).parent().prevAll().slice(8, 9).find("option:selected").each(function () {
                achi_member_ids.push($(this).text());//评审小组成员name
            })

            leaderId = $(this).parent().prevAll().slice(7, 8).find("select").val();//组长id
            var leaderName = $(this).parent().prevAll().slice(7, 8).find("option:selected").text();//组长name
        }

        var formNo = $(this).next().next().val();
        var formKind = $(this).next().next().next().val();
        var content = $(this).parent().prev().children().val();
        var step = $("input[name='step']").val();
        var approveScore = $(this).parent().prevAll().slice(6,7).find("input").val();//成果评分
        var achievementJudgeSuggestions = $(this).parent().prevAll().slice(5,6).find("textarea").val();//评审意见

        var awardAgency = $(this).parent().prevAll().slice(1,2).find("input").val();//颁发机构
        var achiGetTime = $(this).parent().prevAll().slice(2,3).find("input").val();//获得时间
        var prizeName = $(this).parent().prevAll().slice(3,4).find("input").val();//荣誉名称
        var prizeLeval = $(this).parent().prevAll().slice(4,5).find("select").val();//荣誉级别

        if(approveScore == "" && step==3){
            alert("请填写成果评分！");
            return false;
        }


        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:1,isStepComplete:false,clStep:step,content:content,approveScore:approveScore,
                hidJudgeMemberids:hiddenJudgeMembers,achievementJudgeSuggestions:achievementJudgeSuggestions,awardAgency:awardAgency,
                achiGetTime:achiGetTime,prizeName:prizeName,prizeLeval:prizeLeval,
                hiddenJudgeMembers:achi_member_ids.toString(),achi_leader_id:leaderId,hiddenMembersLeader:leaderName},
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
        var content = $("#allContent").val();
        var step = $("input[name='step']").val();
        //var prizeLeval = $("#prizeLeval").find("option:selected").val();
        var approveScore = $("#approveScore").val();
        var suggestion = $("#all_achievementJudgeSuggestions").val();

        var hidJudgeMemberids = "";//评审小组成员id
        var memberNames = [];
        var achi_leader_id = "";
        var hiddenMembersLeader = "";
        if (step == "2") {
            hidJudgeMemberids = $("#groupMember").val();
            if (hidJudgeMemberids == null || hidJudgeMemberids == "") {
                alert("请选择评审小组成员！");
                return false;
            }

            $("#groupMember").find("option:selected").each(function () {
                memberNames.push($(this).text());//评审小组成员name
            })

            achi_leader_id = $("#groupLeader").val();
            hiddenMembersLeader = $("#groupLeader").find("option:selected").text();
        }


        var prizeLevel = $("#all_prizeLeval").val();//荣誉级别
        var prizeName = $("#all_prizeName").val();//荣誉名称
        var achiGetTime = $("#all_achiGetTime").val();//获得时间
        var awardAgency = $("#all_awardAgency").val();//颁发机构

        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        if(approveScore == "" && step==3){
            alert("请填写成果评分！");
            return false;
        }

        var options = {
            url: "ajax_workAchievement_approve_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:1,isStepComplete:false,clStep:step,achievementJudgeSuggestions:suggestion,
                approveScore:approveScore,hidJudgeMemberids:hidJudgeMemberids.toString(),hiddenJudgeMembers:memberNames.toString(),achi_leader_id:achi_leader_id,hiddenMembersLeader:hiddenMembersLeader,
                prizeLevel:prizeLevel,prizeName:prizeName,achiGetTime:achiGetTime,awardAgency:awardAgency},
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
        var _url = "ajax_approve_rejectSingle_workAchi";
        var formNo = $(this).next().val();
        var formKind = $(this).next().next().val();
        var content = $(this).parent().prev().children().val();
        var step = $("input[name='step']").val();
        var approveScore = $(this).parent().prev().prev().prev().children().val();
        var prizeLeval = $(this).parent().prev().prev().children().find("option:selected").val();
        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:3,isStepComplete:false,clStep:step,content:content,approveScore:approveScore,prizeLeval:prizeLeval},
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
        var step = $("input[name='step']").val();

        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        var options = {
            url: "ajax_workAchievement_reject_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:3,isStepComplete:false,clStep:step},
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
