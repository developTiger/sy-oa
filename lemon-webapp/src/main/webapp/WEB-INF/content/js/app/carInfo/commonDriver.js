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
    var wDatePicker = require("wdatePicker");
    var myWidGet = require("../common/myWidGet");


    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#commonDriverQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/carInfo/commonDriverTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }



    var callb= function callback() {
        $("#addCommomDriverInfoForm").validate({

            rules: {
                driverName: {
                    required: true

                },
                carNo:{
                    required:true

                }

            },
            messages: {
                driverName: {
                    required: "必须选择"

                },
                carNo:{
                    required:"必须选择"

                }
            },

            submitHandler: function (form) {      //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateCommonDriver").attr("data-url");

                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addCommomDriverInfoForm").serializeObject(),
                    success: function (data) {
                        if (data == "success"){
                            alert("新增成功");
                            $("#myModal").modal("hide")
                            loadData();
                        }else
                            alert("新增失败");
                    }
                };
                $.ajax(options);
            }

        });

    }
    widget.init(callb);






    $("#deleteCommonDriver").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.length==0){
          alert("请先选择要删除的数据再进行操作");
        }else{
            deleteMenu(chk_value.toString(), false);
        }
    });

    function deleteMenu(selectids) {
        if(confirm("确认执行删除操作吗？")){
            var options = {
                url: "ajax_deleteCommonDriver?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
                type: 'get',
                success: function (data) {
                    if (data == "success") {
                        alert("修改成功");
                        loadData();
                    }
                    else
                        alert(data);
                }
            };
            $.ajax(options);
        }else{
            $(".checkBtn:checkbox").prop("checked",false);
            loadData();
        }

    }





    $("body").delegate("#modifyCommonDriver", "click", function () {
         var _url = $("#modifyCommonDriver").attr("data-url");

         var options = {
             url: _url + "?t=" + new Date().getMilliseconds(),
             type: 'post',
             data: $("#modifyCommomDriverInfoForm").serializeObject(),
             success: function (data) {
                 if (data == "modify_success"){
                     alert("修改成功");
                     $("#myModal").modal("hide")
                     loadData();
                 }else
                    alert("修改失败");

             }
         };
         $.ajax(options);
     });


});