$(document).ready(function() {
  var $ajaxForm = $('#ajax-form'),
      $response = $('#response');
  
  $ajaxForm.bind('submit', function(event) {
    event.preventDefault();
    $response.load('http://api.jquery.com/ #content',
      $ajaxForm.serialize());
  });
});
