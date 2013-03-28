/**
 * Todo Router.
 */
define([
    'js/todoapp/TodoGlobals'
], 
function(TodoGlobals){
    'use strict';

    var TodoRouter = Backbone.Router.extend({
        routes:{
            '*filter': 'setFilter'
        },

        // param can be either 'active', 'completed', or ''.
        setFilter: function( param ) {            
            // Set the current filter to be used
            TodoGlobals.todoFilter = param.trim() || '';

            // Trigger a collection filter event, causing hiding/unhiding
            // of Todo view items
            if (TodoGlobals.todos) {
                TodoGlobals.todos.trigger('filter');
            }
        }
    });

    return TodoRouter;
});
