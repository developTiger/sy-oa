/**
 * Created by user on 2016/8/5.
 */
define(function (require, exports, module) {

    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    check.checkAll("body", ".checkAll", ".checkBtn");
    require("mutiSelect");


    //点击按钮将数据传给后台  审批拒接
    $("body").delegate("#projectno1", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null||( $("#hid1").val()==null||($("#hid1").val()).length<=0)){
            alert("请勾选项目");
            return false;
        }


        ajax_mainProjectNO1(chk_value.toString());
    })

    function ajax_mainProjectNO1(selectids) {
        var options = {
            url: "ajax_mainProjectNO1?t=" + new Date().getMilliseconds() + "&ids=" + selectids ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("签核拒绝成功");
                    window.location.reload();
                }else{
                    alert("签核拒绝失败");
                }

            }
        };
        $.ajax(options);
    }




//取消按钮退出模态
    $("body").delegate("#cancelModal_1", "click", function () {
        $("#myModal_1").modal("hide")
    });
    $("body").delegate("#cancelModal_2", "click", function () {
        $("#myModal_2").modal("hide")
    });

    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        if(chk_value.toString().length <= 0||chk_value.toString()==null){
            alert("请勾选项目");
            return false;
        }

        ajax_mainProjectNO(chk_value.toString());
    })

    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_main_lc01_down?formNos="+selectids;
    }




      // var sssd= $(".proname").parent().parent().attr("data-tt-id")





    var sssd ;
    $("body").delegate(".proname","click",function(){
        sssd= $(this).parent().parent().attr("data-tt-id");
        $("#hid3").val(sssd);
    })
    $("body").delegate(".projectok", "click", function () {
        var htmlname="";
        var htmlid="";
        $(".leaders option:selected").each(function(){
            htmlname+=$(this).text()+",";
            htmlid += $(this).val()+",";
        });

        var s=htmlname.substring(0,htmlname.length-1);
        var d=htmlid.substring(0,htmlid.length-1);
        $("#hid2").val(s);
       /* var htmlid = $(".leaders").multiselect("getChecked").map(function(){
            return this.value;
        }).get();*/

        $("#hid1").val(d);


        var saa=  $("#hid1").val();
        var sname=$("#hid2").val();
        ajax_mainLeadName(saa,sname,sssd);
    })


    function ajax_mainLeadName(saa,sname,sformNO) {


        var options = {
            url: "ajax_mainProjectLeadName?t=" + new Date().getMilliseconds() + "&ids=" + saa + "&sname=" + encodeURI(encodeURI(sname)) + "&sformNO=" + sformNO ,
            type: 'get',
            success: function (data) {
                if(data=="success"){
                    alert("选择成功");
                }else{
                    alert("选择失败");
                }

            }
        };
        $.ajax(options);
    }


   /* $("body").delegate(".leaders", "change", function () {
        var htmlname = "";
        var htmlid = "";
        $(".leaders option:selected").each(function () {
            htmlname += $(this).text() + ",";
            htmlid += $(this).val() + ",";
        });

        var s = htmlname.substring(0, htmlname.length - 1);
        var d = htmlid.substring(0, htmlid.length - 1);
        var sid = $("#hid11").val(d);
        var sname = $("#hid22").val(s);
    })*/

})