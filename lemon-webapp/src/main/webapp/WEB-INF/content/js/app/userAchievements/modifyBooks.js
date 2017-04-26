/**
 * Created by liulin on 2016/7/6.
 */
define(function (require, exports, module) {

    var wDatePicker = require("wdatePicker");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    var CG = require("../common/resultCG");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    //审核页面加载
    var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />

    form.init();

    //用ajax加载数据,利用变量直接将方法赋值上去，然后放进去
    var emps = {};
    $.ajax({
        url: "ajax_get_all_emp?t=" + new Date().getMilliseconds(),
        type: 'get',
        success: function (data) {
            emps = data;
        }
    });

    $("body").delegate(".selectdd", "change", function () {
        if ($(this).val() == "0@") {
            $(this).parent().next().removeClass("hidden")
        } else {
            $(this).parent().next().addClass("hidden")
        }
    })



    $("body").delegate("#firstSelect", "change", function () {
        if ($(this).val() == "0@") {
            $("#firstInput").show();
        } else {
            $("#firstInput").hide();
        }
    });

    //修改提交
    $("#modifyBooksInfoForm").validate({

        rules: {
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
            }
        },
        messages: {
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
            }

        },

        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form

            var _url = $("#modifyBook").attr("data-url");

            var options = {
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#modifyBooksInfoForm").serializeObject(),
                success: function (data) {
                    if (data == "success") {
                        alert("修改成功");//
                        window.location.href = "sra_u_books";//链接返回到显示页面
                    }
                }
            };
            $.ajax(options);
        }
    });


    //添加人员，弹出对话框初始化
    $("body").delegate("#add_p","click",function(){
        var win_main = (new Function("","return " + $("#sa").val()))();
        var data = $.trim($("#hiden_in").val());
        CG.init(win_main,$(".staff-rows"),data);
        $("#myModal").modal("show");
    });

    //提交按钮
    $("body").delegate("#getval", "click", function () {

        var obj = CG.vail($(".special-sele"));
        if(obj.err){
            CG.concat_data($(".special-sele"),$("#hiden_in"),$("#winner_Info"),$("#hiden_in_sb"));
            $("#myModal").modal("hide");
        }else{
            alert("第 " + obj.info + " 获奖人数据未选择或者填写或者获奖人姓名长度不能超过20个字!");
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
    });

})

