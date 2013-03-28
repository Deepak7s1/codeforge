/**
 * Todo item view.
 * When creating a new View, the options you pass — after being merged into 
 * any default options already present on the view — are attached to the view 
 * as this.options for future reference.
 *
 * There are several special options that, if passed, will be attached directly 
 * to the view: model, collection, el, id, className, tagName and attributes.
 * 
 * If the view defines an initialize function, it will be called when the view 
 * is first created. 
 */
define([
    'text!templates/todo/todo-item.template',
    'js/todoapp/TodoGlobals'
], 

function(ItemTemplate, TodoGlobals){
    'use strict';

    /*
     * Pass in a TodoModel when instantiating TodoView.
     */
    var TodoView = Backbone.View.extend({

        //... is a list tag.
        tagName:  'li',

        // Cache the template function for a single item.
        template: _.template( ItemTemplate ),

        // The DOM events specific to an item.
        events: {
            'click .toggle':	'togglecompleted',
            'dblclick label':	'edit',
            'click .destroy':	'clear',
            'keypress .edit':	'updateOnEnter',
            'blur .edit':		'close'
        },

        // The TodoView listens for changes to its model, re-rendering. Since there's
        // a one-to-one correspondence between a **Todo** and a **TodoView** in this
        // app, we set a direct reference on the model for convenience.
        initialize: function() {
            this.model.on( 'change', this.render, this );
            this.model.on( 'destroy', this.remove, this );
            this.model.on( 'visible', this.toggleVisible, this );
        },

        // Re-render the titles of the todo item.
        render: function() {
            this.$el.html( this.template( this.model.toJSON() ) );
            this.$el.toggleClass( 'completed', this.model.get('completed') );

            this.toggleVisible();
            this.input = this.$('.edit');
            return this;
        },

        toggleVisible : function () {
            this.$el.toggleClass( 'hidden',  this.isHidden());
        },

        isHidden : function () {
            var isCompleted = this.model.get('completed');
            return ( // hidden cases only
                (!isCompleted && TodoGlobals.todoFilter === 'completed')
                || (isCompleted && TodoGlobals.todoFilter === 'active')
            );
        },

        // Toggle the `"completed"` state of the model.
        togglecompleted: function() {
            this.model.toggle();
        },

        // Switch this view into `"editing"` mode, displaying the input field.
        edit: function() {
            this.$el.addClass('editing');
            this.input.focus();
        },

        // Close the `"editing"` mode, saving changes to the todo.
        close: function() {
            var value = this.input.val().trim();

            if ( value ) {
                this.model.save({ title: value });
            } else {
                this.clear();
            }

            this.$el.removeClass('editing');
        },

        // If you hit `enter`, we're through editing the item.
        updateOnEnter: function( e ) {
            if ( e.which === ENTER_KEY ) {
                this.close();
            }
        },

        // Remove the item, destroy the model from *localStorage* and delete its view.
        clear: function() {
            this.model.destroy();
        }
    });
    return TodoView;
});
