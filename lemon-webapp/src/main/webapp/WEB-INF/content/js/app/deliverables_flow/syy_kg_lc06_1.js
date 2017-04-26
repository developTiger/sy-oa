/**
 * Created by swb on 2016/8/30.
 */
define(function (require, exports, module) {
    require("jquery");
    var validate = require("validate");
    var check = require("../common/checkbox");
    var widget = require("../common/widget");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    require("../common/templeteHelper");//模板加载
    require("select2");
    $(".select_like").select2({});
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });
    //多选框 全选
    check.checkAll("body", ".checkAll", ".checkBtn");

    //审核页面加载
    var form = require("../common/form");
    form.init();
    jQuery.validator.addMethod("_character", function(value, element) {
        var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$|\、|\，|\,|\?|\。|\!|\！|\(|\)|\（|\）|\"|\"|\;|\“|\”|\；|\>|\<|\《|\》/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "只能为字母，数字，中文");
    $("#appform").validate({
        rules: {
            summery: {
                required: true,
                maxlength:500
            },
            subWords: {
                required: true,
                maxlength:22
            },
            briefly: {
                required: true,
                maxlength:300
            }
        },
        messages: {
            summery: {
                required: "必填",
                maxlength:"长度不能超过500个字符"
            },
            subWords: {
                required: "必填",
                maxlength:"长度不能超过22个字符"
            },
            briefly: {
                required: "必填",
                maxlength:"长度不能超过300个字符"
            }
        },
        submitHandler: function (form) {
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#addOrUpdateDeliverInfo").attr("data-url");

            $.ajax({
                url: _url + "?t=" + new Date().getMilliseconds(),
                type: 'post',
                data: $("#appform").serializeObject(),
                success: function (data) {

                    if (data.isSuccess) {
                        alert("新增成功");
                        window.close();
                    }
                }
            });
        }
    });
    $("body").delegate("#projectNamesel", "change", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        //alert(formNoSel);
        projectIdAndPlan(formNoSel);
    });
    function projectIdAndPlan(formNoSel) {
        var options = {
            url: "ajax_acceptanceId?t=" + new Date().getMilliseconds() + "&formNoSel=" + formNoSel ,
            type: 'get',
            success: function (data) {
                //var myData = eval("(" + data+")");
                //  console.log(data);
                // alert(data.projectPlanInfoTxt);
                $("#projectPlanInfoTxt").val(data.projectPlanInfoTxt);
                $("#projectNo").val(data.projectNo);
                $("#projectName").val(data.projectName);
                $("#deptName").val(data.deptName);
                $("#assumeCompany").val(data.assumeCompany);
                $("#joinComopany").val(data.joinComopany);
                $("#beginTime").val(data.beginTime);
                $("#endTime").val(data.beginTime);
            }
        };
        $.ajax(options);
    };
    //upload
    $("body").delegate("#uploaddeliver", "click", function () {
        var formNo = $("#formDeliverApplyDto").val();
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc06_Upload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: ['upFile'],           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {formNo:formNo},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                    $("#myModal").modal("hide");
                    loadData(2);
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    });
});
