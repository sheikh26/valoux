var scrollAnimationTime = 1200,
        scrollAnimation = 'easeInOutExpo';
    $('a.scrollto').bind('click.smoothscroll',function (event) {
        event.preventDefault();
        var target = this.hash;
        $('html, body').stop().animate({
            'scrollTop': $(target).offset().top
        }, scrollAnimationTime, scrollAnimation, function () {
            window.location.hash = target;
        });
    });   




/* ================================
===  PROJECT LOADING           ====
================================= */

jQuery(document).ready(function($) {
    $('.more').on('click', function(event) {
        event.preventDefault();

        var href = $(this).attr('href') + ' .single-project',
            portfolioList = $('#portfolio-list'),
            content = $('#loaded-content');

        portfolioList.animate({'marginLeft':'-120%'},{duration:400,queue:false});
        portfolioList.fadeOut(400);
        setTimeout(function(){ $('#loader').show(); },400);
        setTimeout(function(){
            content.load(href, function() {
                $('#loaded-content meta').remove();
                $('#loader').hide();
                content.fadeIn(600);
                $('#back-button').fadeIn(600);
            });
        },800);

    });

    $('#back-button').on('click', function(event) {
        event.preventDefault();

        var portfolioList = $('#portfolio-list')
            content = $('#loaded-content');

        content.fadeOut(400);
        $('#back-button').fadeOut(400);
        setTimeout(function(){
            portfolioList.animate({'marginLeft':'0'},{duration:400,queue:false});
            portfolioList.fadeIn(600);
        },800);
    });

    
});

/* ================================
===  PARALLAX                  ====
================================= */
$(document).ready(function(){
  var $window = $(window);
  $('div[data-type="background"], header[data-type="background"], section[data-type="background"]').each(function(){
    var $bgobj = $(this);
    $(window).scroll(function() {
      var yPos = -($window.scrollTop() / $bgobj.data('speed'));
      var coords = '50% '+ yPos + 'px';
      $bgobj.css({ 
        backgroundPosition: coords 
      });
    });
  });
  
     $('.tooltipped').tooltip({delay: 50});
     $('.modal-trigger').leanModal();
     
     
  
});

// Close button
function slide(){
      $('.button-collapse').sideNav('hide');
      
      
    }
// Collapse
(function($){
  $(function(){

    $('.button-collapse').sideNav();

  }); // end of document ready
})(jQuery); // end of jQuery name space

function toggle4(){
			$("#box_show4").slideToggle("show");
					}
					
						function toggle5(){
			$("#box_show5").slideToggle("show");
					}
function toggle7(){
      $("#box_show8").slideToggle("show");
        $("#box_show7").slideToggle("hide");
          }
          
          function toggle8(){
      $("#box_show8").slideToggle("show");
        $("#box_show7").slideToggle();
          }
//
//plugin bootstrap minus and plus
//http://jsfiddle.net/laelitenetwork/puJ6G/
$('.btn-number').click(function(e){
    e.preventDefault();
    
    fieldName = $(this).attr('data-field');
    type      = $(this).attr('data-type');
    var input = $("input[name='"+fieldName+"']");
    var currentVal = parseInt(input.val());
    if (!isNaN(currentVal)) {
        if(type == 'minus') {
            
            if(currentVal > input.attr('min')) {
                input.val(currentVal - 1).change();
            } 
            if(parseInt(input.val()) == input.attr('min')) {
                $(this).attr('disabled', true);
            }

        } else if(type == 'plus') {

            if(currentVal < input.attr('max')) {
                input.val(currentVal + 1).change();
            }
            if(parseInt(input.val()) == input.attr('max')) {
                $(this).attr('disabled', true);
            }

        }
    } else {
        input.val(0);
    }
});
$('.input-number').focusin(function(){
   $(this).data('oldValue', $(this).val());
});
$('.input-number').change(function() {
    
    minValue =  parseInt($(this).attr('min'));
    maxValue =  parseInt($(this).attr('max'));
    valueCurrent = parseInt($(this).val());
    
    name = $(this).attr('name');
    if(valueCurrent >= minValue) {
        $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
        alert('Sorry, the minimum value was reached');
        $(this).val($(this).data('oldValue'));
    }
    if(valueCurrent <= maxValue) {
        $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
        alert('Sorry, the maximum value was reached');
        $(this).val($(this).data('oldValue'));
    }
    
    
});
$(".input-number").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
             // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) || 
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
  
