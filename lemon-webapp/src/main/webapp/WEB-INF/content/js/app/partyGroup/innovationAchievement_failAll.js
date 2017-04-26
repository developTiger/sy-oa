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
        var url = "ajax_query_failAll_innoAchievement";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc03_a_failAll_tpl.html");
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

    /*    //审核页面加载
     var form = require("../common/form")

     //初始化 添加验证 及 做提交
     //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

     form.init(function () {

     form.doSubmit(); //此处自定义验证逻辑 ，最后调用 form.doSubmit 提交签核
     })*/

    //T3专业评审委员会汇总审批节点 单个审核
    $("body").on("click",".approveSingle", function () {
        var _url = "ajax_approve_singleOrAll_innoAchi";
        var formNo = $(this).next().next().val();
        var formKind = $(this).next().next().next().val();
        var content = $(this).parent().prev().children().val();
        var clStep = $(".searchStep").val();




        var groupMemberIds = "";
        var groupMemberNames = [];//组员name
        var groupLeaderId = "";
        var groupLeaderName = "";
        if(clStep=="2") {
            groupMemberIds = $(this).parent().prevAll().slice(6, 7).find("select").val();//组员id
            if (groupMemberIds == null || groupMemberIds == "") {
                alert("请选择成果评审小组成员！");
                return false
            }
            $(this).parent().prevAll().slice(6, 7).find("option:selected").each(function () {
                groupMemberNames.push($(this).text());
            })

            groupLeaderId = $(this).parent().prevAll().slice(5, 6).find("select").val();
            groupLeaderName = $(this).parent().prevAll().slice(5, 6).find("option:selected").text();
        }

        var approveScore = "";
        var suggestion = "";
        if(clStep=="3" ) {
            approveScore = $(this).parent().prev().prev().prev().prev().children().val();//成果评分
            suggestion = $(this).parent().prevAll().slice(3, 4).find("textarea").val();//评审意见
        }

        var prizeLeval = "";
        var awardDeptId = "";
        var awardDeptName = "";
        if(clStep=="4" ) {
            prizeLeval = $(this).parent().prev().prev().prev().children().find("option:selected").val();//获奖等级
            awardDeptId = $(this).parent().prevAll().slice(1, 2).find("select").val();//颁发单位id
            awardDeptName = $(this).parent().prevAll().slice(1, 2).find("option:selected").text();//颁发单位name
        }



        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:1,isStepComplete:false,clStep:clStep,content:content,approveScore:approveScore,prizeLeval:prizeLeval,
                hidGroupMemberIds:groupMemberIds.toString(),hidGroupMemberNames:groupMemberNames.toString(),groupLeader:groupLeaderId,hidGroupLeaderName:groupLeaderName,
                achiSuggestion:suggestion,awardDeptId:awardDeptId,awardDeptName:awardDeptName},
            async:false,
            success: function (data) {
                if(data){
                    alert("审核通过");
                    loadData();
                    $(".modal").modal("hide");
                }else{
                    alert("审核失败");
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
        var clStep = $(".searchStep").val();



        var groupMemberId = "";
        var groupMemberNames = [];//组员name
        var groupLeaderId = "";
        var groupLeaderName = "";
        if(clStep=="2") {
            var groupMemberId = $("#groupMember").val();//组员id
            if (groupMemberId == "" || groupMemberId == null) {
                alert("请选择成果评审小组成员！");
                return false;
            }

            $("#groupMember").find("option:selected").each(function () {
                groupMemberNames.push($(this).text());
            })

            groupLeaderId = $("#groupLeader").val()//组长id
            if (groupLeaderId == "" || groupLeaderId == null && clStep == "2") {
                alert("请选择成果评审小组组长！");
                return false;
            }
            groupLeaderName = $("#groupLeader").find("option:selected").text();
        }


        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        var approveScore = "";
        var suggestion = "";
        if(clStep == "3"){
            approveScore = $("#approveScore").val();//成果评分
            if(approveScore == "" ){
                alert("请填写成果评分！");
                return false;
            }
            suggestion = $("#suggestion").val();//评审意见
        }

        var prizeLeval = "";
        var awardDeptId = "";
        var awardDeptName = "";
        if(clStep == "4"){
            prizeLeval = $("#prizeLeval").val();//获奖等级
            awardDeptId = $("#awardDeptId").val();//颁发部门id
            awardDeptName = $("#awardDeptId").find("option:selected").text();
        }

        var options = {
            url: "ajax_innoAchievement_approve_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:1,isStepComplete:false, clStep:clStep,approveScore:approveScore,prizeLeval:prizeLeval,
                hidGroupMemberIds:groupMemberId.toString(),hidGroupMemberNames:groupMemberNames.toString(),groupLeader:groupLeaderId,hidGroupLeaderName:groupLeaderName,
                achiSuggestion:suggestion,awardDeptId:awardDeptId,awardDeptName:awardDeptName},
            success: function (data) {
                if (data.isSuccess) {
                    alert("审核成功");
                    loadData();
                    $(".modal").modal("hide");
                }
                else
                    alert("审核失败");
            }
        };
        $.ajax(options);
    }


    //T3专业评审委员会汇总审批节点 单个否决
    $("body").on("click",".rejectSingle", function () {
        var _url = "ajax_approve_rejectSingle_innoAchi";
        var formNo = $(this).next().val();
        var formKind = $(this).next().next().val();
        var content = $(this).parent().prev().children().val();
        var clStep = $(".searchStep").val();
        var approveScore = $(this).parent().prev().prev().prev().children().val();
        var prizeLeval = $(this).parent().prev().prev().children().find("option:selected").val();
        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:3,isStepComplete:false,clStep:clStep,
                content:content,approveScore:approveScore,prizeLeval:prizeLeval},
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
        var clStep = $(".searchStep").val();

        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        var options = {
            url: "ajax_innoAchi_reject_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:3,isStepComplete:false,clStep:clStep},
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
