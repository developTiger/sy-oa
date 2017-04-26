/**
 * Created by admin on 2016/9/3.
 */
define(function(require,exports,module){

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//使用dateFormat日期格式转换
    var List = require("../common/pagelist");
    require("mutiSelect");
    require("ajaxUpload");
    var html2canvas= require("html2canvas");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    //下拉多选插件
    require("select2");
    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        multiple: true

    });

    //多选下拉 初始化
    $(document).ready(function() {
        $('.example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '350px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();
    });

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_workAchievements_v").length>0) {
            target=$("#syy_workAchievements_v");
            var tpl = require("text!app/tpl/partyGroup/syy_dq_lc02_v_Tpl.html");
            form.render(target,tpl);
        }


        //多选下拉 初始化
        $(document).ready(function() {
            $('.example-dropUp_d').multiselect({
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


    }


    exports.pageLoad=function(){
        loadForm();

        var step = $("input[name='workAchiStep']").val();
        var isViewOnly = $("input[name='workAchiIsViewOnly']").val();
        var isComplete = $("input[name='workAchiIsComplete']").val();

        if(step == "4" && isViewOnly == 0 && isComplete == 0){
            $(".image_generate").removeClass("hidden");
        }

    };

    $("#appform").validate({

        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#workAchievementsSubmit").attr("data-url");

            var fileIds = [];
            $("input[name='fileName1']").each(function(){
                fileIds.push($(this).attr("id"));
            });

            // $("input[name='hiddenJoinPeople']").val($(".joinPeople").val().toString());
            $("input[name='hiddenType']").val($(".competitionType").val().toString());
            $("input[name='hiddenProjectName']").val($("#selectProName").find("option:selected").text());

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
                        alert("提交成功！");
                        window.close();
                    } else {
                        alert(result.msg);
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('新增失败，请重试！！');
                }
            });
        }
    })


    $(document).ready(function(){
        $("#applyPerson").val($("input[name='applyerName']").val());
    })

    $body.delegate("#selectProName","change",function(){
        var id = $(this).find("option:selected").val();
        $.ajax({
            url:"ajax_query_workProject"+"?t="+new Date()+"&id="+id,
            type:"get",
            success:function(data){
                if(data){

                    $("#leader").val(data.leader);
                    $("#competitionType").val(data.competitionType);

                    var joinPeople = data.joinPeople;
                    var projectType = data.competitionType;
                    var arrray_type = projectType.split(",");

                    // var arr_people = joinPeople.split(",");
                    // $(".select2_multiple").val(arr_people).trigger("change");//select2设置值
                    $(".Js_joinPeople").val(joinPeople)

                    $('.competitionType').multiselect('select', arrray_type);//项目申报类别 multiselect 设置值

                }
            }
        })
        if($(this).val() == ""){
            $("#leader").val("");

            $('.competitionType option:selected').each(function() {
                $(this).prop('selected', false);
            })
            $('.competitionType').multiselect('refresh');//multiselect 取消值

            $(".select2_multiple").val(null).trigger("change");//select2 取消值


        }
    })

    //审核 确定 按钮
    $("#approve").on("click", function () {

        $("input[name='hidJudgeMemberids']").val($("select[name='achiGroupMembersIds']").val());
        var members = [];
        $("select[name='achiGroupMembersIds']").find("option:selected").each(function(){
            members.push($(this).text());
        })
        $("input[name='hiddenJudgeMembers']").val(members.toString());//成员name

        $("input[name='hiddenMembersLeader']").val($("select[name='achi_leader_id']").find("option:selected").text());

    })



    // var loadData = function(){
    //     var url = "ajax_query_workersachievement";
    //     var tpl = require("text!app/tpl/partyGroup/syy_dq_lc02_v_count_Tpl.html");
    //     List("#table",tpl,url,"",1,10);
    // }
    //
    // loadData();

    $(document).ready(function(){
        var id = $("input[name='id']").val();
        if(id != ""){
            $("#selectProName").trigger("change");
        }
    })


    $(".image_generate").on("click", function(event) {
        //window.location.href="syy_dq_lc03_iframe_html";

        var prizeLevel = $("select[name='prizeLevel']").val();
        if(prizeLevel ==""){
            alert("请先选择荣誉级别！");
            return false;
        }


        var formNo = $("input[name='workAchiFormNo']").val();
        var url = "syy_dq_lc02_iframe_html?"+"formNo="+formNo+"&prizeLevel="+prizeLevel;
        window.open(encodeURI(encodeURI(url)));
        $(this).addClass("hidden")
    })


    //js 时间转换
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy年MM月";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };

})
