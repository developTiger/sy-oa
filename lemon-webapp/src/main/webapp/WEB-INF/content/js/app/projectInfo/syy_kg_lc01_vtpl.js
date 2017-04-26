define(function (require, exports, module) {
    require("jquery");
    require("select2");
    require("mutiSelect");
    require("ajaxUpload");
    require('../init');
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var check = require("../common/checkbox");
    var template = require("template");
    var widget = require("../common/widget");

    $(document).ready(function () {
        $('#groupMemberList').multiselect({
            includeSelectAllOption: true,
            maxHeight: 100
        });
    });

    $('.leaders').select2({
        placeholder: "请选择",
        width: '100%'
    });



    $("body").delegate("#deputy", "change", function () {
        $("#deputy option:selected").each(function () {
            $('#deputyHid').val(($(this).val())) ; //这里得到的就是
        });

    })


    //时间插件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd'});
    });
    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal_1").modal("hide")
    });

    $("body").delegate(".txtValue1","change",function() {
        $(".txtValue1").children("optgroup").children("option").each(function(){
            if($(this).prop('selected')){
                var groupMemberList=$(".txtValue1").val();
                $("#groupMembers").val(groupMemberList);
            }//每一个option
        })
    })
//获取专家
    $("body").delegate(".leaders", "change", function () {
     /*   var htmlname = "";
        var htmlid = "";
            htmlname = $(".leaders option:selected") .text() ;
            htmlid =  $(".leaders option:selected") .val() ;

*/
        var selectId = [];
        var selectName=[];
        $(".leaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidid").val(selectId);
        var sid = $("#hidname").val(selectName);
    })
//获取主管领导
    $("body").delegate(".zhuleaders", "change", function () {

        var selectId = [];
        var selectName=[];
        $(".zhuleaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidleadid").val(selectId);
        var sid = $("#hidleadname").val(selectName);
    })

    var chk_value = [];
    $('.checkBtn:checked').each(function () {
        chk_value.push($(this).val());
    });
    var ss=chk_value.toString();


    $("body").delegate("#upload", "click", function () {
        var formNo = $("#formNo").val();
        var fileIds = [];
        $("input[name='fileName']").each(function(){
            fileIds.push($(this).attr("id"));
        });
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc01_ApproveUpload',
            secureuri: false,                       //是否启用安全提交,默认为false
            fileElementId: fileIds,           //文件选择框的id属性
            dataType: 'text',                       //服务器返回的格式,可以是json或xml等
            data: {formNo:formNo},
            success: function (data, status) {        //服务器响应成功时的处理函数
                var resultStart = data.indexOf("{");
                var resultEnd = data.indexOf("}");
                var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                    alert("上传文件成功！");
                } else {
                    alert(result.msg);
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    })


    var val_text=$("#groupMembers").val();
    if(val_text!=null){
      if(val_text.indexOf(",") > 0) {
          var result = val_text.split(",");
          for (var i = 0; i < result.length; i++) {
              $(".txtValue1").children("optgroup").children("option").each(function () {
                  if ($(this).val() == result[i]) {
                      $(this).prop("selected", "selected");
                  }//每一个option
              });

          }
      }else{
          $(".txtValue1").children("optgroup").children("option").each(function () {
              if ($(this).val() == val_text) {
                  $(this).prop("selected", "selected");
              }//每一个option
          });
      }
    $(".select2_multiple").select2({
        placeholder: "请选择",
        width:'100%'
    });
    }

    $('.zhuleaders').select2({
        placeholder: "请选择",
        width: '100%'

    });

})

