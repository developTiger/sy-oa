/**
 * Created by admin on 2016/8/13.
 */
define(function(require,exports,module){
    require('../init');
    var List = require("../common/pagelist");
    var template = require("template");
    var check = require("../common/checkbox");
    var wDatePicker = require("wdatePicker");
    require("ajaxUpload");
    require("../common/templeteHelper2");
    var check = require("../common/checkbox");
    check.checkAll("body", ".checkAll", ".checkBtn");

    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:00:00'});
    });
    loadData();
    check.checkAll("body", ".checkAll", ".checkBtn")


    $("#equipmentQueryBtn").click(function(){
        loadData();
    });

    /**
     *删除单一设备
     */
    $(".delete_one_equipment").click(function(){
        var _url=$(this).attr("data-url");
       var option={
           url:_url+"&t="+new Date().getMilliseconds(),
           type:'post',
           success:function(data){
               if(data.isSuccess){
                   alert("删除成功！");
                   loadData();
               }else{
                   alert(data.msg)
               }
           }
       };
        $.ajax(option);
    });
    /**
     * 删除多个设备
     */
    $("body").delegate("#delete_more_equipments","click",function(){
        var _url=$(this).attr("data-url");
        var checks=[];
        $(".checkBtn:checked").each(function(){
            checks.push($(this).val());
        });
        var option={
            url:_url,
            type:'post',
            data:{ids:checks.toString()},
            success:function(data){
                if(data.isSuccess){
                    alert("删除成功！");
                    loadData();
                }else{
                    alert(data.msg);
                }
            }

        };
        $.ajax(option);
    });

    $("#inputInfo").click(
        function(){
            $.ajaxFileUpload({
                url: 'inputEquipInfo',
                secureuri: false,
                fileElementId: ['fileId'],
                dataType: 'JOSN',
                // data: $("#publishNews").serializeObject(),
                success: function (data, status) {
                   // alert(data);
                    debugger;
                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("操作成功！"+result.msg);
                        loadData();
                    } else {
                        alert(result.msg);
                        mode=1;
                        $("#myModal").modal("show");
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });

        });

    function loadData(){
        var tpl = require("text!app/tpl/equipment/manageEquipmentTpl.html");
        var url=$("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }

//项目计划导入，弹出对话框初始化
    $("body").delegate("#edit_equip_info","click",function(){
        $("#myModal").modal("show");
    });
    //项目计划导入，弹出对话框初始化
    $("body").delegate("#cancelModal_1","click",function(){
        $("#myModal").modal("hide");
    });
    //项目计划导出模板
    $("body").delegate("#outequipInfo","click",function(){
       $("#myModal").modal("hide");
        window.location.href="outputEquipInfo";
    });



})
