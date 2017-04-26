/**
 * Created by user on 2016/7/5.
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
       //widget.init();
    require('treeTable');
    var myWidGet = require("../common/myWidGet");
    check.checkAll("body", ".checkAll", ".checkBtn");
    loadData();
    $("#projectrrsultQueryBtn").click(function () {
        loadData();
    });
    //年度
    $("body").delegate("#niandu", "click", function () {
        wDatePicker({ dateFmt: 'yyyy',
                      readOnly:true
        });
    });
    //获得时间
    $("body").delegate(".Wdate2", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });
    //$("body").delegate("#beginTime", "click", function () {
    //    wDatePicker({ dateFmt: 'yyyy-MM-dd',
    //        readOnly:true,
    //        minDate:'#F{$dp.$D(\'endTime\')}'
    //    });
    //});
    //$("body").delegate("#endTime", "click", function () {
    //    wDatePicker({ dateFmt: 'yyyy-MM-dd',
    //        readOnly:true,
    //        maxDate:'#F{$dp.$D(\'beginTime\')}'
    //    });
    //});
    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/awards/awardsInfoTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        //var data= $("#searchForm").serializeObject();
        List("#table", tpl, url, data, 1, 10);
    };

    //删除car
    $("#deleteProjectResult").click(function () {

        if(!confirm("确认要删除？")){
            window.event.returnValue = false;
        }
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        deleteProjectResult(chk_value.toString(), false);
    });
    function deleteProjectResult(selectids) {
        var options = {
            url: "ajax_deleteProjectResult?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
    };



    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn').each(function () {
            chk_value.push($(this).val());
        });
        // alert(chk_value);
        if(chk_value.toString()==null||chk_value.toString()==""){
            alert("数据为空");
            return false
        }
        else {
            ajax_mainProjectNO(chk_value.toString());
        }
    })

    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_awardsInfo_down?formNos="+selectids;
    }

})