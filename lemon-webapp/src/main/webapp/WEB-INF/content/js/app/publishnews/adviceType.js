/**
 * Created by xiazl on 2017/1/18.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    require("jquery");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var check = require("../common/checkbox");
    var validate = require("validate")
    var widget = require("../common/widget");
    widget.init();


    loadData();
    $("#adviceTypeList").click(function () {
        loadData();
    });
//列表数据加载
    function loadData() {
        var tpl = require("text!app/tpl/publishnews/adviceTypeTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
    };

    //增加修改
    $("body").on("click","#add_update_adviceType",function(){
        var url = $(this).attr("data-url");
        $("#addOrUpdateAdvice").validate({
            submitHandler: function (form) {
                $.ajax({
                    url: url +"?t="+ new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addOrUpdateAdvice").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            loadData();
                        }
                        else
                            alert(data.msg);
                        $("#myModal").modal("hide")
                    }
                });

            }

        });
    });

    //单个删除
    $("body").delegate(".delete_one", "click", function () {
        if(!confirm("该操作对已经设定该类型的通知产生影响，您是否继续删除！")){
            return;
        }
        var _url = $(this).attr("data-url");
        var options = {
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功");
                    $("#myModal").modal("hide");
                    loadData();
                }else{
                    alert(data.msg);
                }
            }
        };
        $.ajax(options);
    });

    //单个删除
    $("body").delegate(".forbidden_one", "click", function () {
        var _url = $(this).attr("data-url");
        var options = {
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("设置成功");
                    $("#myModal").modal("hide");
                    loadData();
                }else{
                    alert(data.msg);
                }
            }
        };
        $.ajax(options);
    });
})