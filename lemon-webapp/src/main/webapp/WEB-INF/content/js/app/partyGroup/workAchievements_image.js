/**
 * Created by liulin on 2017/3/28.
 */
define(function(require,exports,module){

    require("jquery");
    var html2canvas= require("html2canvas");


    var formNo = $("#formNo").val();


    $("#image_generare").click(function(event) {

        var $this = $(this)

        var formNo = $("#formNo").val();
        html2canvas($("#imageHolder"), {
            allowTaint: false,
            taintTest: false,
            //height:900,
            //width:1250,
            onrendered: function (canvas) {
                canvas.id = "mycanvas";
                //document.body.appendChild(canvas);
                //生成base64图片数据
                var dataUrl = canvas.toDataURL();
                //$this.next().attr("href", dataUrl).attr("download", "五小成果荣誉证书.jpg")
                //$this.append($("<img>").attr("src", dataUrl));

                $.post("syy_dq_02_imageGenerate",{dataImage:dataUrl,formNo:formNo},function(data){
                    if(data==true){
                        alert("图片生成成功！")
                        window.close();
                    }
                })//数椐值太大，post提交有长度限制，需要更改tomcat配置





            }

        });
    })



})