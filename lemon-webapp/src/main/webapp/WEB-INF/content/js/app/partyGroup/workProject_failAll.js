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


    //多选下拉 初始化
    $(document).ready(function() {
        $('#example-dropUp_1').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '250px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();
    });

    $(document).ready(function() {
        $('#example-dropUp_2').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '250px',

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
        var url = "ajax_query_failAll_count";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc01_a_failAll_tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);

        //多选下拉 初始化
        $(document).ready(function() {
            $('.example-dropUp').multiselect({
                includeSelectAllOption: true,
                maxHeight: 400,
                buttonWidth: '250px',

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
        var _url = "ajax_approve_singleOrAll";
        var formNo = $(this).next().next().val();
        var formKind = $(this).next().next().next().val();
        var content = $(this).parent().prev().children().val();
        var step = $("input[name='step']").val();

        var membersName = $(this).parent().prev().prev().prev().prev().find("select").val()
        var project_membersName="";
        var project_membersNameIds = "";
        var hiddenLeaderId = "";
        var members_leader = "";
        if(step == "2") {
            if (membersName != null || membersName != "") {
                project_membersNameIds = membersName.toString();//评审小组成员id
            } else {
                alert("请选择评审小组成员！");
                return false;
            }

            var memberNames = [];
            $(this).parent().prev().prev().prev().prev().find("option:selected").each(function () {
                memberNames.push($(this).text());
            })
            project_membersName = memberNames.toString()//评审小组成员name

            var leaderId = $(this).parent().prev().prev().prev().find("select").val().toString();
            hiddenLeaderId = leaderId.toString();//评审小组组长id
            members_leader = $(this).parent().prev().prev().prev().find("option:selected").text();//评审小组组长name
        }

        var project_judgeSuggestions = $(this).parent().prev().prev().find("textarea").val();//评审意见 不知道有没有


        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:1,isStepComplete:false,clStep:step,content:content,
                project_membersName:project_membersName,project_membersNameIds:project_membersNameIds,
                hiddenLeaderId:hiddenLeaderId,members_leader:members_leader,project_judgeSuggestions:project_judgeSuggestions},
            async:false,
            success: function (data) {
                if(data){
                    alert("审核通过");
                    loadData();
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
        var step = $("input[name='step']").val();
        if(formKinds=="" || formNos==""){
            alert("请选则项目申报流程！");
            return false;
        }

        if($("select[name='memberNames']").val() != null)
            var project_membersNameIds = $("select[name='memberNames']").val().toString();

        var memberName = [];
        $("select[name='memberNames']").find("option:selected").each(function(){
            memberName.push($(this).text());
        })
        var project_membersName = memberName.toString();


        if($("select[name='memberLeader']").val() != null)
            var hiddenLeaderId = $("select[name='memberLeader']").val().toString();
        var members_leader = $("select[name='memberLeader']").find("option:selected").text();

        var project_judgeSuggestions = $("textarea[name='project_membersName']").val();

        var options = {
            url: "ajax_workProject_approve_All?t=" + new Date().getMilliseconds(),
            type: 'post',
            data:{formNos:formNos,formKinds:formKinds,content:content,appValue:1,isStepComplete:false,clStep:step,
                project_membersNameIds:project_membersNameIds,project_membersName:project_membersName,
                hiddenLeaderId:hiddenLeaderId,members_leader:members_leader,
                project_judgeSuggestions:project_judgeSuggestions},
            success: function (data) {
                if (data.isSuccess) {
                    alert("审核成功");
                    loadData();
                    //$("#myModal").modal("hide");
                    window.location.reload();
                }
                else
                    alert("审核失败");
            }
        };
        $.ajax(options);
    }


    //T3专业评审委员会汇总审批节点 单个否决
    $("body").on("click",".rejectSingle", function () {
        var _url = "ajax_approve_rejectSingle";
        var formNo = $(this).next().val();
        var formKind = $(this).next().next().val();
        var content = $(this).parent().prev().children().val();
        var step = $("input[name='step']").val();
        $.ajax({
            url:_url,
            type:"post",
            dataType:"json",
            data:{formNo:formNo,formKind:formKind,appValue:3,isStepComplete:false,clStep:step,content:content},
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
            url: "ajax_workProject_reject_All?t=" + new Date().getMilliseconds(),
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
