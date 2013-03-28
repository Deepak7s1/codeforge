// Changes Underscore's template settings to use different symbols to 
// set off interpolated code. Define:
// (1) an interpolate regex to match expressions that should be interpolated verbatim
// (2) an escape regex to match expressions that should be inserted after being HTML escaped
// (3) an evaluate regex to execute arbitrary javascript code 
_.templateSettings = {
    interpolate : /\{\{(.+?)\}\}/g,
    escape: /\{\{\{(.+?)\}\}\}/g,
    evaluate: /\[\[(.+?)\]\]/g
};
