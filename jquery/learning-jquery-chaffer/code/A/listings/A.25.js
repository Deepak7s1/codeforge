$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $('#button-1').show();
});

$(document).ready(function() {
  var $button = $('#button-1');
  $button.click(function() {
    $.print('hello');
    return false;
  });
});
