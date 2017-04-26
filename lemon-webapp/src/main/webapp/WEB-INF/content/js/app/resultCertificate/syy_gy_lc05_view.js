/**
 * Created by pxj on 2016/9/28.
 */
define(function (require, exports, module) {
    var validate = require("validate");
//审核页面加载
    var form = require("../common/form")
//初始化 添加验证 及 做提交
//form.init();  无回调函数的 初始化，则默认使用validate验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
    form.init();
});