/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    var validate = require("validate");
    var wDatePicker = require("wdatePicker");
    var myWidGet = require("../common/myWidGet");
    require("mutiSelect");
    $(document).ready(function() {
        $('#example-dropUp').multiselect({
            includeSelectAllOption: true,
            maxHeight: 100
        });
    });

    if($("#idstatus").val()==""||$("#idstatus").val()==null||$("#idstatus").val()=="$projectStatus" ){
        $("#p_status option:checked").attr("selected", "");
        $("#www").attr("selected")
    }
    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#projectQueryBtn").click(function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var tpl = require("text!app/tpl/projectInfo/projectInfoTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
    }

    var callb= function callback() {
        $("#addProjectInfoForm").validate({

            /*rules: {
                carType: {
                    required: true
                },
                carNo:{
                    required:true,
                    number:true
                    //digits:true
                },
                controlName: {
                    required: true
                },
                repairLog:{
                    required:true
                }
            },
            messages: {
                carType: {
                    required: "必填"
                },
                carNo:{
                    required:"必须为合法数字",
                    number:"必须为合法数字"
                },
                controlName:{
                    required:"必填"
                },
                repairLog:{
                    required:"必填"
                }

            },*/

            submitHandler: function (form) {
                //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateProjectInfo").attr("data-url");

                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addProjectInfoForm").serializeObject(),
                    success: function (data) {
                        if (data == "success")
                            alert("新增成功");
                        $("#myModal").modal("hide")
                        loadData();
                    }
                };
                $.ajax(options);
            }

        });

    }
    widget.init(callb);



    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    //删除car
    $("#deleteProjectInfo").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        deleteMenu(chk_value.toString(), false);
    })

    function deleteMenu(selectids) {
        var options = {
            url: "ajax_deleteProjectInfo?t=" + new Date().getMilliseconds() + "&ids=" + selectids,
            type: 'get',
            success: function (data) {
                if (data == "success") {
                    alert("删除成功");
                    loadData();
                }
                else
                    alert(data);
            }
        };
        $.ajax(options);
    }

    //状态为维护保养时，显示录入
    $("body").delegate("#project", "change", function () {
        if (this.value == '') {
            $('#record').show();
        } else {
            $('#record').hide();
        }
    });

    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy'

        });
    });


    //导出
    $("body").delegate("#daochu", "click", function () {
        var chk_value = [];
        $('.checkBtn').each(function () {
            chk_value.push($(this).val());
        });
       // alert(chk_value);
        if(chk_value.toString()==null||chk_value.toString()==""){
            alert("数据为空");
            return false
        }
        else {
            ajax_mainProjectNO(chk_value.toString());
        }
    })

    function ajax_mainProjectNO(selectids) {
        window.location.href="ajax_syy_projectInfo_down?formNos="+selectids;
    }


});