/**
 * Created by liulin on 2017/3/28.
 */
define(function(require,exports,module){

    require("jquery");
    var html2canvas= require("html2canvas");

    var dataUrl="";
    var formNo = $("#formNo").val();
    //$(document).ready(function(){
    //    html2canvas($("#imageHolder"), {
    //        allowTaint: false,
    //        taintTest: false,
    //
    //        onrendered: function(canvas) {
    //            canvas.id = "mycanvas";
    //            //document.body.appendChild(canvas);
    //            //生成base64图片数据
    //            dataUrl = canvas.toDataURL();
    //            //$this.next().attr("href",dataUrl).attr("download","五小成果荣誉证书.jpg")
    //            //$(".hidden_image").addClass("hidden")
    //            //ajax.trigger("done");
    //            //$.post("syy_dq_03_imageGenerate",{dataImage:dataUrl,formNo:formNo})
    //            //$.ajax({
    //            //    url:"syy_dq_03_imageGenerate?t="+new Date().getMilliseconds()+"&formNo"+formNo,
    //            //    type:'post'
    //            //    //data:{data:dataUrl,formNo:formNo}
    //            //})
    //            $("#hidData").val("{"+dataUrl+"}");
    //            $("#hidData2").val("test");
    //        }
    //
    //    });
    //})


    $("#image_generare").click(function(event) {

        var $this = $(this)
        var dataUrl = "";
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
                dataUrl = canvas.toDataURL();
                //$this.next().attr("href", dataUrl).attr("download", "五小成果荣誉证书.jpg")
                //$this.append($("<img>").attr("src", dataUrl));

                $.post("syy_dq_03_imageGenerate",{dataImage:dataUrl,formNo:formNo},function(data){
                    if(data==true){
                        alert("图片生成成功！")
                        window.close();
                    }
                })//数椐值太大，post提交有长度限制，需要更改tomcat配置





            }

        });
    })



})