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
    //widget.init();
    var validate = require("validate");
    require('treeTable');
    var myWidGet = require("../common/myWidGet");


    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#companyQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/carInfo/rentCompanyTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }


    var callb= function callback() {
        $("#addCompanyInfoForm").validate({

            rules:{
                name:{
                    required:true,
                    maxlength:50
                }
            },
            messages:{
                name:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                }
            },

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateCompany").attr("data-url");

                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addCompanyInfoForm").serializeObject(),
                    success: function (data) {
                        var obj = eval("("+data+")");
                        if(obj.flag == 0){
                            if(obj.res_info == "success")
                                alert("新增成功");
                            else
                                alert("新增失败");
                        }else if(obj.flag == 1){
                            if(obj.res_info == "success")
                                alert("修改成功");
                            else
                                alert("修改失败");
                        }
                        //alert("新增成功");
                        if (obj.res_info == "success"){
                            $("#myModal").modal("hide")
                            loadData();
                        }
                    }
                };
                $.ajax(options);
            }

        });

    }
    widget.init(callb);


    $("#deleteCompany").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
      if(chk_value.length == 0){
          alert("请先选择要删除的数据再进行操作")
      }else {
          deleteMenu(chk_value.toString(), false);
      }
    })

    function deleteMenu(selectids) {
        if(confirm("确认执行删除操作吗？")){
            var options = {
                url: "ajax_deleteCompany?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
                type: 'get',
                success: function (data) {
                    if (data == "success") {
                        alert("删除成功");
                        loadData();
                    }
                    else
                        alert(data);
                }
            };
            $.ajax(options);
        }else {
            $(".checkBtn:checkbox").prop("checked", false);
            loadData();
        }

    }

    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });





    /*$("body").delegate("#addOrUpdateCar", "click", function () {
     var _url = $("#addOrUpdateCar").attr("data-url");

     var options = {
     url: _url + "?t=" + new Date().getMilliseconds(),
     type: 'post',
     data: $("#addCarInfoForm").serializeObject(),
     success: function (data) {
     if (data == "success")
     alert("新增成功");
     $("#myModal").modal("hide")
     loadData();
     }
     };
     $.ajax(options);
     });*/


});