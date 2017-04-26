/**
 * Created by admin on 2016/8/27.
 */
define(function(require,exports,module){


    var template = require("template");
    require("app/common/templeteHelper");
    var $body = $("body");


    function loadData(target){
        var _url = target.attr("data-url");
        if(_url == "ajax_query_myApply"){
            var tpl = require("text!app/tpl/oaIndex/myApplyTpl.html");
        }
        if(_url == "ajax_query_applyWorkDone"){
            var tpl = require("text!app/tpl/oaIndex/applyWorkDoneTpl.html");
        }
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds(),
            type:"post",
            success:function(data){
                if(data){
                    $("#firstTable").html("");
                    var html = template.compile(tpl)(data);
                    $("#firstTable").html(html);
                }
            }
        })

    }

    $body.delegate(".queryData","click",function(){
        loadData($(this));
    })

    $("#home-tab").click();

    function loadData2(target){
        var _url = target.attr("data-url");
        if(_url == "ajax_query_myWorkToDo"){
            var tpl = require("text!app/tpl/oaIndex/myWorkToDoTpl.html");
        }
        if(_url == "ajax_query_myWorkDone"){
            var tpl = require("text!app/tpl/oaIndex/myWorkDoneTpl.html");
        }
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds(),
            type:"post",
            success:function(data){
                if(data){
                    $("#secondTable").html("");
                    var html = template.compile(tpl)(data);
                    $("#secondTable").html(html);
                }
            }
        })
    }

    $body.delegate(".queryData2","click",function(){
        loadData2($(this));
    })

    $("#secondForm").click();

    function loadData3(){
        var _url = "ajax_query_notice_index";
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds(),
            type:"post",
            success:function(data){
                if(data){
                    var tpl = require("text!app/tpl/oaIndex/NoticeTpl.html");
                    var html = template.compile(tpl)(data);
                    $("#thirdTable").html(html);
                }
            }
        })
    }

    loadData3();

    function loadData4(){
        var _url = "ajax_query_news_notice";
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds(),
            type:"post",
            success:function(data){
                if(data){
                    var tpl = require("text!app/tpl/oaIndex/newsTpl.html");
                    var html = template.compile(tpl)(data);
                    $("#fourthTable").html(html);
                }
            }
        })
    }

    loadData4();


    $body.delegate(".queryData5","click",function(){
        loadData5($(this));
    })

    function loadData5(target){
        var _url = target.attr("data-url");
        var tpl = require("text!app/tpl/oaIndex/myAppraiseTpl.html");
        $.ajax({
            url:_url+"&t="+new Date().getMilliseconds(),
            type:"post",
            success:function(data){
                if(data){
                    $("#appraiseTable").html("");
                    var html = template.compile(tpl)(data);
                    $("#appraiseTable").html(html);
                }
            }
        })
    }
$("#default5").click();


    $("body").delegate(".viewForm", "click", function () {
        var url = $(this).attr("data-url");
        window.open(encodeURI(encodeURI(url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
        //$("#viewframe").attr("src",url);
        //$("#ViewModal").modal({backdrop: 'static', keyboard: false});
        //$("#ViewModal").addClass("modal-center")
    });
})
