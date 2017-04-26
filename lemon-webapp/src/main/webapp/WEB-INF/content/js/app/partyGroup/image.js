/**
 * Created by admin on 2017/3/17.
 */
define(function(require,exports,module){

    require("jquery");
    var html2canvas= require("html2canvas");

    $(document).ready( function(){

        $(".example2").on("click", function(event) {
            var $this = $(this);

            html2canvas(document.body, {
                allowTaint: true,
                taintTest: false,
                onrendered: function(canvas) {

                    canvas.id = "mycanvas";
                    //document.body.appendChild(canvas);
                    //生成base64图片数据
                    var dataUrl = canvas.toDataURL();
                    $this.attr("href",dataUrl).attr("download","1.jpg")
                    $this.append($("<img>").attr("src",dataUrl))

                    //$(".example2").off("click");
                }
            });
        });

    });


})

