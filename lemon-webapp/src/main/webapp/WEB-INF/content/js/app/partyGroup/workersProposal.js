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
    var check = require("../common/checkbox");
    require("mutiSelect");

    check.checkAll("body", ".checkAll", ".checkBtn")

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    //下拉多选插件
    require("select2");
    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        // multiple: true

    });

    //多选下拉 初始化
    $(document).ready(function() {
        $('#example-dropUp').multiselect({
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

    //主要参加人 控件
    $(".Js_addPeople").on("click",function(){

        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc07_a_mainPeople_tpl.html");
        $.ajax({
            url:"ajax_partyGroup_lc07_mainPeople?"+"t="+new Date().getMilliseconds(),
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

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_workersProposal_v").length>0) {
            target=$("#syy_workersProposal_v");
            var tpl = require("text!app/tpl/partyGroup/syy_dq_lc07_v_Tpl.html");
            form.render(target,tpl);
        }
    }


    exports.pageLoad=function(){
        loadForm();
    };

    $("#appform").validate({

        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#workProposalSubmit").attr("data-url");
            // var people = [];
            // $(".select2_multiple").find("option:selected").each(function () {
            //     people.push($(this).val());
            // })
            // $("#hiddenPerson").val(people);
            //$("input[name='hiddenDeptName']").val($("#undertakeDept").find("option:selected").text());
            var people = [];
            $(".Js_multiPeople").each(function(){
                people.push($(this).val());
            })
            $("input[name='hidProposalPerson']").val(people.toString());
            $.ajax({
                url:_url+"?t="+new Date().getMilliseconds(),
                type:"post",
                dataType:"text",
                data:$("#appform").serializeObject(),
                success:function(data){
                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("提交成功！");
                        window.close();
                    } else {
                        alert(result.msg);
                    }
                }
            })
        }
    })


    $(document).ready(function(){
        $("#applyUnit").val($("input[name='deptName']").val());
    })

    $("#approve").click(function(){
        $("input[name='hiddenDeptName']").val($("#undertakeDept").find("option:selected").text());


    })

    $("#queryAllForm").click(function () {
        loadData();
    })

    var loadData = function(){
        var url = "ajax_query_workerProposal";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc07_v_count_Tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }

    loadData();





})
