/**
 * Created by admin on 2017/2/13.
 */
define(function(require,exports,module){

    var validate = require("validate")
    var widget = require("../common/widget");
    widget.init();
    require("../common/jquery.serializeObject");
    var List = require("../common/pagelist");
    require("../common/templeteHelper2");

    //增加修改
    $("body").on("click","#addOrUpdate",function(){
        var url = $(this).attr("data-url");
        $("#addOrUpdatetypeForm").validate({
            submitHandler: function (form) {
                $.ajax({
                    url: url +"?t="+ new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addOrUpdatetypeForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            loadData();
                        }
                        else
                            alert(data.msg);
                        $("#myModal").modal("hide")
                    }
                });

            }

        });
    });

    $("#searchCompetition").click(function(){
        loadData();
    }).trigger("click");

    function loadData(){
        var tpl = require("text!app/tpl/partyGroup_edit/competitionTypeTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
    }

    $("body").on("click",".deleteTitle",function(){
        var _url = $(this).attr("data-url");
        $.ajax({
            url:_url+"&t="+new Date().getMilliseconds(),
            type:'get',
            success:function(data){
                if(data.isSuccess)
                    alert("删除成功！");
                loadData();

            }
        })
    })


})