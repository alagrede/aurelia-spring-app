// Minimalize menu when screen is less than 768px
$(function() {

    $("body").delegate(".navbar-minimalize", "click", function(e) {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });


    $(window).bind("load resize", function() {
        if ($(this).width() < 769) {
            $('body').addClass('body-small')
        } else {
            $('body').removeClass('body-small')
        }
    });

    $(window).bind("load resize scroll", function() {
      fix_height();
    });

});


// Pour initialiser la taille de l'écran au démarrage de l'application Aurelia
function fix_height() {
    // Hauteur du contenu principal
    var heightWithoutNavbar = $("body > #wrapper").height() - 61;
    $(".menu.menu-y").css("min-height", heightWithoutNavbar + "px");

    var navbarHeigh = $('nav.navbar-default').height();
    var wrapperHeigh = $('#page-wrapper').height();

    if(navbarHeigh > wrapperHeigh){
        $('#page-wrapper').css("min-height", navbarHeigh + "px");
    }

    if(navbarHeigh < wrapperHeigh){
        $('#page-wrapper').css("min-height", ($(window).height() - 61)  + "px");
    }

    // Hauteur du menu
    var heightWithoutNavbar = $(window).height() - 50;
    var wrapperHeigh = $('#page-wrapper').height();
    if (wrapperHeigh > heightWithoutNavbar) {
        $(".menu.menu-y").css("min-height", wrapperHeigh + "px");    
    } else {
        $(".menu.menu-y").css("min-height", heightWithoutNavbar + "px");
    }
}

    function SmoothlyMenu() {
        if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
            // Hide menu in order to smoothly turn on when maximize menu
            $('#side-menu').hide();
            // For smoothly turn on menu
            setTimeout(
                function () {
                    $('#side-menu').fadeIn(300);
                }, 100);
        } else if ($('body').hasClass('fixed-sidebar')){
            $('#side-menu').hide();
            setTimeout(
                function () {
                    $('#side-menu').fadeIn(300);
                }, 300);
        } else {
            // Remove all inline style from jquery fadeIn function to reset menu state
            $('#side-menu').removeAttr('style');
        }
    }

