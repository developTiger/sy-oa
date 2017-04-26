/**
 * Created by zhouz on 2016/5/15.
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
    $("#carQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/carInfo/carInfoTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }


    //新增
    var callb= function callback() {
        $("#addCarInfoForm").validate({

            rules: {
                carType: {
                    required: true,
                    maxlength:50
                },
                carNo:{
                    required:true,
                    maxlength:50
                    //digits:true
                },
                companyId: {
                 required: true
                 },
                status:{
                    required:true
                },
                controlName:{
                    required:true,
                    maxlength:50
                }
            },
            messages: {
                carType: {
                    required: "必填",
                    maxlength:"长度不能超过50个字符"
                },
                carNo:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                companyId:{
                    required:"必须选择"
                },
                status:{
                    required:"必须选择"
                },
                controlName:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                }

            },

            submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
                    var _url = $("#addOrUpdateCar").attr("data-url");

                //alert($("#addCarInfoForm").serializeObject().length);
                    var options = {
                        url: _url + "?t=" + new Date().getMilliseconds(),
                        type: 'post',
                        data: $("#addCarInfoForm").serializeObject(),
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
                            /*if (data == "success")
                                alert("新增成功");*/
                            if(obj.res_info == "success"){
                                $("#myModal").modal("hide")
                                loadData();
                                window.location.href="sra_c_car";
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
    $("#deleteCar").click(function () {
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
                url: "ajax_deleteCar?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd ' });
    });
});