/**
 * Created by zhouz on 2016/5/30.
 */
/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");

    require("chained");

    //widget.init();
    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#formQueryBtn").click(function () {
        loadData();
    });

    function loadData() {

        var tpl = require("text!app/tpl/workflow/formlistTable.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }
    var callb= function() {
        $("#addform").validate({
            rules: {
                formKindNo: {
                    required: true
                }

            },
            messages: {
                formKindNo: {
                    required: "必填"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form



                var _url = $("#addOrUpdateForm").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addform").serializeObject(),
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

    $("#deleteForm").click(function(){
        var chk_value=[];
        $('.checkBtn:checked').each(function(){
            chk_value.push($(this).val());
        });
        deleteForm(chk_value.toString(),false);
    });
    function deleteForm(selectids){
        var options = {
            url:"_deleteForm?t=" + new Date().getMilliseconds()+"&ids="+selectids,
            type: 'get',
            success: function (data) {
                if (data== "success") {
                    alert("删除成功");
                    loadData();
                }
                else
                    alert(data);
            }
        };
        $.ajax(options);
    }
    $("body").delegate("#cancelModal","click",function(){
        $("#myModal").modal("hide")
    });
    $("body").delegate(".viewTest","click",function(){
        var url = $(this).attr("data-url");
        var formKind = $(this).attr("form-kind").toLowerCase()+'_a';
        window.location.href=decodeURI(formKind+'?'+url);
    });

    //id删除数据
    $("body").delegate("#deleteFormId","click",function() {
        var _url = $("#deleteFormId").attr("data-url");
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