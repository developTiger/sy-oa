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

    check.checkAll("body", ".checkAll", ".checkBtn")
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
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });
    $("body").delegate("#add_p","click",function(){
        $("#myModal2").modal("show");
    });

    loadData11();
    $("#queryEmps").click(function () {
        loadData11();
    });
    function loadData11(){
        var tpl = require("text!app/tpl/awards/syy_gy_lc02_tpl2.html");
        var url= "ajax_query_addEmps1";
        var name = $("#empName").val();
        var dept = $("#deptName").val();
        List("#table",tpl,url,"empName="+encodeURI(name)+"&deptName="+dept,1,10);
    }
    loadDate2();
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

    ////取消事件
    //$("body").delegate("#cancelModal_1","click",function(){
    //    sub_staff();
    //});
    ////审核页面加载
    var form = require("../common/form");
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
    form.init();

   $("#addProjectResultInfoForm").validate({
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
           /!* alert(_url);*!/
           var options = {
               url: _url + "?t=" + new Date().getMilliseconds(),
               type: 'post',
               data: $("#addProjectResultInfoForm").serializeObject(),
               success: function (data) {
                   if (data == "success")
                       alert("提交成功");

                   window.history.go(-1);

                   $("#myModal2").modal("hide")
                   loadData();
               }
           };
           $.ajax(options);
       }
   });

})