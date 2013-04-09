$.print = function(message) {
  $(document).ready(function() {
    $('<div class="result"><div>')
      .text(String(message))
      .appendTo('#results');
  });
};

$(document).ready(function() {
  for (var i = 0; i < 5; i++) {
    $('<div>Print ' + i + '</div>')
      .bind('click', {value: i}, function(event) {
        $.print(event.data.value);
      }).insertBefore('#results');
  }
});
