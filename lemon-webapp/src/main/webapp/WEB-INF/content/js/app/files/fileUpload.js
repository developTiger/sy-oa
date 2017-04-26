define(function (require, exports, module) {

    require("jquery");
    var template = require("template");
    require("ajaxUpload");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    widget.init();
    var List = require("../common/pagelist");

    loadData(0);// 0  清空  urldate  1：新增 2：忽略
    $("#DeptQueryBtn").click(function () {
        loadData(0);
    });

    var urlDate = [];

    function loadData(backType) {
        var tpl = require("text!app/tpl/file/fileTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        if (backType == 1)
            urlDate.push($("#searchForm").serializeObject());
        if (backType == 0)
            urlDate = [];
        List("#fileContent", tpl, url, data, 1, 10);

    }


    $("body").delegate(".getFloder", "dblclick", function () {
        var id = $(this).attr("data-id");
        var path = $(this).attr("data-fpath");
        var fullpath = $("#fullPath").html();
        $("#fid").val(id);
        if (fullpath == "~") {
            $("#path").val(path);
             $("#fullPath").html(path);
        } else {
            $("#path").val(fullpath + " / " + path);
            $("#fullPath").html(fullpath + " / " + path);
        }
        loadData(1);
    });

    $("body").delegate("#upload", "click", function () {
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'ajax_fileupload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: $("#searchForm").serializeObject(),
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                    $("#myModal").modal("hide");
                    loadData(2);
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    })
    $("body").delegate("#goback", "click", function () {
        var length = urlDate.length;
        if (length > 1) {
            var data = urlDate[length - 2];

            $("#fid").val(data.id);
            $("#path").val(data.path);
            urlDate.pop();
            loadData(2);
        }
        else {
            $("#fid").val("");
            $("#path").val("");
            urlDate = [];
            loadData(2);
        }

    })
    $("body").delegate("#homeBtn", "click", function () {

        $("#fid").val("");
        $("#path").val("");
        urlDate = [];
        loadData(2);


    })


    $("body").delegate(".delete", "click", function () {
        var a = confirm("确定要删除么？");
        if (a == true) {
            var url = $(this).attr("data-url");
            var id = $(this).attr("data-id");
            var options = {
                url: url + "?t=" + new Date().getMilliseconds() + "&id=" + id,
                type: 'get',
                success: function (data) {
                    if (data.isSuccess) {
                        alert("操作成功");
                        loadData(2);
                    }
                    else
                        alert(data.msg);
                }
            };
            $.ajax(options);
        }
        else {
            return;
        }

    })

    $("body").delegate("#addOrUpdatefloder", "click", function () {
        var url = $(this).attr("data-url");
        var options = {
            url: url + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#addfloder").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");
                    loadData(2);
                    $("#myModal").modal("hide");
                }
                else
                    alert(data.msg);
            }
        };
        $.ajax(options);
    })
    document.body.onbeforeunload = function (event) {
        var c = event || window.event;
        if (/webkit/.test(navigator.userAgent.toLowerCase())) {
            return "离开页面将导致数据丢失！";
        }
        else {
            c.returnValue = "离开页面将导致数据丢失！";
        }
    }

})