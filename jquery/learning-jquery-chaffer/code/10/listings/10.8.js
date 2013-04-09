(function($) {
  $('div.photo').live('mouseenter mouseleave', function(event) {
    var $details = $(this).find('.details');
    if (event.type == 'mouseenter') {
      $details.fadeTo('fast', 0.7);
    } else {
      $details.fadeOut('fast');
    }
  });

  $(document).ready(function() {
    $('#more-photos').click(function() {
      $(this).trigger('nextPage');
      return false;
    });
  });
})(jQuery);
