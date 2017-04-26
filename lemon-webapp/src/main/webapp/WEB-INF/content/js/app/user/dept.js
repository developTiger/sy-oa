/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    var validate = require("validate");
    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#DeptQueryBtn").click(function () {
        loadData();
    });
    function loadData() {
        var tpl = require("text!app/tpl/uauth/deptTableTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
    }

    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });
    $("#deleteMenu").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.length>0){
            if(confirm("该操作对这些部门中可能存在的员工产生影响，您是否继续删除！")){
                setUserStaus(chk_value.toString());
            }
        }else{
            alert("请选择将要删除的部门！");
        }

    })
    function setUserStaus(selectids) {
        var options = {
            url: "ajax_deleteDept?t=" + new Date().getMilliseconds(),
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

    $("body").delegate("#deleteDeptItem", "click", function () {
        var deleteId=$(this).attr("data-title");
        if(confirm("该操作对这部门可能存在的人员产生影响，您是否继续删除！")){
            setUserStaus(deleteId);
        }
    });

        $("body").delegate("#dept-no", "blur", function () {
        if($(this).val() !=""){
            $("#dept-no").val($(this).val().toUpperCase())
        }
    });


    var vaLi = function validates() {
        jQuery.validator.addMethod("isNameFormat", function (value, element) {
            var departShortNameRegx = /[a-zA-Z\d]+/;
            return (departShortNameRegx.test(value));
        }, "数字或者字母");
        $("#addDeptForm").validate({
            rules: {
                deptName: {
                    required: true,
                    maxlength: 50
                },
                deptNo: {
                    required: true,
                    isNameFormat: $("#dept-no").val()
                },
                deptType: {
                    required: true
                },
                //parentDeptId:{
                //    required:true
                //},
                brief: {
                    required: true,
                    maxlength: 50
                }
            },
            messages: {
                deptName: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },

                deptNo: {
                    required: "必填",
                    isNameFormat: "请输入字母或者数字"
                },
                deptType: {
                    required: "必须选择"
                },
                //parentDeptId:{
                //    required:"必须选择"
                //},
                brief: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                }

            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateDept").attr("data-url");
                $("#hiddenId").val($("#chargeLeaderId").find("option:selected").text());
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addDeptForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            $("#myModal").modal("hide")
                            loadData();
                        } else {
                            alert(data.msg);
                        }
                    }
                };
                $.ajax(options);
            }
        })
    }
    widget.init(vaLi);
});