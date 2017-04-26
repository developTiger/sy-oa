/**
 * Created by user on 2016/6/15.
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

    require('treeTable');
    var myWidGet = require("../common/myWidGet");

    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#driverQueryBtn").click(function () {
        loadData();
    });
   //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true

        });
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/driver/driverInfoTpl.html");

        var url = $("#searchForm").attr("data-url");

        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }

    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    //删除car
    $("#deleteDriver").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value==""){
            alert("请先选择要删除的数据再进行操作");
            return;
        }
        if(!confirm("确认要删除？")){
            window.event.returnValue = false;
        }

        deleteDriver(chk_value.toString(), false);
    })

   function deleteDriver(selectids) {
        var options = {
            url: "ajax_deleteDriver?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
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
    jQuery.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (length == 11 && mobile.test(value))|| (tel.test(value));
    }, "号码格式错误");

    var vaLi= function validates() {
        $("#addDriverInfoForm").validate({
            rules: {
                companyId:{
                    required: true,
                    rangelength:[1,50]
                },
                driverName: {
                    required: true
                },
                phone:{
                    required: true,
                    mobile:true

                },
                hrieTime:{
                    required: true
                },
                bornTime:{
                    required: true
                },
                obEvidtime:{
                    required: true
                },
                docType:{
                    required: true
                }
            },
            messages: {
                companyId:{
                    required: "请选择公司",
                    rangelength:"字符必须在50以内"
                },
                driverName: {
                    required: "必填"
                },
                phone:{
                    required: "请填写号码",
                    mobile:"号码格式错误"
                },
                hrieTime:{
                    required: "请填写时间"
                },
                bornTime:{
                    required: "请填写时间"
                },
                obEvidtime:{
                    required: "请填写时间"
                },
                docType:{
                    required: "请选择证件类型"
                }


            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateDriver").attr("data-url");
             /!* alert(_url);*!/
                var options = {
                     url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addDriverInfoForm").serializeObject(),
                    success: function (data) {
                        var obj = eval("("+data+")");
                        if(obj.flag = 0){
                            if(obj.res_info = "success")
                                alert("提交成功");
                            else
                                alert("提交失败");
                        }else if(obj.flag = 1){
                            if(obj.res_info = "success")
                                alert("提交成功");
                            else
                                alert("提交失败");
                        }
                        if (obj.res_info == "success"){
                            $("#myModal").modal("hide")
                            loadData();
                        }
                    }
                };
                $.ajax(options);
            }
        })
    }
    widget.init(vaLi);

    $("#driver-bgtime").blur(function(){

        if($("#driver-bgtime").val()!=null){
            var reg=/-/g;

            var begin=$("#driver-bgtime").val();
            begin=begin.replace(reg,'');
            var begintime=parseFloat(begin);

            var end=$("#driver-edtime").val();
            end=end.replace(reg,'');
            var endtime=parseFloat(end);
            if(endtime<begintime){
                alert("结束时间不能小于开始时间")
            }
        }

    });
    $("#driver-edtime").blur(function(){
        if($("#driver-bgtime").val()!=null){
            var reg=/-/g;

            var begin=$("#driver-bgtime").val();
            begin=begin.replace(reg,'');
            var begintime=parseFloat(begin);

            var end=$("#driver-edtime").val();
            end=end.replace(reg,'');
            var endtime=parseFloat(end);
            if(endtime<begintime){
                alert("结束时间应大于开始时间")
            }
        }
    });

})