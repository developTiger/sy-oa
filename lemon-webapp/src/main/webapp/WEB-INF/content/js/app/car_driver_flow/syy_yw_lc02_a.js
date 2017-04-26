/**
 * Created by swb on 2016/8/2.
 */
define(function (require, exports, module) {
    require("jquery");
    require("select2");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    require("../common/jquery.serializeObject");
    //var tpl = require("text!app/tpl/carflow/syy_yw_lc01_vTpl.html");
    require("../common/templeteHelper");
    //var widget = require("../common/widget");
    require("mutiSelect");
    require("ajaxUpload");
    require("printArea");
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    });

    var form =  require("../common/form")
    //初始化 添加验证 及 做提交

    form.init();

    //widget.init();

    /**
     * 渲染中间页面
     */
    var renderForm = function () {
        var target;
        var tpl;
        if ($("#syy_yw_lc02_v").length > 0) {
            target = $("#syy_yw_lc02_v");
            tpl = require("text!app/tpl/reception_flow/syy_yw_lc02_v_tpl.html");
        }
        form.render(target, tpl);


    }

    exports.pageLoad = function () {
        renderForm();
        $('#leaders').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '264px'
        });
        $('#deptName,#deptName1').select2({
            placeholder: "请选择",
            width: '100%'
        });

        $('#selEmp').multiselect({
            /*includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '264px'*/
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '264px',
            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true

        });
    };

    //打印
    $("body").delegate(".printBusiness","click",function(){
        $("#divPrint").printArea();
    });

    $("body").delegate(".driverId","change",function(){
        $("#driverName").val($(".driverId").text());
    });
    $("body").delegate(".carId","change",function(){
        $("#carNo").val($(".carId").text());
    });
    $("body").delegate("#leaders","change",function(){
        $("#hidLeader").val($("#leaders").val());
    });

    $("body").delegate("#selEmp","change",function(){
        var html="";
        $("#selEmp option:selected").each(function(){
            html+=$(this).text()+',';
        });
        $("#personComp").val(html);
    });

    $("body").delegate("#deptName","change",function(){
        /*$("#hid_dept").val($("#deptName option:selected").text()+",");*/
        var html="";
        $("#deptName option:selected").each(function(){
            html+=$(this).text()+',';
        });
        $("#hid_dept").val(html);
    });
    //$("#leaders").change(function(){
    //    $("#hidLeader").val($("#leaders").val());
    //})




    jQuery.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        //var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        var mobile = /^((1\d[0-9]{1})+\d{8})$/;
        var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (length == 11 && mobile.test(value))|| (tel.test(value));
    }, "号码格式错误");

    jQuery.validator.addMethod("compareDate",function(value,element){
        var assigntime = $("#actualBeginTime").val();
        var deadlinetime = $("#actualEndTime").val();
        var reg = new RegExp('-','g');
        assigntime = assigntime.replace(reg,'/');//正则替换
        deadlinetime = deadlinetime.replace(reg,'/');
        assigntime = new Date(parseInt(Date.parse(assigntime),10));
        deadlinetime = new Date(parseInt(Date.parse(deadlinetime),10));
        if(assigntime>deadlinetime){
            return false;
        }else{
            return true;
        }
    },"<font color='#E47068'>结束日期必须大于开始日期</font>");

    jQuery.validator.addMethod("decimal", function(value, element) {
        //value = parseInt(value);
        var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        if (value == "0"){
            return false;
        }
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "数字或带两位小数的数字且不为负数");
    jQuery.validator.addMethod("_integer", function(value, element) {
        var reg = /^[1-9]\d*|0$/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "房间数量为整数");

    jQuery.validator.addMethod("_character", function(value, element) {
        var reg = /^[A-z]+$|^[\u4E00-\u9FA5]+$|\d/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "联系人为中文或者英文字符");

    jQuery.validator.addMethod("_guestCompany", function(value, element) {
        var reg = /^[A-z]+$|^[\u4E00-\u9FA5]+$|\d/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "来宾单位为中文或者英文字符、数字");

    jQuery.validator.addMethod("_reason", function(value, element) {
        //var reg = /^[\u4E00-\u9FA5]+$/;
        var reg = /^[A-z]+$|^[\u4E00-\u9FA5]+$|\d/;
        if (!reg.test(value)){
            return false;
        }
        return true;
    }, "事由只能为中文");

    $("#appform").validate({
        rules: {
            linkman: {
                required: true,
                maxlength:25
            },
            mobile: {
                //required: true,
                mobile:true
            },
            foreignGuest: {
                required: true,
                maxlength:200
            },
            reasons: {
                required: true,
                maxlength:500

            }
        },
        messages: {
            linkman: {
                required: "必填",
                maxlength:"长度不能超过25个字符"
            },
            mobile: {
                required: "必填",
                mobile:"号码格式错误"
            },
            foreignGuest: {
                required: "必填",
                maxlength:"长度不能超过200个字符"
            },
            reasons: {
                required: "必填",
                maxlength:"长度不能超过500个字符"
            }

        },
        submitHandler: function (form) {

            var valid= $("#appform").valid();
            if(valid){
                $("#jdSubmit").attr("disabled","disabled") ;
            }

            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#jdSubmit").attr("data-url");
            /*$.ajax({
             url: _url + "?t=" + new Date().getMilliseconds(),
             type: 'post',
             data: $("#appform").serializeObject(),
             success: function (data) {

             if (data!=0) {
             alert("新增成功");
             window.close();
             }
             }
             });*/
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url: _url + "?t=" + new Date().getMilliseconds(),
                secureuri: false,                       //是否启用安全提交,默认为false
                fileElementId: ['upFile'],           //文件选择框的id属性
                dataType: 'text',                       //服务器返回的格式,可以是json或xml等
                //data: {formNo:formNo},
                data: $("#appform").serializeObject(),
                success: function (data, status) {        //服务器响应成功时的处理函数
                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));
                    /*if (result.isSuccess) {     //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                     alert("上传文件成功！");
                     $("#myModal").modal("hide");
                     loadData(2);
                     } else {
                     alert(result.msg);
                     }*/
                    if (result.isSuccess) {
                        alert("新增成功！");
                        window.history.back();
                        window.close();
                    } else {
                        alert("请选择上传文件");
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });
        }

    });



    $(function(){
        /*$("body").delegate("#budget","click",function(){
         var suiteNum=$("#suiteNum").val();
         var singleRoomNum=$("#singleRoomNum").val();
         var standardRoomNum=$("#standardRoomNum").val();
         var otherRoomNum=$("#otherRoomNum").val()

         var suitePrice=$("#suitePrice").val();
         var singleRoomPrice=$("#singleRoomPrice").val();
         var standardRoomPrice=$("#standardRoomPrice").val();
         var otherRoomPrice=$("#otherRoomPrice").val();

         $("#budget").val(eval(parseFloat(suiteNum)*parseFloat(suitePrice)+parseFloat(singleRoomNum)*parseFloat(singleRoomPrice)
         +parseFloat(standardRoomNum)*parseFloat(standardRoomPrice)+parseFloat(otherRoomNum)*parseFloat(otherRoomPrice)));
         });*/
        //工作餐
        $("body").delegate(".workLunch","blur",function(){
            var price=$("#price");
            var peopleNum=$("#peopleNum");
            var eatNum=$("#eatNum");

            var t=$("#person_sum");
            try{
                var ps = parseInt(price.val());
                var my = parseInt(peopleNum.val());

                var ps1 = parseInt(eatNum.val());

                var sum=ps*my*ps1;

                if (sum == NaN){
                    t.val(0);
                }else{
                    t.val(sum);
                }

            } catch (err){
                budget.val(0);
                console.log(err);
            }
        });


        //住宿核算
        $("body").delegate(".accommodation","blur",function(){

            var suiteNum=$("#suiteNum");
            var suitePrice=$("#suitePrice");

            var singleRoomNum=$("#singleRoomNum");
            var standardRoomNum=$("#standardRoomNum");
            var otherRoomNum=$("#otherRoomNum");

            var singleRoomPrice=$("#singleRoomPrice");
            var standardRoomPrice=$("#standardRoomPrice");
            var otherRoomPrice=$("#otherRoomPrice");

            var budget=$("#budget");
            try{
                var ps = parseInt(suiteNum.val());
                var my = parseInt(suitePrice.val());

                var ps1 = parseInt(singleRoomNum.val());
                var my1 = parseInt(singleRoomPrice.val());

                var ps2 = parseInt(standardRoomNum.val());
                var my2 = parseInt(standardRoomPrice.val());

                var ps3 = parseInt(otherRoomNum.val());
                var my3 = parseInt(otherRoomPrice.val());

                var sum=ps*my+ps1*my1+ps2*my2+ps3*my3;

                if (sum == NaN){
                    budget.val(0);
                }else{
                    budget.val(sum);
                }

            } catch (err){
                budget.val(0);
                console.log(err);
            }

        });


        //宴会金额核算
        //1.人数
        $("body").delegate(".peopleNums","blur",function(){
            var parent = $(this).closest("tr");
            var budeget = $(".budeget",parent);
            try{
                var ps = parseInt($(this).val());
                var my = $(".standardCosts",parent).val();
                var c=0;
                console.log(typeof(ps));
                if ("" == ps || 0 == ps){
                    my = 0;
                    c = 0;
                }else{
                    c = (ps * my).toFixed(2);
                }
                if (c == NaN){
                    budeget.val(0);
                }else{
                    budeget.val(c);
                }

            } catch (err){
                budeget.val(0);
                console.log(err);
            }
        });
        //2.标准金额
        $("body").delegate(".standardCosts","blur",function(){
            var parent = $(this).closest("tr");
            var budeget = $(".budeget",parent);
            try{
                var ps = Number($(".peopleNums",parent).val());
                var my = parseFloat($(this).val());
                var c = 0;

                if ("" == ps || 0 == parseInt(ps) || ps == NaN){
                    ps = 0;
                    c = 0;
                }else{
                    c = (ps * my).toFixed(2);
                }
                budeget.val(c);
            } catch (err){
                budeget.val(0);
                console.log(err);
            }
        });
        //总和核算
        /*$("body").delegate("#sum_all","blur",function(){
         var accommodation=$("#budget").val();
         var person_sum=$("#person_sum").val();
         var list=$(".sum_cost");
         var sum=0;
         for(var i=0;i<list.length;i++){
         sum+=parseFloat(list[i].value);
         }
         $("#sum_all").val(eval(parseFloat(accommodation)+parseFloat(person_sum)+parseFloat(sum)));
         });*/
        $("body").delegate(".accommodation","blur",function(){
            var accommodation=$("#budget").val();
            var workLunch=$("#person_sum").val();
            //var person_sum=$("#person_sum").val();
            var list=$(".sum_cost");
            var sum=0;


            try{
                for(var i=0;i<list.length;i++){
                    sum+=parseFloat(list[i].value);
                }
                var total=eval(parseFloat(accommodation)+parseFloat(sum)+parseFloat(workLunch));
                //$("#sum_all").val(eval(parseFloat(accommodation)+parseFloat(sum)));

                if (total == NaN){
                    $("#sum_all").val(0);
                    $("#lowcase").val(0);
                }else{
                    $("#sum_all").val(total);
                    $("#lowcase").val(total);
                }

            } catch (err){
                budget.val(0);
                console.log(err);
            }
        });


        $("body").delegate("#low_show","click",function(){
            $("#low_show").val($("#sum_show").text());
        });


        var n=0;
        $("body").delegate("#b100","click",function(){


            n++;
            var html="";
            html+='<tr>'+

            '<td colspan="2"><input type="text" value="" id="banquetTime"'+
            'name="banquetTime'+n+'" class="form-control col-md-7 col-xs-12 Wdate required" ></td>'+

            '<td><input type="text" value="" id="peopleNum"'+
            'name="peopleNum'+n+'" class="form-control col-md-7 col-xs-12 decimal required peopleNums accommodation" ></td>'+
            '<td colspan="2"><input type="text" value="" id="standardCost"'+
            'name="standardCost'+n+'" class="form-control col-md-7 col-xs-12 decimal required standardCosts accommodation" ></td>'+

            '<td><input type="button"  value="删除" class="del"/>' +

            '<input type="text" value="" id="sum_cost"'+
            'name="budget'+n+'" class="form-control col-md-7 col-xs-12 sum_cost decimal required budeget  sums" >'+

            '</td>'+
            '</tr>';
            $("#tr_list").after(html);

            var length=$("#tr_list").nextAll().length;
            $("#count").attr("rowspan",length);
            $("#banquet_num").attr("rowspan",length);

            $("#sum").val(length-1);

        });

        $("body").delegate(".del","click",function(){
            $(this).closest("tr").remove();
            var length=$("#tr_list").nextAll().length;
            $("#count").attr("rowspan",length);
            $("#banquet_num").attr("rowspan",length);

            $("#sum").val(length-1);
        });


    });


    //upload
    $("body").delegate("#uploadCostList", "click", function () {
        var formNo = $("#formNo_01").val();
        $.ajaxFileUpload({
            //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
            url: 'kg_lc02_Upload',
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
                    //alert(result.msg);
                    alert("请选择上传文件！");
                }
            },
            error: function (data, status, e) { //服务器响应失败时的处理函数
                $('#result').html('文件上传失败，请重试！！');
            }
        });
    });

    /*$("body").delegate("#deptName","change",function(){
        $("#deptName").find("option:selected").each(function(){
            var val=$(this).val();
            if(val==0){
                alert("本部门暂时没有人，请选择其他部门");
            }
        });
    });*/


    //导出
    $("body").delegate("#daochu", "click", function () {
        var val=$("#t_formNo").val();
        ajax_mainProjectNO(val);
    });
    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_yw_lc02_down?formNo_dc="+selectids;
    }
    //批量导出
    $("body").delegate("#daochuAll", "click", function () {
        var val=$("#t_formKind").val();
        ajax_receptionAll(val);
    });
    function ajax_receptionAll(selectids) {
        window.location.href="ajax_syy_yw_lc02_allReception?formKind_dc="+selectids;
    }


    //选择专家审核
    $("body").delegate(".leaders", "change", function () {
        var selectId = [];
        var selectName=[];
        $(".leaders option:selected").each(function () {
            selectId.push($(this).val());
            selectName.push($(this).text());
        });
        var sname = $("#hidid").val(selectId);
        var sid = $("#hidname").val(selectName);
    });


   /* var empid=$("#empID").val();
    var empids
    if ($.contains(empid,",")) {
        empids = empid.split(",");
    }else{
        empids = empid.split("");
    }

    $("#deptName1").val(empids).trigger('change')*/
    $("body").delegate("#xiaoxie", "blur", function () {
     var xiaoxie= $("#xiaoxie").val();
        var options = {
            url: "ajax_xiaoxie" + "?t=" + new Date().getMilliseconds()+"&xiaoxie="+xiaoxie,
            type: 'post',
            success: function (data) {

                $("#daxie").val(data);
            }
        };
        $.ajax(options);

    });

    $("body").delegate("#feiyongyusuan1,#feiyongyusuan2,#feiyongyusuan3", "blur", function () {
              var feiyongyusuan1=$("#feiyongyusuan1").val().trim();
              var feiyongyusuan2=$("#feiyongyusuan2").val().trim();
              var feiyongyusuan3=$("#feiyongyusuan3").val().trim();
              var xiaoxie=feiyongyusuan1*1+feiyongyusuan2*1+feiyongyusuan3*1;
               $("#xiaoxie").val(xiaoxie);

                var xiaoxie= $("#xiaoxie").val();
                var options = {
                    url: "ajax_xiaoxie" + "?t=" + new Date().getMilliseconds()+"&xiaoxie="+xiaoxie,
                    type: 'post',
                    success: function (data) {

                        $("#daxie").val(data);
                    }
                };
                $.ajax(options);


    })


});

