// Run code as soon as document is ready.
$( document ).ready(function() {

    /**
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
    var BookItemView = BaseView.extend({  // BaseView is an extension of Backbone.View.
                                          // See extensions.js.
        tagName: 'li',

        initialize: function() {
            console.log("Initialize MyView");

            // Re-render each time the model changes.
            // this.model.on("change", this.render, this);   // if extend from Backbone.View
            this.bindTo(this.model, 'change', this.render);  // if extend from BaseView
        },

        render: function() {
            console.log("Render MyView");
            this.$el.html(this.model.get('title'));

            // A common Backbone convention is to return this at the end of render().
            // This allows sub-views to be easily reusable in parent views.
            return this;
        }
    });


    var BookModel = Backbone.Model.extend({
        // Default todo attribute values
        defaults: {
            title: '',
            author: ''
        },

        initialize: function(){
            console.log('Initialize MyModel.');
        }
    });


    var bookModel = new BookModel({title: 'Object-Oriented JavaScript'});

    var itemView = new BookItemView({model : bookModel});
    $('#a-list').append(itemView.render().el);


    // Example to see the view change as the model's title changes.
    window.setTimeout(
        function () {
            bookModel.set({title: 'Object-Oriented JavaScript 2nd Edition'});
        }, 2000);

    
});
