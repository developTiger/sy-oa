/**
 * Created by Administrator on 2016/7/5 0005.
 */
define(function (require, exports, module) {
    require('../init');
    List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    //require("chained");
    var template = require("template");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
  /*  var CG = require("../common/resultCG");*/
    var widget = require("../common/widget");
    require('tagsinput');
    require('treeTable');

    loadData11();
    $("#queryEmps").click(function () {
        loadData11();
    });

    $('#winner_vali').tagsInput({
        width: 'auto'
    });
    var myWidGet = require("../common/myWidGet");
    $("body").delegate(".addSingleEmp1","click", function () {
        var emps = $(this).val();
        $('#winner_vali').addTag(emps);
        //$('#winner_vali').tagsInput({
        //    width: 'auto'
        //});
    });

    function loadData11(){
        var tpl = require("text!app/tpl/awards/syy_gy_lc02_tpl.html");
        var url= "ajax_query_addEmps1";
        var name = $("#empName").val();
        var dept = $("#deptName").val();
        List("#table",tpl,url,"empName="+encodeURI(name)+"&deptName="+dept,1,10);

    }







    //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            skin:"default",
            readOnly:true
        });
    });
    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    form.init();
/*
    $(document).ready(function(){
        $("textarea").focus(function(){

        });
    });
*/

        $("#appform").validate({
            rules: {
                patent_Name:{
                    required: true,
                    rangelength:[1,50]
                },
                app_No:{
                    required: true,
                    rangelength:[1,50]
                },
                patent_Type:{
                    required: true
                },
                apply_Date:{
                    required: true
                },
                valid_Date:{
                    required: true
                }
                ,
                winner_vali:{
                    required: true
                }
            },
            messages: {
                patent_Name:{
                    required:"请填写公司名称",
                    rangelength:""
                },
                app_No:{
                    required:"请填写专利申请号",
                    rangelength:"不能超过50个字符"
                },
                patent_Type:{
                    required: "请选择类型"
                },
                apply_Date:{
                    required: "请填写专利申请时间"
                },
                valid_Date:{
                    required: "请填写专利有效期"
                }
                ,
                winner_vali:{
                    required: "请添加专利发明人"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdatePatent").attr("data-url");
                console.info($("#appform").serializeObject());
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    data: $("#appform").serializeObject(),
                    type: 'POST',

                    success: function (data) {

                        if (data.isSuccess) {
                            alert("新增成功");
                            window.close();
                        }
                    }
                });
            }
        });




    //添加人员，弹出对话框初始化
    $("body").delegate("#add_p","click",function(){
        /*var win_main = (new Function("","return " + $("#sa").val()))();
        var data = $.trim($("#hiden_in").val());
        CG.init(win_main,$(".staff-rows"),data);*/
        $("#myModal").modal("show");
    });

    /*//提交按钮
    $("body").delegate("#getval", "click", function () {

        var obj = CG.vail($(".special-sele"));
        if(obj.err){
            CG.concat_data($(".special-sele"),$("#hiden_in"),$("#winner_Info"),$("#hiden_in_sb"));
            $("#myModal").modal("hide");
        }else{
            alert("第 " + obj.info + " 获奖人数据未选择或者填写或者获奖人姓名不能超过20个字符!");
        }

    });

    //取消按钮
    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal").modal("hide");
    });

    //新增专利人员按钮
    $("body").delegate("#addStaff", "click", function () {
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.add_staff(win_main,$(".staff-rows"));
    });

    //删除专利人员按钮
    $("body").delegate(".delStaff", "click", function () {
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.del_staff(win_main,$(this));
    });


    //选择事件selected
    $("body").delegate(".special-sele","change",function(){
        var win_main = (new Function("","return " + $("#sa").val()))();
        CG.change_s(win_main,$(this));
    });*/

})