/**
 * Created by zhouz on 2016/7/20.
 */
/**
 * Created by swb on 2016/6/29.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");


    var wDatePicker = require("wdatePicker");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });

    //widget.init();
    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#formHeaderQueryBtn").click(function () {
        loadData();
    });
    $("body").delegate(".viewForm", "click", function () {
        var url = $(this).attr("data-url");
        window.open(encodeURI(encodeURI(url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
        //$("#viewframe").attr("src",url);
        //$("#ViewModal").modal({backdrop: 'static', keyboard: false});
        //$("#ViewModal").addClass("modal-center")
    });
    $("body").delegate(".viewTest", "click", function () {
        var url = $(this).attr("data-url");
        var formKind = $(this).attr("form-kind").toLowerCase() + '_a';
        window.location.href = decodeURI(formKind + '?' + url);
    });
    function loadData() {

        var tpl;
        if($("#wappStatus").val()=="U")
          tpl = require("text!app/tpl/workflow/formHeaderWorkTable.html");
        else
            tpl = require("text!app/tpl/workflow/formHeaderWorkDoneTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }

    var callb = function () {
        $("#qjApplyForm").validate({
            rules: {
                summery: {
                    required: true
                }

            },
            messages: {
                summery: {
                    required: "必填"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#qjSubmit").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#qjApplyForm").serializeObject(),
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
});