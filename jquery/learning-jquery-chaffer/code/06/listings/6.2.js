$(document).ready(function() {
  $('#letter-a a').click(function() {
    $('#dictionary').load('a.html');
    alert('Loaded!');
    return false;
  });
});
