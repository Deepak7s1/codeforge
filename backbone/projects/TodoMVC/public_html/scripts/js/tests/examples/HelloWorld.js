var HelloWorld = function() {};

HelloWorld.prototype.helloWorld = function() {
    return "Hello world!";
};

HelloWorld.prototype.helloSomeone = function(toGreet) {
    return this.sayHello("World") + " " + toGreet;
};

HelloWorld.prototype.sayHello = function(extra) {
    return "Hello" + extra;
};
