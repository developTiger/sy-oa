/**
 * Created by swb on 2016/6/24.
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
    $("#formAppListQueryBtn").click(function(){
        loadData();
    });
    function loadData() {

        var tpl = require("text!app/tpl/workflow/formApproveListTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }

    $("body").delegate("#appUserId","change",function(){
        $("#appName").val($("#appUserId").find("option:selected").text());

    });


    $("body").delegate("#byDept", "change",function(){
        if($(this).val()=="true"){
            $(".chooseDept").removeClass("hidden");
        }
        else{
            $(".chooseDept").addClass("hidden");
        }
    })

    var callb= function() {
        $("#addFormApproveListForm").validate({
            rules: {
                appName: {
                    required: true
                }

            },
            messages: {
                appName: {
                    required: "必填"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form



                var _url = $("#addOrUpdateFormAppList").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addFormApproveListForm").serializeObject(),
                    success: function (data) {
                        if (data == "success")
                            alert("操作成功");
                        $("#myModal").modal("hide")
                        loadData();
                    }
                };
                $.ajax(options);
            }

        })


    }
    widget.init(callb);

    $("body").delegate("#cancelModal","click",function(){
        $("#myModal").modal("hide");
    });

    //删除数据

    $("body").delegate(".delAppListBtn","click",function(){
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

    /*$("body").delegate(".editAppListBtn","click",function(){
        var _url = $(this).attr("data-url");
        var fd1 = $("#hiddenFd1").val();
        $.ajax({
            url:_url+"&fd1="+fd1,
            type:"post",
            success:function(data){
                if(data){

                }
            }
        })
    })*/

});