/**
 * Created by pxj on 2016/9/23.
 */
define(function (require, exports, module) {
    var List;
    List = require("../common/pagelist");
    require("../common/templeteHelper");
    require("ajaxUpload");
    require("../common/jquery.serializeObject");
    require('tagsinput');
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
   // var stpl = require("text!app/tpl/userAchievements/selectTpl.html");
   // var CG = require("../common/resultCG");
   // require("select2");
    /*$(".select2_group").select2({
        width:'100%'
    });*/
    $('#winner_Info').tagsInput({
        width: 'auto'
    });
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("body").delegate("#add_p","click",function(){
        $("#myModal").modal("show");
    });
    $("body").delegate("#back","click",function(){
        window.close();
    });

   // loadData11();
/*    $("#queryEmps").click(function () {
        loadData11();
    });
    function loadData11(){
        var tpl = require("text!app/tpl/awards/syy_gy_lc02_tpl2.html");
        var url= "ajax_query_addEmps1";
        var name = $("#empName").val();
        var dept = $("#deptName").val();
        List("#table",tpl,url,"empName="+encodeURI(name)+"&deptName="+dept,1,10);
    }*/
   // loadDate2();
    function loadDate2(){
        var info=$('#info').val();
        //alert(info);
        //var emps = $(this).val();
        //var emps2 = $(this).next().val();
        if(info==""){
            return;
        }
        var aaa=info.split(",");
        for(var i=0;i<aaa.length;i++){
            var bbb=aaa[i].split("@");
            //var abc=$('#abc').val();
            var def=$('#def').val();
            $('#winner_Info').addTag(bbb[1]);
            if(def==""){
                //abc=bbb[0]+"@"+emps2;
                def=bbb[0]+"@"+bbb[1];
            }else{
                //abc=abc+","+bbb[0]+"@"+emps2;
                def=def+","+bbb[0]+"@"+bbb[1];
            }
            //$('#abc').val(abc);
            $('#def').val(def);
        }

    }
    $("body").delegate(".addSingleEmp1","click", function () {
        var emps = $(this).val();
        var emps2 = $(this).next().val();
        //var abc=$('#abc').val();
        var def=$('#def').val();
        if(def !=""){
            var aaa=def.split(",");
            for(var i=0;i<aaa.length;i++){
                var bbb=aaa[i].split("@");
                for(var j=0;j<bbb.length;j++){
                    if(bbb[0]==1 && bbb[1]==emps){
                        return;
                    }
                }
            }
        }
        $('#winner_Info').addTag(emps);
        if(def==""){
            //abc="1@"+emps2;
            def="1@"+emps;
        }else{
            //abc=abc+","+"1@"+emps2;
            def=def+","+"1@"+emps;
        }
        //$('#abc').val(abc);
        $('#def').val(def);
    });
    //$('#myModal_1').on('hide.bs.modal', function () {
    //
    //});
    $(function () {
        //$('[data-type="checkbox"]').click(function () {
        //
        //    if (!$(this).prop("checked") && $('[data-type="checkall"]').prop("checked")) {
        //        $('[data-type="checkall"]').prop("checked", false);
        //    }
        //    var checkedNum = 0;
        //    $('[data-type="checkbox"]').each(function (i) {
        //        if ($('[data-type="checkBox"]:eq(' + i + ')').prop("checked"))
        //            checkedNum++;
        //    });
        //    //全部选中即为全选
        //    if ($('[data-type="checkbox"]').length == checkedNum) {
        //        $('[data-type="checkall"]').prop("checked", true);
        //    }
        //
        //    var data_value = $(this).attr('data-value'),
        //        txtalso = $.trim($(".txtValue").val()),
        //        id_value = $(this).val()
        //    ids = $.trim($(".txtValue").attr("data-value"));
        //    //alert(ids);
        //    //alert(txtalso);
        //    if ($(this).prop("checked")) {
        //        if (txtalso.length > 0) {
        //            if (txtalso.indexOf(data_value + ',') != -1) {
        //                return;
        //            } else {
        //                txtalso += data_value + ',';
        //            }
        //        } else {
        //            txtalso = data_value + ',';
        //        }
        //        if (ids.length > 0) {
        //            if (ids.indexOf(id_value + ',') != -1) {
        //                return;
        //            } else {
        //                ids += id_value + ',';
        //            }
        //        } else {
        //            ids = id_value + ',';
        //        }
        //    } else {
        //        if (txtalso.indexOf(data_value + ',') != -1) {
        //            txtalso = txtalso.replace(data_value + ',', '');
        //        }
        //        if (ids.indexOf(id_value + ',') != -1) {
        //            ids = ids.replace(id_value + ',', '');
        //        }
        //    }
        //    $(".txtValue").val(txtalso);
        //    $(".txtValue").attr("data-value", ids);
        //    //alert(ids);
        //});
        //$('[data-type="checkall"]').click(function () {
        //    var str = '';
        //    var ids = "";
        //    if ($(this).prop("checked")) {
        //        $.each($('[data-type="checkbox"]'), function (i) {
        //            str += $(this).attr('data-value') + ',';
        //            ids += $(this).val() + ',';
        //        });
        //        $('[data-type="checkbox"]').prop('checked', true);
        //    } else {
        //        $('[data-type="checkbox"]').prop('checked', false);
        //    }
        //    $(".txtValue").val(str);
        //    $(".txtValue").attr("data-value", ids);
        //});
    });

    //取消按钮
    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal_1").modal("hide");
    });

    //状态为维护保养时，显示录入
    $("body").delegate("#firstSelect", "change", function () {
        if (this.value == '0') {
            $('#firstInput').show();
        } else {
            $('#firstInput').hide();
        }
    });

