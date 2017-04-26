/**
 * Created by liulin on 2016/7/13.
 */
define(function(require,exports,module){

    List = require("../common/pagelist");


    $("body").delegate(".tablist", "click", function () {
        var tpl;
        var url =  $(this).attr("data-url");
        if(url=="ajax_treatiseCount_query_list"){
            tpl= require("text!app/tpl/userAchievements/treatiseTpl.html");
        }
        if(url == "ajax_project_query"){
            tpl = require("text!app/tpl/userAchievements/projectTpl.html");
        }
        if(url == "ajax_patent_query"){
            tpl = require("text!app/tpl/userAchievements/patentTpl.html");
        }
        List("#table1", tpl, url, "", 1, 10);
    })
    loadData1();
    //加载数据
    function loadData1() {
        $("#home-tab").click();
        //var tpl = require("text!app/tpl/userAchievements/treatiseTpl.html");
        //var url = $("#searchForm1").attr("data-url");
        //var data = $("#searchForm1").serialize().toString();
        //List("#table1", tpl, url, data, 1, 10);
    }




})
