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
    //widget.init();//摸态框初始化

    require("select2");
    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        // multiple: true,
        formatResult:function(row){
            console.log("formatResult")
        }
    });

    //多选下拉 初始化
    $(document).ready(function() {
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '500px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();
    });

    $(document).ready(function() {
        $('.example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '500px'
        });
    });

    //主要参加人 控件
    $(".Js_addPeople").on("click",function(){

        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc01_a_mainPeople_tpl.html");
        $.ajax({
            url:"ajax_partyGroup_lc01_mainPeople?"+"t="+new Date().getMilliseconds(),
            type:'post',
            success:function(data){
                if (data){
                    var html = template.compile(tpl)(data);
                    $(".Js_attendPeople").append(html);

                    $(".select2_multiple_tpl").select2({
                        width:'100%',
                        placeholder: "请选择",
                    });

                }
            }
        })

    })

    $("body").on("click",".Js_mainPeople_delete",function(){
        $(this).parent().parent().remove();
    })



    //弹窗
    var callba = function callback(){


        $("body").off("click","#updateCountDetail");
        $("body").on("click","#updateCountDetail", function () {
            var _url = $("#updateCountDetail").attr("data-url");
            var joinPeople = [];
            $("#example-dropUp").find("option:selected").each(function () {
                joinPeople.push($(this).val());
            })
            $("#hidJoinPeople").val(joinPeople);
            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#updateWorkProject").serializeObject(),
                success: function (data) {
                    if(data.isSuccess){
                        alert("修改成功！");
                        $("#myModal").modal("hide");
                        loadData();
                    }
                }

            };
            $.ajax(options);
        })
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '215px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();


    };
    widget.init(callba);//不能写成widget.init(callba());    不能进行方法调用




    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_workProject_v").length>0) {
            target=$("#syy_workProject_v");
            var tpl = require("text!app/tpl/partyGroup/syy_dq_lc01_v_Tpl.html");
            form.render(target,tpl);
        }

        $('.example-dropUp_1').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '500px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();

    }



    exports.pageLoad=function(){
        loadForm();

    };

    $("#appform").validate({

        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#workProjectSubmit").attr("data-url");

            var empName = [];
            $(".select2_multiple").find("option:selected").each(function(){
                empName.push($(this).text());
            });
            console.log(empName);

            //var competitionType = [];
            //$(".example-dropUp").find("option:selected").each(function () {
            //    competitionType.push($(this).val());
            //})
            //$("#competType").val(competitionType);


            var fileIds = [];
            $("input[name='fileName1']").each(function(){
                fileIds.push($(this).attr("id"));
            });

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
                    $('#result').html('提交失败，请重试！！');
                }
            });

        }

    })


    $(document).ready(function(){
        $("#leader").val($("input[name='applyerName']").val());
        $("#competitionUnit").val($("input[name='deptName']").val());
    })

    check.checkAll("body", ".checkAll", ".checkBtn");

    $("#approve").click(function(){
        $("input[name='project_membersName']").val($("select[name='members_names']").val().toString());

        var membersId = [];//专业评审小组id
        $("select[name='members_names']").find("option:selected").each(function(){
            membersId.push($(this).attr("data-value"));
        })
        $("input[name='project_membersNameIds']").val(membersId.toString());

        var membersLeaderId = [];//评审小组组长id
        $("select[name='members_leader']").find("option:selected").each(function(){
            membersLeaderId.push($(this).attr("data-value"));
        })
        $("input[name='hiddenLeaderId']").val(membersLeaderId.toString());

    })

    //统计
    var loadData = function(){
        var url = "ajax_query_count_workProject";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc01_v_count_Tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }
    loadData();
    $("#queryAllForm").click(function () {
        loadData();
    })

    $("#downloadCount").click(function(){
        $.post("ajax_workAndAchievement_count_down");
    })


})
