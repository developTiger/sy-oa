/**
 * Created by pxj on 2016/9/14.
 */
define(function (require, exports, module) {
    var wDatePicker = require("wdatePicker");
    var List = require("../common/pagelist");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            readOnly:true
        });
    });
    loadData();
    $("#driveringListQueryBtn").click(function () {
        loadData();
    });
    function loadData() {
        var tpl = require("text!app/tpl/carflow/car_driver_statistics_Tpl.html");
        var url = $("#searchDrivering").attr("data-url");
        var data = $("#searchDrivering").serialize().toString();
        List("#table", tpl, url, data, 1, 10);

    }


    //导出
    $("body").delegate("#daochu", "click", function () {

       var item=  $('#item').val();
        if(item>0){
            ajax_mainProjectNO();
        }else{
            alert("数据为空");
        }

    })


    function ajax_mainProjectNO() {
        window.location.href="ajax_driver_statistics_down";
    }



    /*  $("#tables").delegate("#operation","click",function() {
          console.log($(this).text());
          alert("11");
          var staff_row_next_all = $(this).closest(".drname");
          console.info(staff_row_next_all.text());
         /!*var s= $(this).closest(".cz").find("cz").find("option:selected");
          console.info(s);*!/

      });*/
/*    $(".dr_statistics").delegate("#operation",'click',function () {
        var driver_name= $(this).closest(".dr_statistics").find(".drname");
        var company_name=$(this).closest(".dr_statistics").find(".drcompanty");
        var driver_bgtime=$("#driver-bgtime").text();
        var driver_endtime=$("#driver-edtime").text();

        console.log(driver_name.text());
        console.log(company_name.text());
        console.log(driver_bgtime);
        console.log(driver_endtime);
    });*/
});