/**
 * Created by xubo on 2016/7/12 0012.
 */
define(function (require, exports, module) {

    var staff_rows = $(".staff-rows");


    var win_main = (new Function("","return " + $("#sa").val()))();
    //win_main["98"] = '请选择';
    //win_main["99"] = '其他';
    console.log(win_main);
    var win_main_fb = $.extend(true,{},win_main);



    $("body").delegate("#addStaff","click",function(){
        //添加获奖行
        add_staff();
    });



//添加获奖行

    function add_staff(){
        //当前个数
        var num = $(".staff-row").length;
        var has_Selected = [];
        //排除已选择的数据
        $(".staff-row").each(function(i,e){
            has_Selected.push($(e).find(".controls").children("select").val());
        });
        var win_main_fb_else = {};
        if( num > 0 ){
            for (var i in win_main_fb){
                for( var j = 0 ; j < has_Selected.length ; j++ ){
                    if( i == has_Selected[j] || i == "99"){
                        delete win_main_fb[i];
                        break;
                    }
                }
            }
        }
        getOther();
        //新增的序号
        var next_num = num + 1;
        //输出代码
        var html = '<div class="control-group staff-row">'+
            '<div class="controls controls-row doc-input-size">'+
            '<label class="control-label col-sm-3">'+
            '第' +
            '<span class="staff-num">' + next_num + '</span>' +
            '获奖人' +
            '<span class="required">*</span>'+
            '</label>'+
            '<select required="required"  class="span3 col-sm-3 special-sele"  data-parsley-id="0004">'+
            '<option value="" data-value="">请选择</option>';

        for( var t in win_main_fb ){
            html += '<option value="' + t +'"> '+  win_main_fb[t] + '</option>'
        }

        html += '</select>'+
        '<input type="text" class="input_outname col-sm-3 col-md-offset-1" disabled>'+
        '<a class="btn-sm col-sm-2 delStaff" href="javascript:void(0);">删除</a>'+
        '</div></div>';
        staff_rows.append(html);
    }

    //删除数据
    $("body").delegate(".delStaff","click",function(){
        var staff_row_next_all = $(this).closest(".staff-row").nextAll();
        //var staff_num = $(this).closest(".staff-row").children("staff-num");
        //编号重新排序
        if(staff_row_next_all.length > 0 ){
            staff_row_next_all.each(function(i,e){
                var obj = $(e).closest(".staff-row").find(".staff-num");
                var num = Number(obj.html());
                obj.html(num - 1);
            });
        }
        //删除后回复记录
        var current = $(this).closest(".staff-row").find("select").find("option:selected");
        if( current.val().length > 0){
            win_main_fb[current.val()] = current.text();
            //刷新各个select的值
            rush_select(current.val(),current.text());
        }



        //删除当前记录
        $(this).closest(".staff-row").remove();

        //渲染其他选框
        var select_all = get_Selected_all();
        win_main_fb = $.extend(true,{},{});
        for (var i in win_main){
            for (var j in select_all){
                win_main_fb[i] = win_main[i];
                if( i == j){
                    delete win_main_fb[i];
                    break;
                }
            }
        }
        getOther();
        rush_current_win_main();
    });

    //删除时刷新
    function rush_select(k,v){

        $(".special-sele").each(function(i,e){
            var html = "";
            html += "<option value=" + k + ">" + v + "</option>";
            $(this).append(html);
        });
    }
    //改变的时候渲染
    $("body").delegate(".special-sele","change",function(){
        var select_all = get_Selected_all();
        var current = $(this).val();
        var inputName = $(this).next(".input_outname");
        win_main_fb = $.extend(true,{},{});
        for (var i in win_main){
            for (var j in select_all){
                win_main_fb[i] = win_main[i];
                if( i == j){
                    delete win_main_fb[i];
                    break;
                }
            }
        }
        getOther();
        rush_current_win_main();
        if( current == "99" ){
            inputName.removeAttr("disabled");
        }else{
            inputName.attr("disabled","disabled");
        }
    });

    //获取已选择的集合
    function get_Selected_all(){
        var temp = {};
        $(".special-sele").each(function(i,e){
            var selected = $(e).find("option:selected");
            temp[selected.val()] = selected.text();
        });
        return temp;
    }

    //选中时刷新
    function rush_current_win_main(){
        $(".special-sele").each(function(i,e){
            var key = $(e).find("option:selected");
            var html = "<option selected value='" + key.val() + "'>" + key.text() + "</option>";
            for (var i in win_main_fb){
                if(key.val() != i) {
                    html += "<option value=" + i + ">" + win_main_fb[i] + "</option>";
                }
            }
            $(this).html(html);
        });
    }

    //获得属性其他
    function getOther(){
        for(var i in win_main_fb){
            if( i == "99" ){
                delete win_main_fb[i];
            }
        }
        win_main_fb["99"] = "其他";
    }


    //提交员工
    function sub_staff(){
        var diolag = $("#myModal");
        if(diolag.length == 1) {
            $("#myModal").modal('hide');
            //$("#myModal").remove();
            //$(".modal-backdrop").remove();
        }
    }

    //绑定提交事件
    $("body").delegate("#getval","click",function(){
        package_write_data();
        sub_staff();
    });


    //封装提交数据，并写入页面
    function package_write_data(){
        var winfo = $("#winner_Info");
        var html = "";
        $(".special-sele").each(function(i,e){
            var current = $(this).find("option:selected");
            var inputName = $(this).next(".input_outname");
            if( current.val() == "99" ){
                html += "0@" + (i + 1) + "@" + inputName.val() + ",";
            }else{
                html += "1@" + (i + 1) + "@" + current.val() + ",";
            }
        });
        html = html.substring(0,html.length - 1);
        winfo.val(html);
    }

    //取消事件
    $("body").delegate("#cancelModal_1","click",function(){
        sub_staff();
    });

});