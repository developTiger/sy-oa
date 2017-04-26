/**
 * Created by admin on 2016/8/15.
 */
define(function (require, exports, module) {

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    require("ajaxUpload");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//使用dateFormat日期格式转换

    require("select2");
    var form = require("../common/form")
    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    $("#savePersonName").select2({
        placeholder: "请选择人员",
        allowClear: true

    });


    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm = function () {
        var target;
        if ($("#syy_equipApply_v").length > 0) {
            target = $("#syy_equipApply_v");
            var tpl = require("text!app/tpl/equipForms/syy_ay_lc01_v_Tpl.html");
            form.render(target, tpl);
        }
    }


    exports.pageLoad = function () {
        loadForm();
    };

    $("#appform").validate({

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#equipApplySubmit").attr("data-url");
            var d1 = $("#wbuyTime").val();//购进日期
            var d2 = $("#wproductTime").val();//投产日期
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            if (timestamp1 > timestamp2) {
                alert("投产日期不可能早于购进日期");
                return;
            }
            var deptId=$("#unitName").find("option:selected").attr("data-value");
            $("input[name='hiddenDeptId']").val(deptId);
            $.ajaxFileUpload({
                url: _url + "?t=" + new Date().getMilliseconds(),
                //type:"post",
                secureuri: false,
                fileElementId: ['fileId'],
                dataType: 'text',
                data: $("#appform").serializeObject(),
                success: function (data, status) {
                    var resultStart = data.indexOf('{');
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                    if (result.isSuccess) {
                        alert("申请成功!");
                        window.close();
                    } else {
                        alert(result.msg);
                    }
                },
                error: function (data) {//服务器响应失败的函数处理
                    $('#result').html('文件上传失败，请重试！！');
                }
            })
        }
    })


//设备信息修改使用
    $("#editEquipmentInfoForm").validate({

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#equipmentUpdate").attr("data-url");
            var d1 = $("#wbuyTime").val();//购进日期
            var d2 = $("#wproductTime").val();//投产日期
            var timestamp1 = Date.parse(new Date(d1));
            var timestamp2 = Date.parse(new Date(d2));
            if (timestamp1 > timestamp2) {
                alert("投产日期不可能早于购进日期");
                return;
            }
            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: "post",

                data: $("#editEquipmentInfoForm").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        alert("修改成功!");
                        window.location.href = "sra_e_mg";
                    } else {
                        alert(data.msg);
                    }

                }
            })
        }
    })


    //var foo = [1, 2, 3, 4, 5, 6];
    //foo.length = 3;
    //foo; // [1, 2, 3]
    //
    //foo.length = 6;
    //foo; // [1, 2, 3]
    //
    //var a={
    //    o:3,
    //    m:function(){
    //        return this.o+1;
    //    }
    //}
    //console.log(a.o);
    //var p = Object.create(a);//a就是p对象的一个原型
    //p.o=10;
    //console.log(p.m());

    function dateFormat(date, format) {
        if (!date)return;
        if (!format) {
            format = "yyyy-MM-dd hh:mm:ss";
        }
        date = new Date(date);
        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };

        format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            }
            else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    }


    $("#wtestTime").blur(function () {
        addMonth();
    });

    $("#checkdate").blur(function () {
        addMonth();
    });

//function getNextTime(){
//    debugger;
//    var checkdate=$("#checkdate").select().val();
//    var testTime=$("#wtestTime").val();
//    if(testTime!=""&&testTime!=null&&testTime!=undefined&&checkdate!=""&&checkdate!=null&&checkdate!=undefined){
//        var date=new Date(String(testTime));
//        date.setMonth(date.getMonth() +Number(checkdate))
//        date.setDate(date.getDate()+Number(checkdate));
//        $("#wnextTestTime").val(dateFormat(date,"yyyy-MM-dd"));
//    }
//}
    function addMonth() {
        var checkdate = $("#checkdate").select().val();
        var testTime = $("#wtestTime").val();
        if (checkdate != "" && testTime != "") {
            var d = new Date(Date.parse(testTime));
            d.setMonth(d.getMonth() + Number(checkdate));
            $("#wnextTestTime").val(dateFormat(d, "yyyy-MM-dd"));
        }
    }


})