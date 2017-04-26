/**
 * Created by swb on 2016/8/30.
 */
define(function (require, exports, module) {
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

    $(".select_like").select2({
        width:'100%'
    });
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd HH:mm:ss',
            readOnly:true
        });
    });

    var form = require("../common/form")

    $("body").delegate(".txtValue1","change",function() {
        $(".txtValue1").children("option").each(function(){
            if($(this).prop('selected')){
                var groupMemberList=$(".txtValue1").val();
                $("#groupMembers").val(groupMemberList);
            }
        })
    });

    $("body").delegate("#goBack", "click", function () {
        var proficientOpinion=$("#proficientOpinion").val();
        //if(proficientOpinion==""){
        //    alert("请填写批示");
        //    return ;
        //}
        var chk_value = [];
        chk_value.push($('#formDeliverApplyDto').val());
        var _url = "syy_kg_lc06_approve2";
        var allContent=$("#content").val();
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds()+"&proficientOpinion="+encodeURI(encodeURI(proficientOpinion))+"&ids="+chk_value.toString()+"&allContent=" +encodeURI(encodeURI(allContent))+"&back=back",
            type: 'post',
            data: $("#approveForm").serializeObject(),
            success: function (data) {
                if (data=="success") {
                    alert("退回成功");
                    if($(window.opener.document).find(".pagination li.active a")[0]) {
                        $(window.opener.document).find(".pagination li.active a")[0].click();
                    }
                    else{
                        $(window.opener.document).find("#secondForm")[0].click();
                    }
                    window.close();
                }
            }
        });
    });

    form.init2(function(){
        $("#approveForm").validate({
            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var proficientOpinion=$("#proficientOpinion").val();
                if(proficientOpinion==""){
                    alert("请填写批示");
                    return ;
                }
                var chk_value = [];
                chk_value.push($('#formDeliverApplyDto').val());
                var _url = "syy_kg_lc06_approve2";
                var allContent=$("#content").val();
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds()+"&proficientOpinion="+encodeURI(encodeURI(proficientOpinion))+"&ids="+chk_value.toString()+"&allContent=" +encodeURI(encodeURI(allContent)),
                    type: 'post',
                    data: $("#approveForm").serializeObject(),
                    success: function (data) {
                        if (data=="success") {
                            alert("操作成功");
                            if($(window.opener.document).find(".pagination li.active a")[0]) {
                                $(window.opener.document).find(".pagination li.active a")[0].click();
                            }
                            else{
                                $(window.opener.document).find("#secondForm")[0].click();
                            }
                            window.close();
                        }
                    }
                });
            }
        });
    })


    var val_text=$("#groupMembers").val();
    if(val_text !=undefined){
        if(val_text.indexOf(",") > 0) {
            var result = val_text.split(",");
            for (var i = 0; i < result.length; i++) {
                $(".txtValue1").children("option").each(function () {
                    if ($(this).val() == result[i]) {
                        $(this).prop("selected", "selected");
                    }//每一个option
                });
            }
        }else{
            $(".txtValue1").children("option").each(function () {
                if ($(this).val() == val_text) {
                    $(this).prop("selected", "selected");
                }//每一个option
            });
        }
    }

    $(".select2_multiple").select2({
        width:'100%'
    });

});
