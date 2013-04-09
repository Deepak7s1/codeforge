(function($) {
  $('div.photo').live('mouseenter mouseleave', function(event) {
    var $details = $(this).find('.details');
    if (event.type == 'mouseenter') {
      $details.fadeTo('fast', 0.7);
    } else {
      $details.fadeOut('fast');
    }
  });

  $(document).bind('nextPage', function() {
    var url = $('#more-photos').attr('href');
    if (url) {
      $.get(url, function(data) {
        $('#gallery').append(data);
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

  $(document).ready(function() {
    $('#more-photos').click(function() {
      $(this).trigger('nextPage');
      return false;
    });
  });
})(jQuery);
