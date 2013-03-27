require.config({
    // Needed in order to tell require where to load from if not loading from data-main.
    // By default load any module IDs from jslibs.
    baseUrl: 'jslibs',

    // However, if the module ID starts with "js", load it from the scripts/js directory.
    // And if the module ID starts with "templates", load it from the templates directory.
    //
    // paths config is relative to the baseUrl, and never includes a ".js" extension as the 
    // paths config could be for a directory.
    paths: {
        js: '../scripts/js',
        templates: '../templates'
    },
    shim: {
    }
});

require([
    "js/todoapp/TodoApp",
    "js/todoapp/TodoRouter"
],

function(TodoApp, TodoRouter) {
    // This function is called when all the required modules are loaded.
    // If a module calls define(), then this function is not fired until
    // the module's dependencies have loaded.
    //
    var router = new TodoRouter();    

    if (!Backbone.History.started) {
        Backbone.history.start();
    }
    else {
        Backbone.history.loadUrl(Backbone.history.getHash());
    }

    var app = new TodoApp( { todoRouter : router } );
});
