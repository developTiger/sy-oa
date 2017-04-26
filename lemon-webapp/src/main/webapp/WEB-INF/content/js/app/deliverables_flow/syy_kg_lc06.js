/**
 * Created by swb on 2016/8/30.
 */
define(function (require, exports, module) {
    var xinzeng=true;
    var List;
    List = require("../common/pagelist");
    require("jquery");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    require("select2");
    require("mutiSelect");
    $(".select_like").select2({});
    $(".select2_group").select2({
        placeholder: "请选择",
        width: '100%'
    });
    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        multiple: true

    });

    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });
    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");
    $(".select2_multiple").select2({
        width:'100%'
    });

    //主要参加人 控件
    $(".Js_addPeople").on("click",function(){
        var tpl = require("text!app/tpl/delivers/syy_kg_lc06_a_mainPeople_tpl.html");
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

    $("body").delegate("#zdy", "click", function () {
        $("#name3").show();
        $("#name4").show();
        $("#name1").hide();
        $("#name2").hide();
    });
    $("body").delegate("#zdy2", "click", function () {
        $("#name3").hide();
        $("#name4").hide();
        $("#name1").show();
        $("#name2").show();
    });
    $("#appform").validate({
        rules: {
            beginTime: {
                required: true
            },
            endTime: {
                required: true
            },
            notice:{
                required: true
            }
        },
        messages: {
            beginTime: {
                required: "必填"
            },
            endTime: {
                required: "必填"
            },
            notice:{
                required: "必填"
            }
        },
        submitHandler: function (form) {
            var projectName = $("#projectName").val();
            var projectName3=$("#projectName3").val();
            var projectName2=$("#projectName2").val();
            if((projectName==null || projectName=="")&&(projectName3==null || projectName3=="")&&(projectName2==null || projectName2=="")){
                alert("请选择或填写申报项目");
                return false;
            }

            var valid= $("#appform").valid();
            if(valid){
                $("#Submit").attr("disabled","disabled") ;
            }
            var isKu=$("#name2").is(":visible");
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#Submit").attr("data-url");
            //var leaderName=$("#leaderName").val();
            //alert(leaderName);
            //var groupMembers=$("#groupMemberList1").val();
            //alert(groupMembers);
            var groupMemberList1=$("#groupMemberList1").val();
            var renm=""
            $(".txtValue").each(function(){
                renm =renm + $(this).val()+",";
            });

            $("#renming").val(renm.substring(0,renm.length-1));

            $.ajax({
                url: "lc06_weiyi" + "?t=" + new Date().getMilliseconds()+"&xinzeng="+xinzeng+"&isKu="+isKu,
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    if (data.isSuccess) {
                        $.ajax({
                            url: _url + "?t=" + new Date().getMilliseconds()+"&formNo="+formNo+"&groupMembers="+encodeURI(encodeURI(groupMemberList1))+"&isKu="+isKu,
                            type: 'post',
                            data: $("#appform").serializeObject(),
                            success: function (data) {
                                if (data.isSuccess) {
                                    alert("提交成功");
                                    $("#Submit").removeAttr("disabled") ;
                                    window.close();
                                }
                            }
                        });
                    }else{
                        alert("该项目名已存在，请更换名称！");
                        return false;
                    }
                }
            });

        }
    });

    $("body").delegate("#projectName", "change", function () {
        var projectName= $("#projectName option:selected").val();
        $("#formNo").val(formNo);
        projectIdAndPlan(projectName);
    });
    var formNo=$('#formDeliverApplyDto').val();
    if(formNo!=null && formNo!=""){
        xinzeng=false;
        var planInfo=$('#planInfo').val();
        projectIdAndPlan2(formNo,planInfo);
        $("#name1").hide();
        $("#name2").hide();
        $("#projectNameDiv").show();
        var projectNo=$('#formDeliverApplyDto2').val();
        $("#formNo").val(projectNo);
    }
    function projectIdAndPlan2(formNo,planInfo) {
        var options = {
            url: "ystg2?t=" + new Date().getMilliseconds() + "&projectNo=" + formNo ,
            type: 'get',
            success: function (data) {
                $("#assumeCompany").val(data.assumeCompany);
                $(".select2_group").select2("val",data.leader);
                $("#specialtyType").val(data.specialtyType);
                $("#beginTime").val(data.BeginTime);
                $("#endTime").val(data.EndTime);
                //$("#groupMembers").val(data.groupMembers);
                var groupMembers=data==""?new Array():data.groupMembers.split(",");
                $(".select2_multiple").val(groupMembers).trigger('change');
                $("#joinComopany").val(data.joinComopany);
                $("#projectName2").val(data.projectName);
                $("#ztc").val(data.ztc);
                $("#sxyx").val(data.sxyx);
                $("#nrzy").val(data.nrzy);
                $("#zllxjsl").val(data.zllxjsl);
                if(planInfo==""){
                    $("#projectName2").removeAttr("readOnly");
                }
            }
        };
        $.ajax(options);
    };
    function projectIdAndPlan(projectName) {
        var options = {
            url: "ystg?t=" + new Date().getMilliseconds() + "&projectName=" + encodeURI(encodeURI(projectName)) ,
            type: 'get',
            success: function (data) {
                $("#assumeCompany").val(data.assumeCompany);
                $(".select2_group").select2("val",data.leader);
                $("#specialtyType").val(data.specialtyType);
                $("#beginTime").val(data.beginTime1);
                $("#endTime").val(data.endTime1);
                //groupmembers(data.groupMembers);
                var groupMembers=data==""?new Array():data.groupMembers.split(",");
                $(".select2_multiple").val(groupMembers).trigger('change');

                //$(".select2_multiple").select2().val(groupMembers).trigger('change');

                //$(".select2_multiple").val(groupMembers);
                $("#joinComopany").val(data.joinComopany);
                //$("#projectName2").val(data.projectName);
                var val_text=data.groupMembers;
            }
        };
        $.ajax(options);
    };
    function groupmembers(val_text){
        if(val_text !=undefined){
            if(val_text.indexOf(",") > 0) {
                var result = val_text.split(",");
                for (var i = 0; i < result.length; i++) {
                    $(".txtValue").children("option").each(function () {
                        if ($(this).val() == result[i]) {
                            $(this).prop("selected", "selected");
                        }//每一个option
                    });
                }
            }else{
                $(".txtValue").children("option").each(function () {
                    if ($(this).val() == val_text) {
                        $(this).prop("selected", "selected");
                    }//每一个option
                });
            }
        }
    }
});
