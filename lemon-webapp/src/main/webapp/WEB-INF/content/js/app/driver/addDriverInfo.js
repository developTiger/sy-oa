/**
 * Created by xubo on 2016/7/12 0012.
 */
define(function (require, exports, module) {
    var wDatePicker = require("wdatePicker");
    //时间
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',
                      readOnly:true
        });
    });
    $("#sec_status").val(1);
    $("#sec_status").attr("readonly","readonly");
    $("#hiddenStatus").val(1);
});