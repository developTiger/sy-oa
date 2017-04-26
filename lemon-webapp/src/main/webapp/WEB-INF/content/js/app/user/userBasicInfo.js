define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    require("ajaxUpload");

    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    require("../common/templeteHelper2");
    require("text!app/tpl/uauth/BasicInformation.html");
    require("text!app/tpl/uauth/PositionInformation.html");
    require("text!app/tpl/uauth/QualificationInformation.html");
    require("text!app/tpl/uauth/EducationInformation.html");
    require("text!app/tpl/uauth/WorkExInformation.html");
    require("text!app/tpl/uauth/FamilyInformation.html");

    require("text!app/tpl/uauth/EducationInformationTable.html");
    require("text!app/tpl/uauth/WorkExInformationTable.html");
    require("text!app/tpl/uauth/PositionInformationTable.html");
    require("text!app/tpl/uauth/QualificationInformationTable.html");
    require("text!app/tpl/uauth/FamilyInformationTable.html");
    require("jquery");
    require("select2");
    var currentPage = "BasicInformation";
    var empIdCheck = "";
    $("body").delegate(".selectUserInfo", "click", function () {
        currentPage = $(this).attr("href");
        empIdCheck = $("#baseEmpId").val();
        if (empIdCheck == "") {
            if (currentPage != "BasicInformation")
                alert("请先填写添加员工基本信息");
        } else {
            pageShow(currentPage);
        }
    });

    function pageShow(selectItem) {
        if (selectItem == "BasicInformation") {
            ShowSelect(selectItem, vaLi);
            $("#table").html("");
            /*   $.ajax({
             type:"get",
             url:"ajax_get_all_emp?t="+new Date().getMilliseconds()+"&empId="+$("#empId").val(),
             async:false,
             success:function(data){
             if(data){
             var target = $("#parentEmp");
             $("#parentEmp option:not(:first)").remove();
             for(var i=0; i<data.length; i++)
             {
             target.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>")
             }
             }
             }
             });*/

        } else {
            showSelectTable(selectItem + "Table");
            $("#table").html("");
        }
    }

    function showSelectTable(selectItem) {
        var select2 = require("text!app/tpl/uauth/" + selectItem + ".html");
        var id = $("#baseEmpId").val();
        $.ajax({
            type: "get",
            url: selectItem + "?t=" + new Date().getMilliseconds() + "&empId=" + id,
            async: false,
            success: function (data) {
                if (data) {
                    var html = template.compile(select2)(data);
                    $("#home").html(html);
                } else {
                    var html = template.compile(select2)(data);
                    $("#home").html(html);
                }
            }
        });
    }

    function ShowSelect(selectItem, val) {
        var select = require("text!app/tpl/uauth/" + selectItem + ".html");
        $.ajax({
            type: "get",
            url: selectItem + "?t=" + new Date().getMilliseconds() + "&empId=" + $("#baseEmpId").val(),
            async: false,
            success: function (result) {
                if (result) {
                    //  result=eval("("+result+")");
                    var html = template.compile(select)(result);
                    $("#home").html(html);
                    console.log("compile page ok!");
                } else {
                    var html = template.compile(select);
                    $("#home").html(html);
                }
            }
        });
        /*  if(selectItem=="BasicInformation"){
         $.ajax({
         type:"get",
         url:"ajax_get_all_emp?t="+new Date().getMilliseconds(),
         async:true,
         success:function(data){
         if(data){
         var target = $("#parentEmp");
         $("#parentEmp option:not(:first)").remove();
         var leaderId = $("#parentEmpId").val();
         alert(leaderId);
         for(var i=0; i<data.length; i++)
         {
         if(leaderId==data[i].id.toString()){
         target.append("<option selected value='" + data[i].id + "'>" + data[i].name + "</option>")
         }
         else {
         target.append("<option value='" + data[i].id + "'>" + data[i].name + "</option>")
         }
         }
         }
         console.log("apply emp info end!");
         }
         });
         }*/
        val && (typeof  val == "function") && val();
    }

    /*
     *修改点击事件
     */
    $("body").delegate(".updateInfo", "click", function () {
        var select2 = require("text!app/tpl/uauth/" + $(this).attr("data-title") + ".html");
        $.ajax({
            type: "post",
            url: $(this).attr("data-url"),
            async: false,
            success: function (result) {
                if (result) {
                    var html = template.compile(select2)(result);
                    $("#table").html(html);
                    vaLi && (typeof  vaLi == "function") && vaLi();
                } else {
                    var html = template.compile(select2)(result);
                    $("#table").html(html);
                    vaLi && (typeof  vaLi == "function") && vaLi();
                }
            }
        });


    })
    /*
     * 删除事件！！！
     */
    $("body").delegate(".deleteInfo", "click", function () {
        $.ajax({
            type: "post",
            url: $(this).attr("data-url") + "&type=" + $(this).attr("data-title"),
            async: false,
            success: function (data) {
                if (data.isSuccess) {
                    alert("删除成功");
                    pageShow(currentPage);
                } else {
                    alert("删除失败");
                }
            }
        });
    })

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });


    $("body").delegate("#submitQualificationInfo", "click", function () {
        var a;
        if ($("#empId")) {
            $("#empId").val($("#baseEmpId").val());
        }
        $.ajaxFileUpload({
            url: 'ajax_Add_QualificationInformation',
            secureuri: false,
            fileElementId: ['fileId'],
            dataType: 'text',
            data: $("#BasicInfoForm").serializeObject(),
            success: function (data, status) {
                $("#table").html("");
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {
                    alert("上传成功！");
                    showSelectTable("QualificationInformationTable");
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }

        });

    })

    /*
     //上岗证书 提交按钮
     $("body").on("click","#submitQualificationInfo", function () {
     var a;
     if($("#empId")){
     $("#empId").val( $("#baseEmpId").val());
     }

     var tpl = require("text!app/tpl/uauth/QualificationInformationTable.html");
     $.ajax({
     url:"ajax_Add_QualificationInformation",
     type:"post",
     data:$("#BasicInfoForm").serializeObject(),
     success:function(data){
     if(data){
     alert("修改成功！");
     $("#BasicInfoForm").hide();
     showSelectTable("QualificationInformationTable");
     //var html = template.compile(tpl)(data);
     //$("#table").html(html);
     //vaLi && (typeof  vaLi == "function") &&vaLi();
     }
     }
     });

     });
     */


    /*
     *   验证保存js。。。
     */
    var vaLi = function validates() {
        jQuery.validator.addMethod("idCardCheck", function (value, element) {
            var idCard = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
            return (idCard.test(value));
        }, "");


        $("#BasicInfoForm").validate({
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#submitUserInfo").attr("data-url");
                if ($("#empId")) {
                    $("#empId").val($("#baseEmpId").val());
                }
                var yearWithMoney = $("input[name='yearWithMoney']").val();
                var hasYear = $("input[name='hasYear']").val();
                if (yearWithMoney == '') {
                    $("input[name='hasYear']").val(0)
                } else {
                    if (yearWithMoney >= 0 && hasYear != '') {
                        if (Number(yearWithMoney) < Number(hasYear)) {
                            alert("剩余带薪年假不可能多余带薪年假总天数");
                            return;
                        }
                    }
                }

                var options = {
                    url: "ajax_Add_" + _url + "?t=" + new Date().getMilliseconds(),
                    type: "post",
                    data: $("#BasicInfoForm").serializeObject(),
                    success: function (data) {
                        //  ShowSelect(temp+".html",vaLi);
                        if (data.isSuccess) {
                            alert("操作成功");
                            if (currentPage == "BasicInformation") {
                                $("#baseEmpId").val(data.id);
                                window.history.pushState(null, "新增用户信息", window.location.pathname + "?id=" + data.id);
                            }
                            pageShow(currentPage);
                        }
                        else
                            alert("操作失败");
                    }
                };
                $.ajax(options);
            }
        })
    }
    ShowSelect("BasicInformation", vaLi);

    //取消事件
    $("body").delegate(".cancelBtn", "click", function () {
        $("#table").html("");
    })
    $("#leaderId").select2({
        palceholder: "请选择",
        allowClear: true

    })


});



