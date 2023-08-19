(function ($) {
    'use strict';

    /* :: 4.0 Header Area*/
    //文本
    var first_article = $('.first-hero-slides-content h2').text()
    var second_article = $('.second-hero-slides-content h2').text()
    var third_article = $('.third-hero-slides-content h2').text()
    var first_context = $('.first-hero-slides-content h6').text()
    var second_context = $('.second-hero-slides-content h6').text()
    var third_context = $('.third-hero-slides-content h6').text()

    //点击事件
    $('.first-hero-slides-content a').click(function (){
        alert("hello")
    });
    $('.second-hero-slides-content a').click(function (){
        alert("hello")
    });
    $('.third-hero-slides-content a').click(function (){
        alert("hello")
    });

    /* :: 5.0 About Us*/
    //文本
    var block_article = $('.medica-about-content h2').text()
    var context_article = $('.medica-about-content p').text()
    var one_service_article = $('.one-service-content h5').text()
    var two_service_article = $('.two-service-content h5').text()
    var three_service_article = $('.three-service-content h5').text()
    var four_service_article = $('.four-service-content h5').text()
    var one_service_content = $('.one-service-content p').text()
    var two_service_content = $('.two-service-content p').text()
    var three_service_content = $('.three-service-content p').text()
    var four_service_content = $('.four-service-content p').text()

    //点击事件
    //$('.medica-about-content a').click(function (){
    //    alert("hello")
   // });

    //:: 6.0Cool Facts
    var data_one = $('.one-single-cool-fact-area h2').text()
    var data_one_name = $('.one-single-cool-fact-area h6').text()
    var data_one_con = $('.one-single-cool-fact-area p').text()

    var data_two = $('.two-single-cool-fact-area h2').text()
    var data_two_name = $('.two-single-cool-fact-area h6').text()
    var data_two_con = $('.two-single-cool-fact-area p').text()

    var data_three = $('.three-single-cool-fact-area h2').text()
    var data_three_name = $('.three-single-cool-fact-area h6').text()
    var data_three_con = $('.three-single-cool-fact-area p').text()

    var data_four = $('.four-single-cool-fact-area h2').text()
    var data_four_name = $('.four-single-cool-fact-area h6').text()
    var data_four_con = $('.four-single-cool-fact-area p').text()


    //7.0Gallery Area
    $('.one-btn').click(function (){
        alert("hello")
    });
    $('.two-btn').click(function (){
        alert("hello")
    });
    $('.three-btn').click(function (){
        alert("hello")
    });
    $('.four-btn').click(function (){
        alert("hello")
    });

    //8.0
    var one_blog_con = $('.one-blog p').text()
    var one_blog_date = $('.one-blog a[name=date]').text()
    var one_blog_title = $('.one-blog a[name=title]').text()

    var two_blog_con = $('.two-blog p').text()
    var two_blog_date = $('.two-blog a[name=date]').text()
    var two_blog_title = $('.two-blog a[name=title]').text()

    var three_blog_con = $('.three-blog p').text()
    var three_blog_date = $('.three-blog a[name=date]').text()
    var three_blog_title = $('.three-blog a[name=title]').text()

    //点击事件
    $('.one-blog a[name=title]').click(function (){
        alert("hello")
    });
    $('.two-blog a[name=title]').click(function (){
        alert("hello")
    });
    $('.three-blog a[name=title]').click(function (){
        alert("hello")
    });
})(jQuery);