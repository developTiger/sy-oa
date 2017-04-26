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
        wDatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' });
    });

    //widget.init();
    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#formHeaderQueryBtn").click(function () {
        loadData();
    });
    //$("body").delegate(".viewForm", "click", function () {
    //  var url=$(this).attr("data-url");
    //    $("#viewframe").attr("src",url);
    //    $("#ViewModal").modal({backdrop: 'static', keyboard: false});
    //    $("#ViewModal").addClass("modal-center")
    //});
    $("body").delegate(".viewForm", "click", function () {
        var url = $(this).attr("data-url");
        window.open(encodeURI(encodeURI(url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
        //$("#viewframe").attr("src",url);
        //$("#ViewModal").modal({backdrop: 'static', keyboard: false});
        //$("#ViewModal").addClass("modal-center")
    });
    function loadData() {

        var tpl = require("text!app/tpl/workflow/formHeaderTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
    $("body").delegate(".editForm","click",function(){
        var url = $(this).attr("data-url");
        var formKind = $(this).attr("form-kind").toLowerCase()+'_a';
        //window.location.href=decodeURI(formKind+'?'+url);
        window.open(encodeURI(encodeURI(formKind+'?'+url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');

    });

    $("body").delegate(".Js_callBcak","click",function(){
        if(confirm("确定要撤回吗？")) {
            var formNo = $(this).attr("data-formNo");
            var formKind = $(this).attr("data-formKind");
            ;

            $.ajax({
                url: "ajax_callback?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: {formNo: formNo, content: '', formKind: formKind},
                success: function (data) {
                    if (data.isSuccess) {
                        alert("操作成功");
                        loadData();
                        //$(window.opener.document).find(".pagination li.active a")[0].click()
                        ////window.opener.document.getElementsByClassName()
                        //window.close();

                    }
                    else
                        alert(data.msg)

                }
            });
        }

    });

    var callb= function() {
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