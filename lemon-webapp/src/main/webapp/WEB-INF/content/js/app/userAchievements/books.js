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
    $("#booksQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/userAchievements/booksTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }



    var callb= function callback() {
        $("#addBooksInfoForm").validate({

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdatebook").attr("data-url");

                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addBooksInfoForm").serializeObject(),
                    success: function (data) {
                        if (data == "success")
                            alert("新增成功");
                        $("#myModal").modal("hide")
                        loadData();
                    }
                };
                $.ajax(options);
            }

        });

    }
    widget.init(callb);



    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    //删除car
    $("#deleteBooks").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        deleteMenu(chk_value.toString(), false);
    })

    function deleteMenu(selectids) {
        var options = {
            url: "ajax_deleteBooks?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
    }


    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd'});
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