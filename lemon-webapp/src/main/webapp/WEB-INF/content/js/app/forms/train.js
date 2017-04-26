/**
 * Created by zhouz on 2016/7/18.
 */
define(function (require, exports, module) {

    require("jquery");
    require("mutiSelect");
    require("ajaxUpload");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//模板加载
    List = require("../common/pagelist");//分页
    //widget.init();

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    $("body").delegate(".js_FromTime","blur",function(){
        studyTime();
    });
    $("body").delegate(".js_ToTime","blur",function(){
        studyTime();
    });

    function studyTime(){
        var toTime=$(".js_ToTime").val();
        var fromTime=$(".js_FromTime").val();
        if(toTime=="" || fromTime==""){
            return ;
        }
        var day=computeDaysDelta(fromTime,toTime);
        $("input[name=studyTime]").val(day*8);
    }

    function convertStringToDate(dateString)
    {
        dateString = dateString.split('-');
        return new Date(dateString[0], dateString[1] - 1, dateString[2]);
    }


    function computeDaysDelta(date1, date2)
    {
        date1 = convertStringToDate(date1);
        date2 = convertStringToDate(date2);
        delta = (date2 - date1) / (1000 * 60 * 60 * 24) + 1;

        var weekEnds = 0;
        for(i = 0; i < delta; i++)
        {
            if(date1.getDay() == 0 || date1.getDay() == 6) weekEnds ++;
            date1 = date1.valueOf();
            date1 += 1000 * 60 * 60 * 24;
            date1 = new Date(date1);
        }
        return delta - weekEnds;
    }
    //多选下拉
    $(document).ready(function () {
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '350px'
            //dropRight:true
        });
        //if ($("#hdeptId").val() != "") {
        //    var arrDeptid = $("#hdeptId").val().split(",");
        //    $('#example-dropUp').multiselect('select', arrDeptid);
        //}
    });

    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");

    /*$('#menu-name').multiselect({
     includeSelectAllOption: true,
     maxHeight: 400,
     buttonWidth: '264px'
     });*/


    //审核页面加载
    var form = require("../common/form")


    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    form.init(function () {

        form.doSubmit(); //此处自定义验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    //渲染页面
    var renderForm = function () {
        var target;
        var tpl;
        if ($("#syy_train_v_01").length > 0) {
            target = $("#syy_train_v_01");
            tpl = require("text!app/tpl/forms/syy_rs_lc01_01_v_Tpl.html");//部门 子表单

        }
        //加载主表单数据
        if ($("#syy_train_v").length > 0) {
            target = $("#syy_train_v");
            var tpl = require("text!app/tpl/forms/syy_rs_lc01_vTpl.html");//人事 主表单
        }
        form.render(target, tpl);

    }


    //审核 添加员工 子表单
    function loadData11() {
        var tpl = require("text!app/tpl/forms/syy_rs_lc01_01_addEmps_tpl.html");
        var url = "ajax_query_addEmps";
        var name = $("#empName").val();
        var deptId = $("#empByDeptId").val();
        List("#table", tpl, url, "empName=" + (encodeURI(name)) + "&deptId=" + deptId, 1, 10);
        $(".modal-content").addClass("modal-nomal-width");//加载样式（去公共css里面设置）
    }

    //审核 添加员工 主表单
    function loadData12() {
        var tpl = require("text!app/tpl/forms/syy_rs_lc01_addEmps_tpl.html");
        var url = "ajax_query_addManyEmps";
        var name = $("#empName").val();
        var deptId = $("#empByDeptId").val();
        List("#table", tpl, url, "empName=" + (encodeURI(name)) + "&deptIds=" + deptId, 1, 10);
        $(".modal-content").addClass("modal-nomal-width");//加载样式（去公共css里面设置）
    }


    var formKind = $("#formKind").val();


    /**
     * emp弹窗提交事件
     * callback回调函数在弹窗出现时触发
     */
    var callb = function callback() {
        var that = this;
        /*$("body").undelegate("#addOrUpdateEmps", "click")
         $("body").delegate("#addOrUpdateEmps", "click", function () {

         //表单提交句柄,为一回调函数，带一个参数：form
         var emps = [];
         var deptNames = [];
         $(".checkBtn").each(function () {
         if ($(this).prop("checked")) {
         emps.push($(this).val());
         deptNames.push($(this).attr("data-value"));
         }
         })

         var _url = $("#addOrUpdateEmps").attr("data-url");
         var emp_formKind = $("#emp_formKind").val();

         $.ajax({
         url: _url + "?t=" + new Date().getMilliseconds() + "&emps=" + emps.toString() + "&deptNames=" + deptNames.toString() + "&formNo=" + $("#formNo_01").val() + "&formKind=" + emp_formKind,
         type: 'post',
         //data: $("#editEmps").serializeObject(),
         success: function (data) {

         if (data.isSuccess) {
         alert("新增员工成功");

         $("#myModal").modal("hide");
         window.close();
         renderForm();
         }
         }
         });
         });*/


        if (formKind == "SYY_RS_LC01_01") {
            loadData11();
            $("#queryEmps").click(function () {
                loadData11();
            });
        } else {
            loadData12();
            $("#queryEmps").click(function () {
                loadData12();
            });
        }

        $("body").off("click", ".addSingleEmp");
        $("body").on("click", ".addSingleEmp", function () {
            var emps = $(this).val();

            var _url = $(this).attr("data-url");
            var emp_formKind = $("#formKind").val();

            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds() + "&emps=" + emps.toString() + "&formNo=" + $("#formNo_01").val() + "&formKind=" + emp_formKind,
                type: 'post',
                //data: $("#editEmps").serializeObject(),
                success: function (data) {

                    if (data.isSuccess) {
                        alert("新增员工成功");

                        //$("#myModal").modal("hide");

                        renderForm();
                    } else {
                        alert("员工已存在，请勿重复添加");
                    }
                }
            });
        });


        //弹窗 checkbox样式
        check.niceCheck("input");

        //弹窗 返回值 checkbox选中
        var empId = [];
        $(".empListId").each(function () {
            empId.push($(this).val())//push（） 向数组添加元素
        })

        $(".beanId").each(function () {
            console.log($(this).val())//console.log 页面打印
            if (empId.indexOf($(this).val()) >= 0) {
                $(this).iCheck('check');//iCheck 系统设置的checkbox样式
            }
        })


    }

    exports.pageLoad = function () {
        renderForm();

        var formTrainIsComplete = $("#formTrainIsComplete").val();
        var clStep = $("#subFormIsStep").val();
        if (formTrainIsComplete == "false" && clStep == 3) {
            $("#goBack").hide();
            $("#approve").hide();
            $("small:first").html("(等待审批结果……)")
        }

        widget.init(callb);//加载回调函数 回调函数初始化（弹窗里面的所有事件加载都要写在回调函数里）
    };
    //$("body").on("click","#queryEmps", function () {
    //    var _url = "_get_emp_by_dept";
    //
    //    $.ajax({
    //        url:_url+"?t="+new Date().getMilliseconds()+"&name="+encodeURI(encodeURI($("#empName").val()))+"&formNo=" + $("#formNo_01").val()+("&deptId="+$("#emp_deptId").val()),
    //        type:"get",
    //        success: function (data) {
    //            if(data){
    //                renderForm();
    //            }
    //        }
    //
    //    })
    //})


    //当流程步骤为第5步时，隐藏退回按钮，只能选择同意，整个流程全部跑完
    $(document).ready(function () {

    })


    //页面 emp删除
    $("body").delegate(".deleteEmpInfo", "click", function () {

        var _url = $(this).attr("data-url");
        var formKind = $("#formKind").val();
        var empId = ($(this).parent().parent().parent().parent().parent().attr("id"));
        var options = {

            url: _url + "&t=" + new Date().getMilliseconds() + "&formNo=" + $("#formNo").val() + "&formKind=" + formKind,
            type: 'get',
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功");

                    renderForm();
                }
                else
                    alert(data);
            }
        };
        $.ajax(options);
    })

    //培训申请单 提交按钮触发事件
    $("#appform").validate({
        rules: {
            trainName: {
                required: true,
                maxlength: 50
            },

            strFromTime: {
                required: true
                //digits:true
            },
            strToTime: {
                required: true
            },
            option: {
                required: true
            },
            trainPlace: {
                required: true
            }
        },
        messages: {
            trainName: {
                required: "必填",
                maxlength: "长度小于50个字符"
            },

            strFromTime: {
                required: "必填"
            },
            strToTime: {
                required: "必填"
            },
            option: {
                required: "必须选择"
            },
            trainPlace: {
                required: "必填"
            }

        },

        //submitHandler: function (form) {
        //    //表单提交句柄,为一回调函数，带一个参数：form
        //    var _url = $("#trainSubmit").attr("data-url");
        //
        //    var d1 = $("#strFromTime").val();//开始时间
        //    var d2 = $("#strToTime").val();//结束时间
        //    var timestamp1 = Date.parse(new Date(d1));
        //    var timestamp2 = Date.parse(new Date(d2));
        //
        //    $("#hdeptId").val($("#example-dropUp").val().toString());
        //    $("#deptName").val($(".multiselect").attr("title"));
        //
        //    if (timestamp1 > timestamp2) {
        //        alert("开始时间不能小于结束时间！");
        //        return;
        //    }
        //    $.ajax({
        //        url: _url + "?t=" + new Date().getMilliseconds(),
        //        type: 'post',
        //        data: $("#appform").serializeObject(),
        //        success: function (data) {
        //
        //            if (data.isSuccess) {
        //                alert("新增成功");
        //                $("#myModal").modal("hide");
        //                window.close();
        //                renderForm();
        //            }
        //            else
        //                alert(data.msg);
        //        }
        //    });
        //}

        submitHandler: function (form) {

            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#trainSubmit").attr("data-url");

            var d1 = $("#strFromTime").val();//开始时间
            var d2 = $("#strToTime").val();//结束时间
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));

            $("#hdeptId").val($("#example-dropUp").val().toString());
            $("#deptName").val($(".multiselect").attr("title"));

            if (timestamp1 > timestamp2) {
                alert("开始时间不能小于结束时间！");
                return;
            }
            var fileIds = [];
            $("input[name='fileName1']").each(function () {
                fileIds.push($(this).attr("id"));
            });
            $.ajaxFileUpload({

                url: _url+"?t="+new Date().getMilliseconds(),
                secureuri: false,
                fileElementId: fileIds,
                dataType: 'text',
                data: $("#appform").serializeObject(),
                success: function (data, status) {

                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("新增成功！");
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
});