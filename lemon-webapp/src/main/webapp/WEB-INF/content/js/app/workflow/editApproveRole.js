/**
 * Created by swb on 2016/6/22.
 */
/**
 * Created by swb on 2016/6/21.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");
    require("select2");

    require("mutiSelect");
    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#appRoleQueryBtn").click(function () {
        loadData();
    });

    function loadData() {

        var tpl = require("text!app/tpl/workflow/empTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }




    $("#editApproveRoleForm").validate({
        rules: {
            roleName: {
                required: true
            }

        },
        messages: {
            roleName: {
                required: "必填"
            }
        },
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#editOrUpdateAppRole").attr("data-url");
            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#editApproveRoleForm").serializeObject(),
                success: function (data) {
                    data= eval("("+data+")");
                    if (data.isSuccess){
                        alert("修改成功");
                        window.location.href="sra_fm_approveRole";
                    }
                    else{
                        alert(data.msg);
                    }
                }
            };
            $.ajax(options);
        }

    })



    $("body").delegate(".deleteEmpBtn","click",function(){
        var that = $(this);
        var url = that.attr("data-url")
        $.ajax({url: url + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            success: function (data) {
            var result= eval("("+data+")");
            if (result.isSuccess) {
                alert("删除成功");
                $("#myModal").modal("hide")
                loadData();
            }
            else{
                alert(result.msg)
            }
        }
        });
    });


    $("body").delegate("#cancelModal1","click",function(){
        /*$("#myModal").modal("hide");*/
        window.location.href="sra_fm_approveRole";
    });

    $("body").delegate("#cancelModal","click",function(){
        $("#myModal").modal("hide");
    });

    var callb= function() {
        $('.JS_emp_select').multiselect({
            includeSelectAllOption: true,
            maxHeight: 250,
            buttonWidth: '250px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".select2_single").select2({placeholder:"输入人名查询"  });
        $.fn.modal.Constructor.prototype.enforceFocus = function() {};



        $("#empRoleForm").validate({
            rules: {
                roleName: {
                    required: true
                }

            },
            messages: {
                roleName: {
                    required: "必填"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form


                $("#empIds").val($(".JS_emp_select").val().toString());
                var _url = $("#addOrUpdateEmpRole").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#empRoleForm").serializeObject(),
                    success: function (data) {
                        var result= eval("("+data+")");
                        if (result.isSuccess) {
                            alert("新增成功");
                            $("#myModal").modal("hide")
                            loadData();
                        }
                        else{
                            alert(result.msg)
                        }
                    }
                };
                $.ajax(options);
            }

        })


    }
    widget.init(callb);
});