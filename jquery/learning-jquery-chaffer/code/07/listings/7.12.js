$(document).ready(function() {
  $('#books').cycle({
    timeout: 2000,
    speed: 200,
    pause: true
  });
  if ( $.cookie('cyclePaused') ) {
    $('#books').cycle('pause');
  }
  $('<div id="books-controls"></div>').insertAfter('#books');
  $('<button>Pause</button>').click(function() {
    $('#books').cycle('pause');
    $.cookie('cyclePaused', 'y');
    return false;
  }).appendTo('#books-controls');
  $('<button>Resume</button>').click(function() {
    var $paused = $('ul:paused');
    if ($paused.length) {
      $paused.cycle('resume');
      $.cookie('cyclePaused', null);
    }
    else {
      $(this).effect('shake', {
        distance: 10,
        duration: 80
      });
    }
    return false;
  }).appendTo('#books-controls');

  $('#books').hover(function() {
    $('#books .title').animate({
      backgroundColor: '#eee',
      color: '#000'
    }, 1000);
  }, function() {
    $('#books .title').animate({
      backgroundColor: '#000',
      color: '#fff'
    }, 1000);
  });

  $('h1').click(function() {
    $(this).toggleClass('highlighted', 'slow', 'easeInExpo');
  });

  $('#books .title').resizable();
});
