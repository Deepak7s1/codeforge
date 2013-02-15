require.config({
    // Needed in order to tell require where to load from if not loading from data-main.
    // By default load any module IDs from js/libs...
    baseUrl: 'js/libs',

    // ...except, if the module ID starts with "js", load it from the js directory. 
    // paths config is relative to the baseUrl, and never includes a ".js" extension
    // as the paths config could be for a directory.  
    paths: {
        js: '..'
    },
    shim: {
    }
});

require([
    "js/util/StringUtils"
], 

function(StringUtils) {
    // This function is called when all the required modules are loaded.
    // If a module calls define(), then this function is not fired until
    // the module's dependencies have loaded.
    // 
    // The StringUtils param will hold the StringUtil module object.

    var name = StringUtils.append("hello", "world");
    $("#loadingSeqDiv").append("<p>" + name + "</p>");

});
