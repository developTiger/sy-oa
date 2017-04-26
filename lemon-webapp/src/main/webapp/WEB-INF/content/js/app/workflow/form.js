define(function (require, exports, module) {
    require('Jquery');
    var template = require("template");
    require("../common/jquery.serializeObject");
    var validate = require("validate");


    exports.render = function (target, tpl) {
        this.bindBtn();
        if (!target) return;
        var formNo = target.attr("form-no");
        var viewOnly = target.attr("view_only");
        var _url = target.attr("data-url");
        $.ajax({
            url: _url + "?formNo=" + formNo + "&viewOnly=" + true + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            async: false,
            success: function (data) {
                if (data) {
                    var html = template.compile(tpl)(data);
                    target.html(html);
                    this.renderComplete();
                    //$('#example-dropUp').multiselect({
                    //    includeSelectAllOption: true,
                    //    maxHeight: 400
                    //});

                }
            }
        });
    };

    exports.submit = function (validate) {
        validate();
    };


    exports.doSubmit=function(){

        $.ajax({
            url:"ajax_approve?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#appform").serializeObject(),
            success: function (data) {
                if (data.isSuccess)
                    alert("操作成功");
                else
                    alert(data.msg)
            }
        });
    };
    exports.bindBtn = function () {
        var that = this;
        $("body").delegate(".appbtn", "click", function () {
            if (!that.validate()) {
                return;
            }
            var type = $(this).attr("btn-type")

            if (type == "approver") {
                $("#appValue").val(1);
            }
            if (type == "reject") {
                $("#appValue").val(2);
            }
            if (type == "goBack") {
                $("#appValue").val(3);
            }

        })
    };


})
