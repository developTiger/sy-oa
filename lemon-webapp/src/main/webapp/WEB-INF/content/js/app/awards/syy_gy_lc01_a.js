/**
 * Created by MJ003 on 2016/8/26.
 */
/**
 * Created by user on 2016/7/5.
 */
define(function (require, exports, module) {
    var List;
    require('../init');
    List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    require("chained");
    var template = require("template");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var widget = require("../common/widget");
    require('tagsinput');
    require('treeTable');
    var myWidGet = require("../common/myWidGet");
    check.checkAll("body", ".checkAll", ".checkBtn");
    $('#winner_Info').tagsInput({
        width: 'auto'
    });

    //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            skin:"default",
            readOnly:true
        });
    });
    $(function() {
        $("#series").chained("#mark");
    });
    $("body").delegate("#add_p","click",function(){
        $("#myModal").modal("show");
    });
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
        var emps = $(this).val();
        var emps2 = $(this).next().val();
        var abc=$('#abc').val();
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
        if(abc==""){
            abc="1@"+emps2;
            def="1@"+emps;
        }else{
            abc=abc+","+"1@"+emps2;
            def=def+","+"1@"+emps;
        }
        $('#abc').val(abc);
        $('#def').val(def);
    });
    $("#appform").validate({
        rules: {
            win_Result_Name:{
                required: true,
                rangelength:[1,50]
            },
            //winner_vali:{
            //    required: true,
            //    rangelength:[1,500]
            //},
            issuing_Unit:{
                required: true,
                rangelength:[1,50]
            },
            certif_No:{
                required: true,
                rangelength:[1,50]
            },
            win_Result_Classify:{
                required: true
            },
            win_Result_type:{
                required: true
            },
            win_Level:{
                required: true
            },
            win_Grade:{
                required: true
            },
            win_Date:{
                required: true
            },
            is_Cooperate_Result:{
                required: true
            }
        },
        messages: {
            win_Result_Name:{
                required: "请填写获奖成果名称",
                rangelength:"不能超过50个字符"
            },
            //winner_vali:{
            //    required: "请添加获奖人",
            //    rangelength:"不能超过500个字符"
            //},
            issuing_Unit:{
                required: "请填写颁发单位",
                rangelength:"不能超过50个字符"
            },
            certif_No:{
                required: "请填写证书编号",
                rangelength:"不能超过50个字符"
            },
            win_Result_Classify:{
                required: "请选择获奖成果类型"
            },
            win_Result_type:{
                required: "请选择奖项"
            },
            win_Level:{
                required: "请选择获奖级别"
            },
            win_Grade:{
                required: "请选择获奖等级"
            },
            win_Date:{
                required: "请选择获奖时间"
            },
            is_Cooperate_Result:{
                required: "请选择是否合作"
            }
        },
        submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateProjectResult").attr("data-url");
            //alert($('#winner_Info').val());
            /!* alert(_url);*!/
            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {
                    //alert(data);
                    console.log(data);
                    if (data.isSuccess){
                        alert("提交成功");
                        //$("#myModal").modal("hide");
                        window.close();
                    }
                    //loadData();
                }
            };
            $.ajax(options);
        }
    });
})