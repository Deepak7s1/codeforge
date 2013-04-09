$(document).ready(function() {
  $('#switcher-default')
  .addClass('selected')
  .bind('click', function() {
    $('body').removeClass();
  });
  $('#switcher-narrow').bind('click', function() {
    $('body').removeClass().addClass('narrow');
  });
  $('#switcher-large').bind('click', function() {
    $('body').removeClass().addClass('large');
  });

  $('#switcher button').bind('click', function() {
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});
