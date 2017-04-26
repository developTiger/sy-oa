/**
 * Created by Administrator on 2016/12/19.
 */
/**
 * Created by zy.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var myWidGet = require("../common/myWidGet");
    var wDatePicker = require("wdatePicker");


    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#specitityQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/carInfo/SpectityTypeInfoTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }


    //新增
   var callb= function callback() {
        $("#addSpectityInfoForm").validate({
            rules: {
                specialtyType:{
                    required:true
                }
            },
            messages: {
                specialtyType:{
                    required:"不能为空"
                }
            },
            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateType").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addSpectityInfoForm").serializeObject(),
                    success: function (data) {
                        var obj = eval("("+data+")");
                        if(obj.flag == 0){
                            if(obj.res_info == "success")
                                alert("新增成功");
                            else
                                alert("新增失败");
                        }else if(obj.flag == 1){
                            if(obj.res_info == "success")
                                alert("修改成功");
                            else
                                alert("修改失败");
                        }
                        if(obj.res_info == "success"){
                            $("#myModal").modal("hide")
                            loadData();

                        }
                    }
                };
                $.ajax(options);
            }
        });
    };
    widget.init(callb);

    //取消按钮
   $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    //删除car
    $("#deleteType").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.length==0){
            alert("请先选择要删除的数据再进行操作");
        }else{
            deleteMenu(chk_value.toString(), false);
        }
    });

    function deleteMenu(selectids) {
        if(confirm("确认执行删除操作吗？")){
            var options = {
                url: "ajax_deleteType?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
        }else{
            $('.checkBtn:checkbox').prop("checked",false);
            loadData();
        }
    }


    //时间控件
  /*  $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd ' });
    });*/
});