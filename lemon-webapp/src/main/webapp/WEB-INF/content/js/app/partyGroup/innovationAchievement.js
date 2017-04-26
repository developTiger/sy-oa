/**
 * Created by admin on 2016/9/3.
 */
define(function(require,exports,module){

    var wDatePicker = require("wdatePicker");
    var $body = $("body");
    var validate = require("validate");
    require("../common/jquery.serializeObject");
    require("../common/templeteHelper");//使用dateFormat日期格式转换
    require("ajaxUpload");
    var List = require("../common/pagelist");
    var widget = require("../common/widget");
    widget.init();//摸态框初始化
    var check = require("../common/checkbox");
    require("mutiSelect");
    var html2canvas= require("html2canvas");

    require("select2");

    $(".select2_multiple").select2({
        width:'100%',
        placeholder: "请选择",
        // multiple: true

    });

    $body.delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });

    check.checkAll("body", ".checkAll", ".checkBtn");

    //多选下拉 初始化
    $(document).ready(function() {
        $('.example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 400,
            buttonWidth: '350px',

            enableClickableOptGroups: true,
            enableCollapsibleOptGroups: true,
            enableFiltering: true
        });
        $(".input-group-addon").hide();
        $(".input-group-btn").hide();
    });

    //主要参加人 控件
    $(".Js_addPeople").on("click",function(){

        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc03_a_mainPeople_tpl.html");
        $.ajax({
            url:"ajax_partyGroup_lc03_mainPeople?"+"t="+new Date().getMilliseconds(),
            type:'post',
            success:function(data){
                if (data){
                    var html = template.compile(tpl)(data);
                    $(".Js_attendPeople").append(html);

                    $(".select2_multiple_tpl").select2({
                        width:'100%',
                        placeholder: "请选择",
                    });

                }
            }
        })

    })

    $("body").on("click",".Js_mainPeople_delete",function(){
        $(this).parent().parent().remove();
    })

    var form =  require("../common/form")

    //初始化 添加验证 及 做提交
    form.init(function () {
        form.doSubmit(); //此处写验证逻辑 ，最后调用 form.doSubmit 提交签核
    })

    var loadForm=function(){
        var target;
        if ($("#syy_innovationAchi_v").length>0) {
            target=$("#syy_innovationAchi_v");
            var tpl = require("text!app/tpl/partyGroup/syy_dq_lc03_v_Tpl.html");
            form.render(target,tpl);
        }

        //多选下拉 初始化
        $(document).ready(function() {
            $('.example-dropUp_d').multiselect({
                includeSelectAllOption: true,
                maxHeight: 400,
                buttonWidth: '270px',

                enableClickableOptGroups: true,
                enableCollapsibleOptGroups: true,
                enableFiltering: true
            });
            $(".input-group-addon").hide();
            $(".input-group-btn").hide();
        });


        //var step = $("input[name='innoAchiStep']").val();
        //if(step == "4"){
        //    $("#image_generate").removeClass("hidden");
        //}

    }


    var step="";
    var isViewOnly="";
    var isComplete="";
    exports.pageLoad=function(){
        loadForm();

        step = $("input[name='innoAchiStep']").val();
        isViewOnly = $("input[name='innoAchiIsViewOnly']").val();
        isComplete = $("input[name='innoAchiIsComplete']").val();

        if(step == "4" && isViewOnly == 0 && isComplete == 0){
            $("#image_generate").removeClass("hidden");
        }




    };

    $("#appform").validate({
        rules: {
            innoAge:{
                digits:true
            }
        },
        messages: {
            innoAge: {
                digits:"请输入一个1-200之间的正整数"
            }

        },



        submitHandler:function(form){
            //表单提交句柄,为一回调函数，带一个参数：form
            var _url = $("#innovationAchiSubmit").attr("data-url");

            var fileIds = [];
            $("input[name='file_name']").each(function(){
                fileIds.push($(this).attr("id"));
            })

            //$("#educa_degree").attr("disabled",false);//提交时，设置下拉列表可用，便于传值
            $("#hiddenEmpName").val($("#creatorName").val().toString())

            $.ajaxFileUpload({

                url: 'ajax_add_update_innovationAchi',
                secureuri: false,
                fileElementId: fileIds,
                dataType: 'text',
                data: $("#appform").serializeObject(),
                success: function (data, status) {

                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("新增成功！");
                        window.close();
                    } else {
                        alert(result.msg);
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('新增失败，请重试！！');
                }
            });
        }
    })

    //申请 确定按钮
    $("#approve").on("click", function () {

        $("input[name='hidGroupMemberIds']").val($("select[name='groupMember']").val());
        var members = [];
        $("select[name='groupMember']").find("option:selected").each(function(){
            members.push($(this).text());
        })
        $("input[name='hidGroupMemberNames']").val(members.toString());//成员name

        $("input[name='hidGroupLeaderName']").val($("select[name='groupLeader']").find("option:selected").text());

        $("input[name='awardDeptName']").val($("select[name='awardDeptId']").find("option:selected").text());//颁发单位name

        //if(step == "4" && isViewOnly == 0 && isComplete == 0) {
        //    var imageStatus = $("input[name='innoAchiImageStatus']").val();
        //    if(imageStatus == ""){
        //        alert("请先生成证书图片！");
        //        return false;
        //    }
        //
        //}

        //return false;

    })


    $(document).ready(function(){
        $("#applyUnit").val($("input[name='deptName']").val());

    })

    loadCount();
    function loadCount(){
        var url = "ajax_query_getAll";
        var tpl = require("text!app/tpl/partyGroup/syy_dq_lc03_v_count_Tpl.html");
        var data = $("#searchForm").serialize().toString();
        List("#table",tpl,url,data,1,10);
    }

    $("#queryAllForm").click(function () {
        loadCount();
    })

    //$(".modal-body").css("height","800px");//加载摸态框样式，一个用逗号，多个用冒号

    //$(".odal-body").addClass("modal-nomal-width");

    //$("#creatorName").change(function () {
    //    var empId = $(this).find("option:selected").val();
    //    if($(this).find("option:selected").text() != "请选择"){
    //        $("#hiddenEmpName").val($(this).find("option:selected").text());
    //    }
    //
    //    $.ajax({
    //        url:"ajax_query_singleEmp_info"+"?t="+new Date().getMilliseconds(),
    //        type:"post",
    //        data:{empId:empId},
    //        success: function (data) {
    //            if(data) {
    //
    //                if(data.sex == "1"){
    //                    $("#1").attr("checked",true);
    //                }else{
    //                    $("#2").attr("checked",true);
    //                }
    //
    //                $("#innoAge").val(data.age);
    //
    //                if(data.highestEdu == "博士"){
    //                    $(" select option[value='博士']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "硕士"){
    //                    $(" select option[value='硕士']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "本科"){
    //                    $(" select option[value='本科']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "大专"){
    //                    $(" select option[value='大专']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "高职"){
    //                    $(" select option[value='高职']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "中专"){
    //                    $(" select option[value='中专']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "高中"){
    //                    $(" select option[value='高中']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }else if(data.highestEdu == "初中"){
    //                    $(" select option[value='初中']").attr("selected",true);  //如果值一样 就选中对应的option,
    //                }
    //
    //                $("#position").val(data.position);
    //                $("#title").val(data.workKind);
    //
    //            }else{
    //                alert("数据获取失败！");
    //            }
    //        }
    //    })
    //
    //})


    //$.post("sra_aa");

    //html生成图片

    /*$("#image_generate").on("click", function(event) {
        var $this = $(this);
        var formNo = $("input[name='innoAchiFormNo']").val();
        var currentTime= new Date();
        $.ajax({
            url:"ajax_dq_03_generateImage?"+"formNo="+formNo,
            type:'get',
            async:false,
            success:function(data){
                if(data){
                    //$(".js_projectName").text(data.projectName);
                    if(data.projectName != null)
                        $(".js_awardProject").text(data.projectName);

                    if(data.prizeLeval != null) {
                        if(data.prizeLeval == "first")
                            $(".js_awardLevel").text("一等奖")
                        if(data.prizeLeval == "second")
                            $(".js_awardLevel").text("二等奖")
                        if(data.prizeLeval == "third")
                            $(".js_awardLevel").text("三等奖")
                    }

                    $(".js_time_first").text(formatDate(currentTime));
                    $(".js_time_second").text(formatDate(currentTime));

                    if(data.creatorName){
                        var creators = data.creatorName.split(",");
                        var count=0;
                        var section_2_mainPeople = $(".section_2_mainPeople");
                        for(var i=0;i<creators.length;i++){
                            count++;
                            var name = " <div class='section_2_span_1'>"+count+"、"+creators[i]+"</div> ";
                            if(count == 2){
                                section_2_mainPeople.append(name).append("<br>");
                            }else{
                                section_2_mainPeople.append(name)
                            }
                        }
                    }
                }
            }
        })
        //$(".hidden_image").removeClass("hidden")
        //$("#previewIframe").contents().find("body")
        html2canvas($("#previewIframe"), {
            allowTaint: true,
            taintTest: false,
            //height:2000,
            //width:3000,
            onrendered: function(canvas) {
                canvas.id = "mycanvas";
                //document.body.appendChild(canvas);
                //生成base64图片数据
                var dataUrl = canvas.toDataURL();
                $this.attr("href",dataUrl).attr("download","五小成果荣誉证书.jpg")
                $this.next().append($("<img>").attr("src",dataUrl).attr("class","hidden"));
                //$(".hidden_image").addClass("hidden")
                $("#image_generate").off("click");

                var step = $("input[name='innoAchiStep']").val();
                var isViewOnly = $("input[name='innoAchiIsViewOnly']").val();
                var isComplete = $("input[name='innoAchiIsComplete']").val();

                if(step == "4" && isViewOnly == false && isComplete == false){
                    $.post("ajax_dq_03_imageGenerate",{dataImage:dataUrl,formNo:formNo});
                }

            }

        });
    });*/


    $("#image_generate").on("click", function(event) {
        //window.location.href="syy_dq_lc03_iframe_html";

        var prizeLevel = $("select[name='prizeLeval']").val();
        if(prizeLevel ==""){
            alert("请先选择获奖等级！");
            return false;
        }


        var formNo = $("input[name='innoAchiFormNo']").val();
        var url = "syy_dq_lc03_iframe_html?"+"formNo="+formNo+"&prizeLevel="+prizeLevel;
        window.open(url);
        //只能点击一次
        $(this).addClass("hidden");
    })



    //js 时间转换
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy年MM月";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };


})