// Popout

  $(document).ready(function(){
    $('.collapsible').collapsible({
      accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });
  });
  
  
  
  // Dropdown menu
$(".slide-toggle").click(function(){
            $(".ideabox").animate({
              right: "-1000px"
            });
        });

// Header fixed
//$(window).on("scroll", function() {
//    if($(window).scrollTop() > 50) {
//        $(".header1").addClass("active");
//    } else {
//       
//       $(".header1").removeClass("active");
//    }
//});

// Scroll heading

    function UpdateTableHeaders() {
       $(".persist-area").each(function() {
       
           var el             = $(this),
               offset         = el.offset(),
               scrollTop      = $(window).scrollTop(),
               floatingHeader = $(".floatingHeader", this)
           
           if ((scrollTop > offset.top) && (scrollTop < offset.top + el.height())) {
               floatingHeader.css({
                "visibility": "visible"
               });
           } else {
               floatingHeader.css({
                "visibility": "hidden"
               });      
           };
       });
    }
    
    // DOM Ready      
    $(function() {
    
       var clonedHeaderRow;
    
       $(".persist-area").each(function() {
           clonedHeaderRow = $(".persist-header", this);
           clonedHeaderRow
             .before(clonedHeaderRow.clone())
             .css("width", clonedHeaderRow.width())
             .addClass("floatingHeader");
             
       });
       
       $(window)
        .scroll(UpdateTableHeaders)
        .trigger("scroll");
       
    });
  
  
  
  //fixed on top
function sticky_relocate() {
    var window_top = $(window).scrollTop();
    var div_top = $('#sticky-anchor').offset().top;
    if (window_top > div_top) {
        $('#sticky').addClass('stick');
    } else {
        $('#sticky').removeClass('stick');
    }
}


var buttons = document.getElementsByTagName("button"),
    containers = document.getElementsByClassName("u-fancy-load");

for(var i = 0; i < buttons.length; i++){
 buttons[i].addEventListener("click", function(e){
   var attr = this.getAttribute("data-animation"),
       opposite = attr == "in" ? "out" : "in";
   
   for(var c = 0; c < containers.length; c++){
     containers[c].classList.remove("u-fancy-load--" + opposite);
     containers[c].classList.add("u-fancy-load--" + attr);
   }
 }, false);
}


// List view function
//$(document).ready(function(){
//    $(".grid-view").click(function(){
//        $("#demo").toggleClass("gridbox");
//    });
//});

  
  
  
new WOW().init();

  
  
  
//for item right panel
function toggle8(selector_id2,open_div_id2) {
    var ele = document.getElementById(open_div_id2);
    var text = document.getElementById(selector_id2);
    if(ele.style.display == "block") {
       $('#'+open_div_id2).fadeOut('slow');
     }
     else {
       $('#'+open_div_id2).fadeIn('slow');
       ele.style.display = "block";
       if(open_div_id2 == 'add-now-components')
       {
         $("#components-select").hide();
         $("#components-add").hide();
         $("#components-added").hide();
       }
       if(open_div_id2 == 'components-select')
       {
         $("#add-now-components").hide();
         $("#components-add").hide();
         $("#components-added").hide();
       }
       if(open_div_id2 == 'components-add')
       {
         $("#add-now-components").hide();
         $("#components-select").hide();
         $("#components-added").hide();
       }
    if(open_div_id2 == 'components-added')
       {
         $("#components-select").hide();
         $("#components-add").hide();
         $("#add-now-components").hide();
       }

    if(open_div_id2 == 'hide-con')
       { 
         $("#component-pro").hide();
       }

    if(open_div_id2 == 'component-pro')
       { 
         $("#hide-con").hide();
       }

   
     }
}
$('ul.tabs').tabs();
function toggleAddbrowsebtn(){
  $("#divAddMore").hide();
  $("#add_browse-btn").show();
}

function showAddMore(){
  $("#add_browse-btn").hide();
  $("#divAddMore").show();
}
function toggleAddbrowsebtn2(){
  $("#divAddMore2").hide();
  $("#add_browse-btn2").show();
}

function showAddMore2(){
  $("#add_browse-btn2").hide();
  $("#divAddMore2").show();
}
function toggleAddbrowsebtn3(){
  $("#divAddMore3").hide();
  $("#add_browse-btn3").show();
}

function showAddMore3(){
  $("#add_browse-btn3").hide();
  $("#divAddMore3").show();
}
 
 