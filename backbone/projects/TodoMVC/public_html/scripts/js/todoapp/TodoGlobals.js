/**
 * Todo Globals object.
 */
define([
],
// This function gets called once when the script is first loaded.
// Subsequent dependencies on the script do not invoke the function again.
// The returned object is therefore like a 'singleton'.
function(){
    var TodoGlobals = TodoGlobals || {};
    TodoGlobals.todoFilter = '';
    
    return TodoGlobals;
});
