/**
 * Created by pxj on 2016/12/15.
 */

define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    var validate = require("validate");
    check.checkAll("body", ".checkAll", ".checkBtn");
    require("select2");
    loadData();
    $("#DeptQueryBtn").click(function () {
        loadData();
    });
    function loadData() {
        var tpl = require("text!app/tpl/typeOfTreatise/typeOfTreatiseTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
    }

    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide");
    });
    $("#deleteMenu").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        deleteTypeOfTreatise(chk_value.toString());
    })
    function deleteTypeOfTreatise(selectids) {
        if (confirm("确认执行删除操作吗？")) {
            var options = {
                url: "ajax_deleteTypeOfTreatise?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: {ids: selectids},
                success: function (data) {
                    if (data.isSuccess) {
                        alert("删除成功");
                        loadData();
                    }
                    else
                        alert(data);
                }
            };
            $.ajax(options);
        }
        else {
            loadData();
        }
    }
    $("body").delegate("#deleteTypeOfTreatise", "click", function () {
        deleteTypeOfTreatise($(this).attr("data-title"));
    });



    $("body").delegate("#dept-no", "blur", function () {
        if($(this).val() !=""){
            $("#dept-no").val($(this).val().toUpperCase());
        }
    });

    $(".select2_group").select2({
        width:'100%'
    });
    var vaLi = function validates() {
 /*       jQuery.validator.addMethod("isNameFormat", function (value, element) {
            var departShortNameRegx = /[a-zA-Z\d]+/;
            return (departShortNameRegx.test(value));
        }, "数字或者字母");*/
        $("#addTypeTreatiseForm").validate({
            rules: {
                deptName: {
                    required: true,
                    maxlength: 50
                },
                Type_Treatise_Name: {
                    required: true/*,
                    isNameFormat: $("#dept-no").val()*/
                }/*,
                deptType: {
                    required: true
                },*/
                //parentDeptId:{
                //    required:true
                //},
          /*      brief: {
                    required: true,
                    maxlength: 50
                }*/
            },
            messages: {
                deptName: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },

                Type_Treatise_Name: {
                    required: "必填"/*,
                    isNameFormat: "请输入字母或者数字"*/
                }/*,
                deptType: {
                    required: "必须选择"
                },*/
                //parentDeptId:{
                //    required:"必须选择"
                //},
            /*    brief: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                }*/

            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdate").attr("data-url");
                $("#dept_Name").val($("#dept_Id").find("option:selected").text());
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addTypeTreatiseForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            if(data.id==1){
                                alert("修改成功");
                            }else{
                                alert("新增成功");
                            }
                            $("#myModal").modal("hide");
                            loadData();
                        } else {
                            alert(data.msg);
                        }
                    }
                };
                if($("#dept_Id").find("option:selected").text()=="请选择"){
                    alert("请选择部门");
                }else{
                    $.ajax(options);
                }
            }
        });
    }
    widget.init(vaLi);
});