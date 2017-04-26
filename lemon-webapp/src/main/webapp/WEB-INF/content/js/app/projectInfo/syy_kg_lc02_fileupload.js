/**
 * Created by pxj on 2016/10/12.
 */
define(function (require, exports, module) {
    require("../common/jquery.serializeObject");
    require("ajaxUpload");
    var validate = require("validate");
    //审核页面加载
    var form = require("../common/form");
    //初始化 添加验证 及 做提交
    //form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
    //form.init();
    form.init2(function(){
       $("#approveForm").validate({
           submitHandler: function (form) {
               //表单提交句柄,为一回调函数，带一个参数：form
               var _url = $("#approveUrl").val();
               var chk_value = [];
               var list_projectNo=[];
               chk_value.push($("*[name='formNo']").val());
               list_projectNo.push($("*[name='projectNo']").val());
               $("#formNo_OpenFlow").val(chk_value);
               $("#list_projectNo").val(list_projectNo);
               $.ajaxFileUpload({
                   //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                   url: _url,
                   secureuri: false,                       //是否启用安全提交,默认为false
                   fileElementId: ['upFile'],           //文件选择框的id属性
                   dataType: 'text',                       //服务器返回的格式,可以是json或xml等
                   data: $("#approveForm").serializeObject(),
                   success: function (data, status) {        //服务器响应成功时的处理函数
                       var resultStart = data.indexOf("{");
                       var resultEnd = data.indexOf("}");
                       var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                       if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                          // alert("上传文件成功！");
                          // location.reload();
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
                       }
                   },
                   error: function (data, status, e) { //服务器响应失败时的处理函数
                       $('#result').html('文件上传失败，请重试！！');
                   }
               });

           }
       });
    });
    $("body").delegate(".detial", "click", function () {
        //选中值formNo
        var formNoSel= $("#projectNamesel").val();
        //选中值name
        var pname =   $("#projectName").find("option:selected").text();
        var projectNo =$("#projectNo").val();
        // alert(projectNo);
        window.location.href="ajax_Project_Plan?projectNo="+projectNo;
    });
    $("body").delegate("#unit_Leadership_Summary","click",function () {
        //
        /*      $.ajax({
         url: 'unit_Leadership_Summary',
         type: 'post',
         data: $("#appform").serializeObject(),
         success: function (data) {
         if (data.isSuccess) {
         alert("新增成功");
         window.close();
         }
         }
         });*/

        window.location.href="unit_Leadership_Summary?clstep="+$("*[name='clStep']").val()+"&&formNo="+$("#formNo").val();

    });
});