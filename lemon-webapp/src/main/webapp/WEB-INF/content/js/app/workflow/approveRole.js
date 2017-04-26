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



    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#appRoleQueryBtn").click(function () {
        loadData();
    });

    function loadData() {

        var tpl = require("text!app/tpl/workflow/approveRoleTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
    var callb= function() {


        $("#addApproveRoleForm").validate({
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



                var _url = $("#addOrUpdateAppRole").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addApproveRoleForm").serializeObject(),
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


   /* $("#editApproveRoleForm").validate({
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
                    if (data == "success"){
                        alert("修改成功");
                        window.location.href="sra_fm_approveRole";
                    }
                    else{
                        alert("修改成功");
                    }
                }
            };
            $.ajax(options);
        }

    })*/


    $("body").delegate("#cancelModal","click",function(){
        $("#myModal").modal("hide")
    });

    /*$("body").delegate("#cancelModal1","click",function(){
        window.location.href="sra_fm_approveRole";
    });*/

    //id删除数据
    $("body").delegate("#deleteAppRoleId","click",function() {
        var _url = $("#deleteAppRoleId").attr("data-url");
        var options = {
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data== "success")
                    alert("删除成功");
                $("#myModal").modal("hide");
                loadData();
            }
        };
        $.ajax(options);
    });
});