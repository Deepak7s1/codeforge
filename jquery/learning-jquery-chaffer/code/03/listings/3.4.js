$(document).ready(function() {
  $('#switcher-default')
  .addClass('selected')
  .bind('click', function() {
    $('body').removeClass('narrow').removeClass('large');
  });
  $('#switcher-narrow').bind('click', function() {
    $('body').addClass('narrow').removeClass('large');
  });
  $('#switcher-large').bind('click', function() {
    $('body').removeClass('narrow').addClass('large');
  });

  $('#switcher button').bind('click', function() {
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});