/* 动态显示合著时显示其他合著人
$("body").delegate("#is_cooperate","change",function () {
         if(this.value=="true"){
            $('#winner_Info_Show').show();
         }else{
             $('#winner_Info_Show').hide();
         }
    });*/
    //var count = 0;
    //$("body").delegate("#secondDelete", "click", function () {
    //    $("#second").hide();
    //})
    //$("body").delegate("#thirdDelete", "click", function () {
    //    $("#third").hide();
    //})

    //function setUserStaus(selectids, isActive) {
    //    var options = {
    //        url: "ajax_setUserStstus?t=" + new Date().getMilliseconds(),
    //        type: 'post',
    //        data: {isActive: isActive, ids: selectids},
    //        success: function (data) {
    //            if (data == "success") {
    //                alert("修改成功");
    //                loadData();
    //            }
    //            else
    //                alert(data);
    //        }
    //    };
    //    $.ajax(options);
    //}

    //用ajax加载数据,利用变量直接将方法赋值上去，然后放进去
    /*var emps = {};
     $.ajax({
     url: "ajax_get_all_emp?t=" + new Date().getMilliseconds(),
     type: 'get',
     success: function (data) {
     emps = data;
     }
     });*/

    //$("body").delegate(".selectdd", "change", function () {
    //    if ($(this).val() == "0@") {
    //        $(this).parent().next().removeClass("hidden")
    //    } else {
    //        $(this).parent().next().addClass("hidden")
    //    }
    //})


    ////动态添加下拉列表
    //var addNum = 1;
    //$(function () {
    //    $("body").delegate("#add", "click", function () {
    //        addNum++;
    //        var data = {empinfo: emps, addNum: addNum};
    //        var html = template.compile(stpl)(data);
    //        $("#addOptions").append(html);
    //    });
    //
    //    //点击删除按钮触发事件，删除整个div
    //    $("body").delegate(".test", "click", function () {
    //        var that = this;
    //        var current_num = parseInt($(this).attr("data-num"));
    //        $(that).parent().parent().remove();
    //        $(".selectdd").each(function () {
    //            var tt = $(this);
    //            var thisSeq = parseInt(tt.attr("seq"));
    //            if (thisSeq > current_num) {
    //                tt.attr("seq", thisSeq - 1);
    //                var par = tt.parent().parent();
    //                par.find(".bbb").html(thisSeq - 1);
    //                par.find(".test").attr("data-num", thisSeq - 1)
    //            }
    //        })
    //
    //        addNum--;
    //        //for (var i = current_num + 1; i <= addNum; i++) {
    //        //    $("#" + i).children(".control-label").html("第" + (i - 1) + "著作人 <span class='required'>*</span>");
    //        //    $("#" + i + "_Select").attr("id", (i - 1) + "_Select");
    //        //    $("#" + i + "_Delete").attr("id", (i - 1) + "_Delete");
    //        //    $("#" + i).attr("id", i - 1);
    //        //}
    //        //addNum--;
    //    })
    //
    //});


    //$("body").delegate("#firstSelect", "change", function () {
    //    if ($(this).val() == "0@") {
    //        $("#firstInput").show();
    //    } else {
    //        $("#firstInput").hide();
    //    }
    //});


    //新增著作人
    //$("body").delegate("#addTreatiseInfo", "click", function () {
    //    var list = [];
    //    var text = '';
    //    var data = '';
    //    var msg = '';
    //    var alldone = true;
    //    var a = 0;
    //    $(".selectdd").each(function () {
    //
    //        if ($(this).val() == "") {
    //            alert("请选择论著人！")
    //            alldone = false;
    //            return false;
    //        }
    //
    //        var index = list.indexOf($(this).val());
    //        if ($(this).val() == "0@") {
    //            if ($(this).parent().next().find("input").val() == "") {
    //                alert("其他论著人为必填！")
    //                alldone = false;
    //                return false;
    //            }
    //            var oth = "0@" + $(this).parent().next().find("input").val();
    //            index = list.indexOf(oth)
    //        }
    //        if (index >= 0) {
    //            var sq = index + 1;
    //            msg = msg + "第" + sq + "与第" + $(this).attr("seq") + ",";
    //            list.push("err");
    //        } else {
    //
    //            if ($(this).find("option:selected").text() == "其他") {
    //                text = text + "0@" + (a++) + "@" + $(this).parent().next().find("input").val() + ",";
    //                data = data + $(this).parent().next().find("input").val() + ",";
    //                list.push($(this).val() + $(this).parent().next().find("input").val())
    //            }
    //            else {
    //                text = text + "1@" + (a++) + "@" + $(this).val() + ",";
    //                data = data + $(this).find("option:selected").text() + ",";
    //                list.push($(this).val())
    //            }
    //
    //        }
    //    })
    //    if (msg != '')
    //        alert(msg + "著作人重复");
    //    else {
    //        if (alldone) {
    //            $(".txtValue").val(data.substring(0, data.length - 1));//页面显示的值
    //            $("#hiddenInput").val(text);//传递到后台的值
    //
    //            $("#myModal_1").modal("hide");//按钮点击事件，隐藏弹窗
    //        }
    //    }
    //
    //
    //})

    $("#appform").validate({

        rules: {
         /*   first_Author:{
                required: true
            },*/
            level_Author:{
                required:true
            },
            treatise_Name: {
                required: true,
                maxlength: 50
            },
            publish_Time_: {
                required: true
            },
            treatise_Level: {
                required: true
                //digits:true
            },
            treatise_Press: {
                required: true,
                maxlength: 50
            },
            is_Core: {
                required: true

            },
            unit: {
                required: true,
                maxlength: 50
            },
            make_No: {
                required: true,
                maxlength: 50
            },
            is_cooperate: {
                required: true
            },
            treattise: {
                required: true
            }/*,
            winner_Info:{
                required:true
            }*/
        },
        messages: {
        /*    first_Author:{
                required: "必填"
            },*/
            treatise_Name: {
                required: "必填",
                maxlength: "不能超过50个字符"
            },
            treatise_Level: {
                required: "必须选择"
            },
            publish_Time_: {
                required: "必填"
            },
            treatise_Press: {
                required: "必填",
                maxlength: "不能超过50个字符"
            },
            is_Core: {
                required: "必须选择"
            },
            unit: {
                required: "必填",
                maxlength: "不能超过50个字符"
            },
            make_No: {
                required: "必填",
                maxlength: "不能超过50个字符"
            },
            is_cooperate: {
                required: "必须选择"
            },
            treattise: {
                required: "必填"
            }/*,
            winner_Info:{
                required:"必填"
            }*/

        },

        submitHandler: function (form) {
            /*           //表单提交句柄,为一回调函数，带一个参数：form

             var _url = $("#addOrUpdatebook").attr("data-url");

             var options = {
             url: _url + "?t=" + new Date().getMilliseconds(),
             type: 'post',
             data: $("#appform").serializeObject(),
             success: function (data) {
             if (data.isSuccess) {
             alert("新增成功");
             window.close();
             //window.location.href = "sra_u_books";//链接返回到显示页面
             }


             }
             };
             $.ajax(options);*/
            //表单提交句柄,为一回调函数，带一个参数：form
           // alert($("#first_Author").val());
      /*      if($("#is_cooperate").find("option:selected").text()=="是"){
               // alert($("#def").val());
                if($("#def").val()==""){
                    alert("请选择其他合著人");
                }else{
                    judgement();
                }
             }else {
                judgement();
            }*/
            $(":submit").button("loading");
            setTimeout(function () {$(":submit").button("reset") }, 10000);
            judgement();
        }
    });

    
    function  judgement() {
        if ($("#awards_Type").find("option:selected").text()=="请选择") {
            alert("请选择论著类型");
        } else {
            var _url = $("#addOrUpdatebook").attr("data-url");
            var fileIds = [];
            $("input[name='fileName']").each(function () {
                fileIds.push($(this).attr("id"));
            });
            var judge=$("#beans_back_judge").val();
            //alert(judge);
            $.ajaxFileUpload({
                url: _url,
                secureuri: false,
                fileElementId: fileIds,
                dataType: 'text',
                data: $("#appform").serializeObject(),
                success: function (data, status) {
                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                    if (result.isSuccess) {
                        alert("新增成功！");
                    /*    if(judge){
                            history.go(-1);
                        }else {
                            window.close();
                        }*/
                        if($(window.opener.document).find(".pagination li.active a")[0]) {
                            $(window.opener.document).find(".pagination li.active a")[0].click();
                        }
                        else {
                            $(window.opener.document).find("#secondForm")[0].click();
                            //  $(window.opener.document).find(".pagination li.active a")[0].click();

                        }
                        window.close();
                    } else {
                        alert(result.msg);
                        setTimeout(function () {$(":submit").button("reset") }, 10);
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('新增失败，请重试！！');
                }
            });

        }
    }
    
    //新增
    /* $("#addBooksInfoForm").validate({

     submitHandler: function (form) {
     //表单提交句柄,为一回调函数，带一个参数：form

     var _url = $("#addOrUpdatebook").attr("data-url");

     var options = {
     url: _url + "?t=" + new Date().getMilliseconds(),
     type: 'post',
     data: $("#addBooksInfoForm").serializeObject(),
     success: function (data) {
     if (data == "success")
     alert("新增成功");

     }
     };
     $.ajax(options);
     }

     });*/




    //添加人员，弹出对话框初始化
    //$("body").delegate("#add_p","click",function(){
    //    var win_main = (new Function("","return " + $("#sa").val()))();
    //    var data = $.trim($("#hiden_in").val());
    //    CG.init(win_main,$(".staff-rows"),data);
    //    $("#myModal").modal("show");
    //});

    //提交按钮
    //$("body").delegate("#getval", "click", function () {
    //
    //    var obj = CG.vail($(".special-sele"));
    //    if(obj.err){
    //        CG.concat_data($(".special-sele"),$("#hiden_in"),$("#winner_Info"),$("#hiden_in_sb"));
    //        $("#myModal").modal("hide");
    //    }else{
    //        alert("第 " + obj.info + " 获奖人数据未选择或者填写或者获奖人姓名不能超过20个字!");
    //    }
    //
    //});

    //取消按钮
    //$("body").delegate("#cancelModal_1", "click", function () {
    //    $("#myModal").modal("hide");
    //});

    //新增专利人员按钮
    //$("body").delegate("#addStaff", "click", function () {
    //    var win_main = (new Function("","return " + $("#sa").val()))();
    //    CG.add_staff(win_main,$(".staff-rows"));
    //});

    //删除专利人员按钮
    //$("body").delegate(".delStaff", "click", function () {
    //    var win_main = (new Function("","return " + $("#sa").val()))();
    //    CG.del_staff(win_main,$(this));
    //});


    //选择事件selected
    //$("body").delegate(".special-sele","change",function(){
    //    var win_main = (new Function("","return " + $("#sa").val()))();
    //    CG.change_s(win_main,$(this));
    //});

    var id = 1;
    var tpl_files = require("text!app/tpl/userAchievements/syy_gy_lc04_uploadFileTpl.html");
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

    //退回附件删除
    $("body").delegate(".deleteupFileBtn","click",function(){
        $(this).parent().remove();
    });
})
