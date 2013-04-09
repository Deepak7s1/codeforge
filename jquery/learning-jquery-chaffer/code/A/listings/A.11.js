$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  var readyVar = 0;
  function innerFn() {
    readyVar++;
    $.print('readyVar = ' + readyVar);
  }
  innerFn();
  innerFn();
});
