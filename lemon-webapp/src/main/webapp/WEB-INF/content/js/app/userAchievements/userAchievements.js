define(function(require,exports,module){

    require("../common/jquery.serializeObject");
    var wDatePicker = require("wdatePicker");

    //时间插件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd' });
    });














})
