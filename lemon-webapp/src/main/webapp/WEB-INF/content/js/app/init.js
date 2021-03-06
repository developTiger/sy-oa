/**
 * Created by zhouz on 2016/5/15.
 */
//var $= require("jquery");

define(function (require, exports, module) {
    require("bootstrap");
    require("iCheck");

    var NProgress = require("np");
    var check = require("./common/checkbox");
    check.niceCheck("input .checkBtn");
    //if (typeof NProgress != 'undefined') {
    //    $(document).ready(function () {
    //        NProgress.start();
    //    });
    //}
    $("body").delegate(".newWindows", "click", function () {
        var url = $(this).attr("data-url");
        window.open(encodeURI(encodeURI(url)), 'newwindow', 'height=800,width=950,top=111,left=111,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
  
        //$("#viewframe").attr("src",url);
        //$("#ViewModal").modal({backdrop: 'static', keyboard: false});
        //$("#ViewModal").addClass("modal-center")
    });
    jQuery(function ($) {
        // 备份jquery的ajax方法
        var _ajax = $.ajax;
        // 重写ajax方法，先判断登录在执行success函数
        $.ajax = function (opt) {
            var _success = opt && opt.success || function (a, b) {
                };
            var _opt = $.extend(opt, {
                success: function (data, textStatus) {
                    // 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)
                    if (data == 'notlogin') {
                        window.location.href = "login";
                        return;
                    }
                    _success(data, textStatus);
                }
            });
            _ajax(_opt);
        };
    });
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /** ******  left menu  *********************** **/
    $(function () {
        //$('#sidebar-menu li ul').slideUp();
        //$('#sidebar-menu li').removeClass('active');

        $('#sidebar-menu .ma').on('click touchstart', function (event) {
            if(!event.target.hasAttribute("data-url")) {
                if ($(this).is('.active')) {
                    $(this).removeClass('active');
                    $('ul', this).slideUp();
                } else {
                    $('#sidebar-menu li').removeClass('active');
                    $('#sidebar-menu li ul').slideUp();

                    $(this).addClass('active');
                    $('ul', this).slideDown();
                }
            }
        });
        // $('#sidebar-menu .pa').on('click touchstart', function () {
        //     var link = $('a', this).attr('href');
        //     if (link) {
        //         if (!link.toString().startsWith("syy_")) {
        //             window.location.href = link;
        //             event.stopPropagation();
        //             window.event.cancelBubble = true;
        //         }
        //     }
        // });
        $('#menu_toggle').click(function () {
            if ($('body').hasClass('nav-md')) {
                $('body').removeClass('nav-md').addClass('nav-sm');
                $('.left_col').removeClass('scroll-view').removeAttr('style');
                $('.sidebar-footer').hide();

                if ($('#sidebar-menu li').hasClass('active')) {
                    $('#sidebar-menu li.active').addClass('active-sm').removeClass('active');
                }
            } else {
                $('body').removeClass('nav-sm').addClass('nav-md');
                $('.sidebar-footer').show();

                if ($('#sidebar-menu li').hasClass('active-sm')) {
                    $('#sidebar-menu li.active-sm').addClass('active').removeClass('active-sm');
                }
            }
        });
    });

///* Sidebar Menu active class */
//$(function () {
//    var url = window.location;
//    $('#sidebar-menu a[href="' + url + '"]').parent('li').addClass('current-page');
//    $('#sidebar-menu a').filter(function () {
//        return this.href == url;
//    }).parent('li').addClass('current-page').parent('ul').slideDown().parent().addClass('active');
//});

    /** ******  /left menu  *********************** **/
    /** ******  right_col height flexible  *********************** **/
 /*   $(".right_col").css("min-height", $(window).height());
    $(window).resize(function () {
        $(".right_col").css("min-height", $(window).height());
    });*/
    $(".right_col").css("min-height", $(".left_col").height());//动态定义右侧最小高度
    $(window).resize(function () {
        $(".right_col").css("min-height", $(".left_col").height());
    });
    /** ******  /right_col height flexible  *********************** **/


    /** ******  tooltip  *********************** **/
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
    /** ******  /tooltip  *********************** **/
    /** ******  progressbar  *********************** **/
    if ($(".progress .progress-bar")[0]) {
        $('.progress .progress-bar').progressbar(); // bootstrap 3
    }
    /** ******  /progressbar  *********************** **/
    /** ******  switchery  *********************** **/
    if ($(".js-switch")[0]) {
        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
        elems.forEach(function (html) {
            var switchery = new Switchery(html, {
                color: '#26B99A'
            });
        });
    }
    /** ******  /switcher  *********************** **/
    /** ******  collapse panel  *********************** **/
// Close ibox function
    $('.close-link').click(function () {
        var content = $(this).closest('div.x_panel');
        content.remove();
    });

// Collapse ibox function
    $('.collapse-link').click(function () {
        var x_panel = $(this).closest('div.x_panel');
        var button = $(this).find('i');
        var content = x_panel.find('div.x_content');
        content.slideToggle(200);
        (x_panel.hasClass('fixed_height_390') ? x_panel.toggleClass('').toggleClass('fixed_height_390') : '');
        (x_panel.hasClass('fixed_height_320') ? x_panel.toggleClass('').toggleClass('fixed_height_320') : '');
        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
        setTimeout(function () {
            x_panel.resize();
        }, 50);
    });
    /** ******  /collapse panel  *********************** **/
    /** ******  iswitch  *********************** **/
    if ($("input.flat")[0]) {
        $(document).ready(function () {
            $('input.flat').iCheck({
                checkboxClass: 'icheckbox_flat-green',
                radioClass: 'iradio_flat-green'
            });
        });
    }
    /** ******  /iswitch  *********************** **/
    /** ******  star rating  *********************** **/
// Starrr plugin (https://github.com/dobtco/starrr)
    var __slice = [].slice;

    (function ($, window) {
        var Starrr;

        Starrr = (function () {
            Starrr.prototype.defaults = {
                rating: void 0,
                numStars: 5,
                change: function (e, value) {
                }
            };

            function Starrr($el, options) {
                var i, _, _ref,
                    _this = this;

                this.options = $.extend({}, this.defaults, options);
                this.$el = $el;
                _ref = this.defaults;
                for (i in _ref) {
                    _ = _ref[i];
                    if (this.$el.data(i) != null) {
                        this.options[i] = this.$el.data(i);
                    }
                }
                this.createStars();
                this.syncRating();
                this.$el.on('mouseover.starrr', 'span', function (e) {
                    return _this.syncRating(_this.$el.find('span').index(e.currentTarget) + 1);
                });
                this.$el.on('mouseout.starrr', function () {
                    return _this.syncRating();
                });
                this.$el.on('click.starrr', 'span', function (e) {
                    return _this.setRating(_this.$el.find('span').index(e.currentTarget) + 1);
                });
                this.$el.on('starrr:change', this.options.change);
            }

            Starrr.prototype.createStars = function () {
                var _i, _ref, _results;

                _results = [];
                for (_i = 1, _ref = this.options.numStars; 1 <= _ref ? _i <= _ref : _i >= _ref; 1 <= _ref ? _i++ : _i--) {
                    _results.push(this.$el.append("<span class='glyphicon .glyphicon-star-empty'></span>"));
                }
                return _results;
            };

            Starrr.prototype.setRating = function (rating) {
                if (this.options.rating === rating) {
                    rating = void 0;
                }
                this.options.rating = rating;
                this.syncRating();
                return this.$el.trigger('starrr:change', rating);
            };

            Starrr.prototype.syncRating = function (rating) {
                var i, _i, _j, _ref;

                rating || (rating = this.options.rating);
                if (rating) {
                    for (i = _i = 0, _ref = rating - 1; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
                        this.$el.find('span').eq(i).removeClass('glyphicon-star-empty').addClass('glyphicon-star');
                    }
                }
                if (rating && rating < 5) {
                    for (i = _j = rating; rating <= 4 ? _j <= 4 : _j >= 4; i = rating <= 4 ? ++_j : --_j) {
                        this.$el.find('span').eq(i).removeClass('glyphicon-star').addClass('glyphicon-star-empty');
                    }
                }
                if (!rating) {
                    return this.$el.find('span').removeClass('glyphicon-star').addClass('glyphicon-star-empty');
                }
            };

            return Starrr;

        })();
        return $.fn.extend({
            starrr: function () {
                var args, option;

                option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
                return this.each(function () {
                    var data;

                    data = $(this).data('star-rating');
                    if (!data) {
                        $(this).data('star-rating', (data = new Starrr($(this), option)));
                    }
                    if (typeof option === 'string') {
                        return data[option].apply(data, args);
                    }
                });
            }
        });
    })(window.jQuery, window);

    $(function () {
        return $(".starrr").starrr();
    });

    $(document).ready(function () {

        $('#stars').on('starrr:change', function (e, value) {
            $('#count').html(value);
        });


        $('#stars-existing').on('starrr:change', function (e, value) {
            $('#count-existing').html(value);
        });

    });

    /** ******  /table  *********************** **/
    /** ******    *********************** **/
    /** ******    *********************** **/
    /** ******    *********************** **/
    /** ******    *********************** **/
    /** ******    *********************** **/
    /** ******    *********************** **/
    /** ******  Accordion  *********************** **/

    $(function () {
        $(".expand").on("click", function () {
            $(this).next().slideToggle(200);
            $expand = $(this).find(">:first-child");

            if ($expand.text() == "+") {
                $expand.text("-");
            } else {
                $expand.text("+");
            }
        });
    });

    /** ******  Accordion  *********************** **/

    /** ******  scrollview  *********************** **/
//$(document).ready(function () {
//
//    $(".scroll-view").niceScroll({
//        touchbehavior: true,
//        cursorcolor: "rgba(42, 63, 84, 0.35)"
//    });
//
//});
    /** ******  /scrollview  *********************** **/

    /** ******  NProgress  *********************** **/
//if (typeof NProgress != 'undefined') {
//
//    $(window).load(function () {
//        NProgress.done();
//    });
//}
    /** ******  NProgress  *********************** **/

})
;