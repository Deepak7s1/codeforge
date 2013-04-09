$(document).ready(function() {
  var $ajaxForm = $('#ajax-form'),
      $response = $('#response');

  $ajaxForm.bind('submit', function(event) {
    event.preventDefault();

    $.ajax({
      url: 'http://api.jquery.com/jsonp/',
      dataType: 'jsonp',
      data: {
        title: $('#title').val()
      },
      success: function(data) {
        console.log(data);
      }
    });
  });
});
