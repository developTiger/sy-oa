define(function (require, exports, module) {

    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");
    require("ajaxUpload");
    var template = require("template");
    var widget = require("../common/widget");
    var myWidGet = require("../common/myWidGet");
    require('treeTable');
    require("jquery");
    //require("mutiSelect");
    require("select2");
  //   check.checkAll("body", ".checkAll", ".checkBtn");

    $(".select2_multiple").select2({
        width:'100%',
        closeOnSelect: false,
        placeholder: "请选择",
        multiple: true

    });



    var form =  require("../common/form");
    //初始化 添加验证 及 做提交
    form.init();

    //主要参加人 控件
    $(".Js_addPeople").on("click",function(){
        var tpl = require("text!app/tpl/projectInfo/syy_kg_lc01_a_mainPeople_tpl.html");
        $.ajax({
            url:"ajax_partyGroup_lc01_mainPeople?"+"t="+new Date().getMilliseconds(),
            type:'post',
            success:function(data){
                if (data){
                    var html = template.compile(tpl)(data);
                    $(".Js_attendPeople").append(html);

                    $(".select2_group").select2({
                        placeholder: "请选择",
                        width: '100%'
                    });

                }
            }
        })
    })

    $("body").on("click",".Js_mainPeople_delete",function(){
        $(this).parent().parent().remove();
    })

    $("body").delegate("#back","click",function(){
        window.close();
    });

    loadData11();
    $("#queryEmps").click(function () {
        loadData11();
    });
    function loadData11(){
        var tpl = require("text!app/tpl/awards/syy_gy_lc02_tpl.html");
        var url= "ajax_query_addEmps1";
        var name = $("#empName").val();
        var dept = $("#deptName").val();
        List("#table",tpl,url,"empName="+encodeURI(name)+"&deptName="+dept,1,10);
    };

    $("body").delegate(".addSingleEmp1","click", function () {
          var emps;
          var emp = $(this).val();
          var ems = $("#ems").val(emp);
              emps =$("#ems").val()+",";
          var s = emps.substring(0,emps.length-1);
        alert(s);
    });



   jQuery.validator.addMethod("_character", function(value, element) {
        var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$|\、|\，|\,|\?|\。|\!|\！|\(|\)|\（|\）|\"|\"|\;|\“|\”|\；|\>|\<|\《|\》/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "请选择");
    jQuery.validator.addMethod("judgeGroup", function(value, element) {
        if (value ==null){
            return false;
        }
        return true;
    }, "请选择");

    var loadForm=function(){
        var target;
        if ($("#syy_kg01_v").length>0) {
            target=$("#syy_kg01_v");
            var tpl = require("text!app/tpl/projectInfo/syy_kg_lc01_vTpl.html");
            form.render(target,tpl);
        }
    }
    exports.pageLoad = function () {
        loadForm();
    };

    $("body").delegate("#baoBeginDate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });

    });
    //时间插件
    $("body").delegate("#Inputdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy',
            readOnly:true
        });

    });
    $("body").delegate("#deputy", "change", function () {
        $("#deputy option:selected").each(function () {
            $('#deputyHid').val(($(this).val())); //这里得到的就是
        });
    });
    $("body").delegate(".txtValue1","change",function() {
        $(".txtValue1").children("option").each(function(){
            if($(this).prop('selected')){
                var groupMemberList=$(".txtValue1").val();
                $("#groupMembers").val(groupMemberList);
            }//每一个option
        })
    });


    $("body").delegate("#projectPlan_EndTime", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            minDate:'#F{$dp.$D(\'projectPlan_StartTime\')}'
        });
    });
    $("body").delegate("#projectPlan_StartTime", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd',
            readOnly:true,
            maxDate:'#F{$dp.$D(\'projectPlan_EndTime\')}'
        });
    });
    $.validator.addMethod("compareDate",function(value,element){
        var assigntime = $("#beginTime").val();
        var deadlinetime = $("#endTime").val();
        var reg = new RegExp('-','g');
        assigntime = assigntime.replace(reg,'/');//正则替换
        deadlinetime = deadlinetime.replace(reg,'/');
        assigntime = new Date(parseInt(Date.parse(assigntime),10));
        deadlinetime = new Date(parseInt(Date.parse(deadlinetime),10));
        if(assigntime>deadlinetime){
            return false;
        }else{
            return true;
        }
    },"<font color='#E47068'>结束日期必须大于开始日期</font>");





    //新增
    $("body").delegate("#applyProjectPlan","click",function() {
        $("#appform").validate({
            rules: {
                projectNo: {
                    required: true,
                    maxlength: 50
                },
                projectName: {
                    required: true,
                    maxlength: 50
                    //digits:true
                },
                specialtyType: {
                    required: true
                },
                projectPlanInfo: {
                    required: true,
                    maxlength: 500
                },
                assumeCompany: {
                    required: true,
                    maxlength: 50
                },
                joinComopany: {
                    required: true,
                    maxlength: 50
                },
                beginTime1: {
                    required: true
                },
                endTime1: {
                    required: true/*,
                    compareDate: true*/

                },
                projectType: {
                    required: true
                },
                leader: {
                    required: true
                },
                deputy: {
                    required: true
                },
                dept: {
                    required: true,
                    maxlength: 50
                },
                groupMemberList1: {
                    required: true
                },
                groupMember: {
                    required: true
                }
            },
            messages: {
                projectNo: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                projectName: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                specialtyType: {
                    required: "请选择"
                },
                projectPlanInfo: {
                    required: "必填",
                    maxlength: "长度不能超过500个字符"
                },
                assumeCompany: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                joinComopany: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                beginTime1: {
                    required: "请选择"

                },
                endTime1: {
                    required: "请选择"/*,
                     compareDate: "结束日期必须大于开始日期"*/
                },
                projectType: {
                    required: "请选择"
                },
                leader: {
                    required: "请选择"
                },
                deputy: {
                    required: "请选择"
                },
                joinComopany: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                dept: {
                    required: "必填",
                    maxlength: "长度不能超过50个字符"
                },
                groupMemberList1: {
                    required: "必填"
                },
                groupMember: {
                    required: "必填"
                }
            },

            submitHandler: function (form) {
                var valid= $("#appform").valid();
                if(valid){
                    $("#applyProjectPlan").attr("disabled","disabled") ;
                }
                var fileIds = [];
                $("input[name='fileName']").each(function(){
                    fileIds.push($(this).attr("id"));
                });
                 var renm=""
                $(".txtValue").each(function(){
                    renm =renm + $(this).val()+",";
                });

                $("#renming").val(renm.substring(0,renm.length-1));

                $.ajax({
                    url: "ajax_judgeProjectNo",
                    data:"projectNo=" + $("#projectNo").val(),
                    type:'post',
                    dataType:'json',
                    success: function (data) {
                        if(!data.isSuccess){
                            alert(data.msg);
                        }else {
                            $.ajaxFileUpload({
                                url: $("#applyProjectPlan").attr("data-url"),
                                secureuri: false,
                                fileElementId: fileIds,
                                dataType: 'text',
                                data: $("#appform").serializeObject(),
                                success: function (data, status) {
                                    var resultStart = data.indexOf("{");
                                    var resultEnd = data.indexOf("}");
                                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                                    if (result.isSuccess) {
                                        alert("发布成功！");
                                        $("#applyProjectPlan").removeAttr("disabled") ;
                                        window.close();
                                        if($(window.opener.document).find(".pagination li.active a")[0])
                                            $(window.opener.document).find(".pagination li.active a")[0].click();
                                        else
                                            $(window.opener.document).find("#secondForm")[0].click();

                                    } else {
                                        alert(result.msg);
                                    }
                                },
                                error: function (data, status, e) { //服务器响应失败时的处理函数
                                    $('#result').html('新增失败，请重试！！');
                                }
                            });
                        }
                    }});

            }

        });

    });




    var id = 1;
    var tpl_files = require("text!app/tpl/forms/syy_rs_lc02_v_filesUploadTpl.html");
    //多附件上传
    $("body").delegate(".addFileBtn","click",function(){
        id++;
        var d = {num:id};
        var html = template.compile(tpl_files)(d);

        $("#filesHtml").append(html);
    });
    //附件删除
    $("body").delegate(".deleteFileBtn","click",function(){
        $(this).parent().parent().remove();
    });


    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal_1").modal("hide")
    });

    $('#myModal_1').on('hide.bs.modal', function () {  });


        var val_text = $("#groupMember").val();
       /* if (val_text != null) {
            if (val_text.indexOf(",") > 0) {
                var result = val_text.split(",");
                for (var i = 0; i < result.length; i++) {
                    $(".txtValue").children("optgroup").children("option").each(function () {
                        if ($(this).val() == result[i]) {
                            $(this).prop("selected", "selected");
                        }//每一个option
                    });

                }
            } else {
                $(".txtValue").children("optgroup").children("option").each(function () {
                    if ($(this).val() == val_text) {
                        $(this).prop("selected", "selected");
                    }//每一个option
                });
            }
            $(".select2_multiple").select2({
                width: '100%',
                closeOnSelect: false

            });
        }*/


     /*   $("body").delegate(".txtValue", "change", function () {
            $(".txtValue").children("optgroup").children("option").each(function () {
                if ($(this).prop('selected')) {
                    var groupMemberList = $(".txtValue").val();
                    $("#groupMember").val(groupMemberList);
                }//每一个option
            })
        })*/


        $(".select2_group").select2({
            placeholder: "请选择",

            width: '100%'
        });




})
