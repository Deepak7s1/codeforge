$(document).ready(function() {
  $('#letter-a a').click(function() {
    $('#dictionary').load('a.html');
    return false;
  });

  $('#letter-b a').click(function() {
    $.getJSON('b.json', function(data) {
    });
    return false;
  });
});
