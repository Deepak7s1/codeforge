define([
], 

function() {
    $("#loadingSeqDiv").append("<p>Loading StringUtils...</p>");

    var StringUtils = {};

    StringUtils.append = function(s1, s2) {
        return s1 + s2;
    }

    return StringUtils;
});