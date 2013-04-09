$(document).ready(function() {
  $('#switcher-default').addClass('selected');

  $('#switcher button').bind('click', function() {
    $('body').removeClass();
    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });

  $('#switcher-narrow').bind('click', function() {
    $('body').addClass('narrow');
  });
  $('#switcher-large').bind('click', function() {
    $('body').addClass('large');
  });
});
