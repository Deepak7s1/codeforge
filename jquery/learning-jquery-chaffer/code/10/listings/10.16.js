(function($) {
  $.event.special.throttledScroll = {
    setup: function(data) {
      var timer = 0;
      $(this).bind('scroll.throttledScroll', function(event) {
        if (!timer) {
          timer = setTimeout(function() {
            $(this).triggerHandler('throttledScroll');
            timer = 0;
          }, 250);
        }
      });
    },
    teardown: function() {
      $(this).unbind('scroll.throttledScroll');
    }
  };

  $('div.photo').live('mouseenter mouseleave', function(event) {
    var $details = $(this).find('.details');
    if (event.type == 'mouseenter') {
      $details.fadeTo('fast', 0.7);
    } else {
      $details.fadeOut('fast');
    }
  });

  $(document).bind('nextPage', function(event, scrollToVisible) {
    var url = $('#more-photos').attr('href');
    if (url) {
      $.get(url, function(data) {
        var $data = $(data).appendTo('#gallery');
        if (scrollToVisible) {
          var newTop = $data.offset().top;
          $(window).scrollTop(newTop);
        }
        checkScrollPosition();
      });
    }
  });

  var pageNum = 1;
  $(document).bind('nextPage', function() {
    pageNum++;
    if (pageNum < 20) {
      $('#more-photos').attr('href', 'pages/' + pageNum + '.html');
    }
    else {
      $('#more-photos').remove();
    }
  });

  var $window = $(window);
  function checkScrollPosition() {
    var distance = $window.scrollTop() + $window.height();
    if ($('#container').height() <= distance) {
      $(document).trigger('nextPage');
    }
  }

  $(document).ready(function() {
    $('#more-photos').click(function() {
      $(this).trigger('nextPage', [true]);
      return false;
    });

    $window
      .bind('throttledScroll', checkScrollPosition)
      .trigger('throttledScroll');
  });
})(jQuery);
