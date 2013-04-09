$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

function outerFn() {
  $.print('Outer function');
  function innerFn() {
    $.print('Inner Function');
  }
}
$.print('innerFn():');
innerFn();
