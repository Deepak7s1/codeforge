$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

var globalVar;

function outerFn() {
  $.print('Outer function');
  function innerFn() {
    $.print('Inner function');
  }
  globalVar = innerFn;
}
$.print('outerFn():');
outerFn();
$.print('globalVar():');
globalVar();
