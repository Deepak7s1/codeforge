$(document).ready(function() {
  $('#books').cycle({
    timeout: 2000,
    speed: 200,
    pause: true
  });
  $('<div id="books-controls"></div>').insertAfter('#books');
  $('<button>Pause</button>').click(function() {
    $('#books').cycle('pause');
    return false;
  }).appendTo('#books-controls');
  $('<button>Resume</button>').click(function() {
    $('ul:paused').cycle('resume');
    return false;
  }).appendTo('#books-controls');
});
