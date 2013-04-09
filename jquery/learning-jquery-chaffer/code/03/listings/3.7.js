$(document).ready(function() {
  $('#switcher-default').addClass('selected');

  $('#switcher button').bind('click', function() {
    var bodyClass = this.id.split('-')[1];

    $('body').removeClass().addClass(bodyClass);

    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});
