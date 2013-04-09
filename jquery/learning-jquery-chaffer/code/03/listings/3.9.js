$(document).ready(function() {
  $('#switcher h3').toggle(function() {
    $('#switcher button').addClass('hidden');
  }, function() {
    $('#switcher button').removeClass('hidden');
  });
});

$(document).ready(function() {
  $('#switcher-default').addClass('selected');

  $('#switcher button').click(function() {
    var bodyClass = this.id.split('-')[1];

    $('body').removeClass().addClass(bodyClass);

    $('#switcher button').removeClass('selected');
    $(this).addClass('selected');
  });
});

