/**
 * 宣传报道管理
 * Created by admin on 2016/9/3.
 */
define(function(require,exports,module){

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//使用dateFormat日期格式转换
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    check.checkAll("body", ".checkAll", ".checkBtn")

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_propagandaManagement_v").length>0) {
            target=$("#syy_propagandaManagement_v");
            var tpl = require("text!app/tpl/partyGroup/syy_dq_lc06_v_Tpl.html");
            form.render(target,tpl);
        }
    }


    exports.pageLoad=function(){
        loadForm();
    };

    $("#appform").validate({

        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#propagandaManagementSubmit").attr("data-url");

            $.ajax({
                url:_url+"?t="+new Date().getMilliseconds(),
                type:"post",
                data:$("#appform").serializeObject(),
                success:function(data){
                    if(data.isSuccess){
                        alert("新增成功！");
                        window.close();
                    }
                    else
                        alert(data.msg);
                }
            })
        }
    })


    $(document).ready(function(){
        //$("#author").val($("input[name='applyerName']").val());
        //$("#unit").val($("input[name='deptName']").val());
    })



    load11();
    $("#proManageQueryBtn").click(function () {
        load11();
    })
    function load11(){
        var url = $("#searchForm").attr("data-url");
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc06_v_count_Tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);

        var deptNo = $("#hiddenDeptNo").val();
        if (deptNo != "02YZBGS"){
            $(".Js_operation").addClass("hidden");
        }else{
            $(".Js_operation").removeClass("hidden");
        }

    }


//弹窗
    var callba = function callback(){
        $(".editPropagandaManagement").validate({
            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url =$("#updateCountDetail").attr("data-url");
                $.ajax({
                    url:_url+"?t="+new Date().getMilliseconds(),
                    type:"post",
                    data:$(".editPropagandaManagement").serializeObject(),
                    success: function (data) {
                        if(data.isSuccess){
                            alert("修改成功！");
                            $("#myModal").modal("hide");
                            load11();
                        }
                    }
                })
            }
        })
    };
    widget.init(callba);//不能写成widget.init(callba());    不能进行方法调用

    $(".deleteProManagement").on("click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url:_url,
            type:'post',
            success: function (data) {
                if(data){
                    alert("删除成功！");
                    load11();
                }
            }
        })
    })


    //下载
    $(".Js_proManageCountDown").on("click",function(){
        var data = encodeURI($("#searchForm").serialize().toString());
        var _url = "ajax_syy_dq_lc06_a_count_down?"+data;
        $(this).attr("href",_url);

    })




})
