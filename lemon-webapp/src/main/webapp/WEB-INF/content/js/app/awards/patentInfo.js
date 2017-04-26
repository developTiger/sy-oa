/**
 * Created by Administrator on 2016/7/5 0005.
 */
define(function (require, exports, module) {
    var List;
    require('../init');
    List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");

    check.checkAll("body", ".checkAll", ".checkBtn")

    loadData();
    $("#patentQueryBtn").click(function () {
        loadData();
    });
    //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
                       readOnly:true
        });
    });
    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/awards/patentInfoTpl.html");

        var url = $("#searchForm").attr("data-url");

        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }

    //删除
    $("#deletePatent").click(function () {

        if(!confirm("确认要删除？")){
            window.event.returnValue = false;
        }
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        deletePatent(chk_value.toString(), false);
    })
    function deletePatent(selectids) {
        var options = {
            url: "ajax_deletePatent?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
    }
})