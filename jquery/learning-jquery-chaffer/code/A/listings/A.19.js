$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  $('#button-1, #button-2').show();
});

$(document).ready(function() {
  $('input').each(function(index) {
    function clickHandler() {
      $.print('index = ' + index);
      return false;
    }

    $(this).click(clickHandler);
  });
});
