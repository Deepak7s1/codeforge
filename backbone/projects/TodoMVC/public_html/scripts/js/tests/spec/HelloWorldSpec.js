describe("HelloWorld Test Suite", function() {

    beforeEach(function() {
        // Add a custom matcher to check divisibility by 2.
        this.addMatchers({
            toBeDivisibleByTwo: function() {
                return (this.actual % 2) === 0;
            }
        });
    });

    it("Should return 'Hello world!'", function() {
        expect(new HelloWorld().helloWorld()).toEqual("Hello world!");
    });

    it("Should be 'Hello world!'", function() {
        expect(new HelloWorld().helloWorld()).not.toBe("HELLO WORLD!");
    });

    it("Should return contains 'world'", function() {
        expect(new HelloWorld().helloWorld()).toContain("world");
    });

    it("Should have length divisible by two", function() {
        expect(new HelloWorld().helloWorld().length).toBeDivisibleByTwo();
    });

    it("Should call sayHello() from helloSomeone()", function() {
        var person = new HelloWorld();
        spyOn(person, "sayHello");
        person.helloSomeone("Gildarts");
        expect(person.sayHello).toHaveBeenCalled();
    });

    it("Should call sayHello() from helloSomeone() with 'World' as param", function() {
        var person = new HelloWorld();
        spyOn(person, "sayHello");
        person.helloSomeone("Gildarts");
        expect(person.sayHello).toHaveBeenCalledWith("World");
    });

    it("Should return 'ello ello <name>' from helloSomeone() using dummy call", function() {
        var person = new HelloWorld();
        person.sayHello = jasmine.createSpy('dummyFunction').andReturn("ello ello");
        expect(person.helloSomeone("Gildarts")).toEqual("ello ello Gildarts");
    });

});
