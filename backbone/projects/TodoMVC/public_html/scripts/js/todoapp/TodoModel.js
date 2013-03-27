/**
 * Our basic **Todo** model has `title`, `order`, and `completed` attributes.
 */
define([

], function(){
	'use strict';

    var TodoModel = Backbone.Model.extend({
		// Default attributes for the todo
		// and ensure that each todo created has `title` and `completed` keys.
		defaults: {
			title: '',
			completed: false
		},

		// Toggle the `completed` state of this todo item.
		toggle: function() {
			this.save({
				completed: !this.get('completed')
			});
		}
    });
    return TodoModel;
});
