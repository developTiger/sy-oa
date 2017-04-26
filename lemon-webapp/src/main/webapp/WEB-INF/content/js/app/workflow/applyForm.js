define(function (require, exports, module) {
    require('jquery');

    $("body").delegate(".createForm","click",function(){
        var url = $(this).attr("data-url");
        var formKind = $(this).attr("form-kind").toLowerCase()+'_a';
        window.open(formKind,'newwindow','height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');

    });

});