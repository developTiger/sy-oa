/**
 * Created by admin on 2016/8/13.
 */
define(function(require,exports,module){
    require('../init');
    var List = require("../common/pagelist");
    var template = require("template");
    var check = require("../common/checkbox");
    var $body = $("body");
    check.checkAll("body", ".checkAll", ".checkBtn")
    $(".top_nav").hide();
    $body.delegate(".queryData","click",function(){
        var _url= $(this).attr("data-url");

        if(_url == "detailInfo1"){
            var tpl = require("text!app/tpl/equipment/detailInfo1.html");
        }
        if(_url == "detailProjectOpen"){
            var tpl = require("text!app/tpl/equipment/projectOpen.html");
        }
        if(_url == "detailProjectCheck"){
            var tpl = require("text!app/tpl/equipment/projectCheck.html");
        }
        if(_url == "detailProjectDelay"){
            var tpl = require("text!app/tpl/equipment/projectdelay.html");
        }
        if(_url == "detailProjectDeliver"){
            var tpl = require("text!app/tpl/equipment/projectDeliver.html");
        }

        var id = $("#hidId").val()
        var projectID = $("#hidProjectID").val()
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds()+"&id="+id+"&projectID="+projectID,
            type:"get",
            success:function(data){
                    $("#displayData").html("");
                    var html = template.compile(tpl)(data);
                    $("#displayData").html(html);
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


})
