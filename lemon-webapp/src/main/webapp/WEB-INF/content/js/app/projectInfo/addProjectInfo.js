define(function (require, exports, module) {

    require('../init');
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");

    var widget = require("../common/widget");
    require("jquery");
    require("mutiSelect");
    $(document).ready(function() {
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 100
        });
    });



        /*var _url = $("#addOrUpdateGroupMembers").attr("data-url");

        var options = {
            url: _url + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#addGroupMembersInfoForm").serializeObject(),
            success: function (data) {
                if (data == "success")
                    alert("新增成功");
                $("#myModal").modal("hide")
                loadData();
            }
        };
        $.ajax(options);*/



    //$("#membersTanChuang").focus(function(){
    //    widget.showModal("_addGroupMembers","选择组员",null);
    //    widget.init(callb());
    //});
    //时间插件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd'});
    });

    //新增
        $("#addProjectInfoForm").validate({

            rules: {
                projectNo: {
                    required: true,
                    maxlength:50
                },
                projectName:{
                    required:true,
                    maxlength:50
                    //digits:true
                },
                specialtyType: {
                    required: true
                },
                forSpecialtyType:{
                    required:true,
                    maxlength:50
                },
                projectPlanInfo:{
                    required:true,
                    maxlength:50
                },
                assumeCompany:{
                    required:true,
                    maxlength:50
                },
                joinComopany:{
                    required:true,
                    maxlength:50
                },
                beginTime1:{
                    required:true
                },
                endTime1:{
                    required:true

                },
                remark:{
                    required:true,
                    maxlength:50
                },
                projectType:{
                    required:true
                },
                leader:{
                    required:true
                },
                deputy:{
                    required:true
                },
                remark:{
                    required:true,
                    maxlength:50
                },
                dept:{
                    required:true,
                    maxlength:50
                },
                groupMemberList1:{
                    required:true
                }
            },
            messages: {
                projectNo: {
                    required: "必填",
                    maxlength:"长度不能超过50个字符"
                },
                projectName:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                specialtyType:{
                    required:"必须选择"
                },
                forSpecialtyType:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                projectPlanInfo:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                assumeCompany:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                joinComopany:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                beginTime1:{
                    required:"必填选择"

                },
                endTime1:{
                    required:"必须选择"
                },
                remark:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                projectType:{
                    required:"必须选择"
                },
                leader:{
                    required:"必须选择"
                },
                deputy:{
                    required:"必须选择"
                },
                joinComopany:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                dept:{
                    required:"必填",
                    maxlength:"长度不能超过50个字符"
                },
                groupMemberList1:{
                    required:"必填"
                }


            },

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form

                var _url = $("#addOrUpdateProjectInfo").attr("data-url");

                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addProjectInfoForm").serializeObject(),
                    success: function (data) {
                        if (data == "success")
                            alert("新增成功");
                        window.location.href="sra_p_project";
                    }
                };
                $.ajax(options);
            }

        });




        //取消按钮
        $("body").delegate("#cancelModal", "click", function () {
            $("#myModal").modal("hide")
        });

        $("body").delegate("#cancelModal_1", "click", function () {
            $("#myModal_1").modal("hide")
        });




    $('#myModal_1').on('hide.bs.modal', function () {

    });
    $(function(){
        $('[data-type="checkbox"]').click(function(){

            if(!$(this).prop("checked") && $('[data-type="checkall"]').prop("checked")){
                $('[data-type="checkall"]').prop("checked",false);
            }
            var checkedNum = 0;
            $('[data-type="checkbox"]').each(function(i){
                if($('[data-type="checkbox"]:eq('+i+')').prop("checked"))
                    checkedNum++;
            });
            //全部选中即为全选
            if($('[data-type="checkbox"]').length == checkedNum){
                $('[data-type="checkall"]').prop("checked",true);
            }


            var data_value = $(this).attr('data-value'),
                txtalso = $.trim($(".txtValue").val()),
                id_value = $(this).val()
                ids = $.trim($(".txtValue").attr("data-value"));
            //alert(ids);
            //alert(txtalso);
            if($(this).prop("checked")) {
                if(txtalso.length > 0) {
                    if(txtalso.indexOf(data_value+',') != -1) {
                        return ;
                    } else {
                        txtalso += data_value + ',';
                    }
                } else {
                    txtalso = data_value+',';
                }
                if(ids.length > 0) {
                    if(ids.indexOf(id_value+',') != -1) {
                        return ;
                    } else {
                        ids += id_value + ',';
                    }
                } else {
                    ids = id_value + ',';
                }
            } else {
                if(txtalso.indexOf(data_value+',') != -1) {
                    txtalso = txtalso.replace(data_value+',', '');
                }
                if(ids.indexOf(id_value+',') != -1) {
                    ids = ids.replace(id_value+',', '');
                }
            }
            $(".txtValue").val(txtalso);
            $(".txtValue").attr("data-value",ids);
            //alert(ids);
        });
        $('[data-type="checkall"]').click(function(){
            var str = '';
            var ids = "";
            if($(this).prop("checked")) {
                $.each($('[data-type="checkbox"]'), function(i){
                    str += $(this).attr('data-value') + ',';
                    ids += $(this).val() + ',';
                });
                $('[data-type="checkbox"]').prop('checked', true);
            } else {
                $('[data-type="checkbox"]').prop('checked', false);
            }
            $(".txtValue").val(str);
            $(".txtValue").attr("data-value",ids);
        });
    });






})
