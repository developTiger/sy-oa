define(function (require, exports, module) {

    require('../init');
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");


    require("jquery");
    require("mutiSelect");
    $(document).ready(function() {
        $('#groupMemberList').multiselect({
            includeSelectAllOption: true,
            maxHeight: 100
        });
    });


    $("#modifyProjectInfoForm").validate({

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
            var _url = $("#modifyOrupdateProjectInfo").attr("data-url");

            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#modifyProjectInfoForm").serializeObject(),
                success: function (data) {
                    if (data == "success")
                        alert("修改成功");
                    $("#myModal").modal("hide")
                    window.location.href="sra_p_project";
                    loadData();
                }
            };
            $.ajax(options);
        }

    });

    //时间插件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd'});
    });


    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal_1").modal("hide")
    });



    $('#myModal_1').on('hide.bs.modal', function () {

    });


    /*var txt ="";//文本框显示值
    var id ="";//前台传输到后台的id
    var num=0;//计数器

    $(document).ready(function(){
        $(".emptxt").each(function(){
            id = id + $(this).val()+",";
        })
    })

    $(document).ready(function(){
        $(".emptxt").each(function(){
            txt = txt + $(this).attr("data-value")+",";
        })
    })


    $(function(){
        $('[data-type="checkbox"]').click(function(){

            num++;

            *//*if(num == 1){
               txt =$("#backgroundName").val()+",";
                id = $("#backbroundId").val()+",";
                $(this).prop("checked").each(function(){
                    id = $(this).val();
                })
            }*//*

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

            var txtData = $(this).attr('data-value');//checkbox的值，传到前台显示
            var empId = $(this).val();//checkbox的值：id,需要传到后台保存

            if($(this).each(function(){
                    if($(this).prop("checked")){
                        id =id+ empId+",";
                        txt =txt+ txtData+",";
                    }else{
                        id = id.replace(empId+",","");
                        txt = txt.replace(txtData+",","");
                    }
                })) ;

            $(".txtValue").val(txt );//页面显示的值
            $("#txtData").val(id );//隐藏文本框的值，放id

        });




        $('[data-type="checkall"]').click(function(){
            var str = '';//页面需要显示的人员名称
            var ids = "";//传到后台的id
            if($(this).prop("checked")) {
                $.each($('[data-type="checkbox"]'), function(i){
                    str += $(this).attr('data-value') + ',';
                    ids += $(this).val() + ',';
                });
                $('[data-type="checkbox"]').prop('checked', true);
            } else {
                $('[data-type="checkbox"]').prop('checked', false);

            }

                $("#txtData").val(ids );//隐藏文本框的值，放id
                $(".txtValue").val(str);//页面显示的值


        });
    });

    //如果组员存在，就是checked
   *//* $("body").delegate(".btnSelect","click",function(){
        var empName = $(".txtValue").val()
        $(".emptxt").each(function(){
            if(empName.indexOf($(this).val()) >= 0){
                $(this).prop('checked', true);
            };
        })
    })*//*

    $("body").delegate(".btnSelect","click",function(){
        var empName = $(".txtValue").val();
        var num=0;//长度，用来判断全选
        $(".emptxt").each(function(){
            //单个复选框选中
            if(empName.indexOf($(this).attr("data-value")) >= 0){
                $(this).prop('checked', true);
                num++;
            }
        })
        //全部选中即为全选
        if($(".emptxt").length == num){
            $('[data-type="checkall"]').prop('checked', true);
        }
    })

    $(".txtValue").val(txt );//页面显示的值
    $("#txtData").val(id );//隐藏文本框的值，放id
*/




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

            var empId = $.trim($("#txtData").val());
            var txtData = $.trim($(".txtValue").val()) ;//用jQuery的trim方法删除前后空格
            //单个选中，不是全选
            $(this).each(function(i){
                if($(this).prop('checked')){
                    //alert($(this).val())
                    empId=empId+ $(this).val()+",";
                    txtData=txtData+ $(this).attr("data-value")+",";
                    if( i >0){
                        txtData=txtData+ $(this).attr("data-value")+",";
                    }

                }else{
                    //alert($(this).attr("data-value"))
                    empId=empId.replace($(this).val()+",","");
                    txtData=txtData.replace($(this).attr("data-value")+",","");
                }
            })

            $("#txtData").val(empId);//隐藏文本框的值，放id
            $(".txtValue").val(txtData);//页面显示的值

    });




    $('[data-type="checkall"]').click(function(){
        var str = '';//页面需要显示的人员名称
        var ids = "";//传到后台的id
        if($(this).prop("checked")) {
            $.each($('[data-type="checkbox"]'), function(i){
                str += $(this).attr('data-value') + ',';
                ids += $(this).val() + ',';
            });
            $('[data-type="checkbox"]').prop('checked', true);
        } else {
            $('[data-type="checkbox"]').prop('checked', false);

        }

        $("#txtData").val(ids );//隐藏文本框的值，放id
        $(".txtValue").val(str);//页面显示的值


        });
    });

    $("body").delegate(".btnSelect","click",function(){
        var empName = $(".txtValue").val();
        var num=0;//长度，用来判断全选
        var ids="";
        var id = ""
        $(".emptxt").each(function(){
            //单个复选框选中
            if(empName.indexOf($(this).attr("data-value")) >= 0){
                id = $(this).val();
                ids = ids + id+",";
                $(this).prop('checked', true);
                num++;
            }
        })

        $("#txtData").val(ids);//隐藏文本框的值，放id

        //全部选中即为全选
        if($(".emptxt").length == num){
            $('[data-type="checkall"]').prop('checked', true);
        }



    })



})
