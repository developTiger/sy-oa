/**
 * Created by liulin on 2016/7/6.
 */
define(function(require,exports,module){

    var wDatePicker = require("wdatePicker");

    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd' });
    });


})