/**
 * Created by swb on 2016/10/12.
 */
define(function(require,exports,module){
    require('../init');
    var List = require("../common/pagelist");
    var template = require("template");
    var check = require("../common/checkbox");
    var $body = $("body");
    check.checkAll("body", ".checkAll", ".checkBtn")
    var tpl_equipmentMaintenance = require("text!app/tpl/projectInfo/queryProjectOpenInfo.html");


    $body.delegate(".queryData","click",function(){
        var _url= $(this).attr("data-url");
        if(_url == "lc03_info"){
            var tpl = require("text!app/tpl/projectInfo/queryMajorInfo.html");
        }
        if(_url == "project_open_info"){
            var tpl = require("text!app/tpl/projectInfo/queryProjectOpenInfo.html");
        }
        if(_url == "ajax_query_technologyJudgementInfo"){
            var tpl = require("text!app/tpl/equipment/queryTechnologyJudgementTpl.html");
        }
        var id = $("#hidId").val();
        var formNo=$("#hidFormNo").val();
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds()+"&id="+id+"&formNo1="+formNo,
            type:"get",
            success:function(data){
                if(data){
                    $("#displayData").html("");
                    var html = template.compile(tpl)(data);
                    $("#displayData").html(html);
                }
            }
        })
    })

    $("#home-tab").click();


    function checkall(table,btn,callback){
        this.table=table;
        this.btn = btn;
        this.check = callback;
    }//这是一个构造函数

    checkall.prototype.check = function(callback){//查找属性，javascript向上遍历原型链，找到返回对象属性，一直到原型链的顶部:object.prototype,没有找到属性就返回undefined
        var that = this;
        $(that.btn).click(function(){
            $(that.table).find("input[type='checkbox']").prop("checked",$(this).prop("checked"));
            callback && typeof callback=="function"&&callback();//这一段完全不明白
        })
    }
    module.exports=checkall;

    $.fn.abc = function(){};//abc表示任何一个实例都可以用
    $("#sf").abc();







})

