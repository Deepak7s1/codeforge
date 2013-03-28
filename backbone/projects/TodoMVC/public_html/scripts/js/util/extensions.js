/**
 * Extends Backbone.View to better handle event binding/unbinding.
 * The basic idea is that BaseView will keep a reference to all of the 
 * events to which it's subscribed to, so that when it's time to dispose 
 * the View, all of those bindings will automatically be unbound. 
 * 
 * See http://stackoverflow.com/questions/7567404/backbone-js-repopulate-or-recreate-the-view/7607853#7607853
 */
var BaseView = function (options) {
    this.bindings = [];
    Backbone.View.apply(this, [options]);
};


_.extend(BaseView.prototype, Backbone.View.prototype, {

    bindTo: function (model, ev, callback) {
        model.bind(ev, callback, this);
        this.bindings.push({ model: model, ev: ev, callback: callback });
    },

    unbindFromAll: function () {
        _.each(this.bindings, function (binding) {
            binding.model.unbind(binding.ev, binding.callback);
        });
        this.bindings = [];
    },

    dispose: function () {
        // Unbind all events this view has bound to.
        this.unbindFromAll(); 

        // This will unbind all listeners to events from this view.
        // This is probably not necessary because this view will be garbage collected.
        this.unbind();

        // Uses the default Backbone.View.remove() method which 
        // removes this.el from the DOM and removes DOM events.        
        this.remove();
    }

});

BaseView.extend = Backbone.View.extend;

