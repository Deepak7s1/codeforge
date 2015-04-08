/*
 * Adapted from Chapter 2 of "Node.js the Right Way".
 * Usage: node --harmony filewatch2.js
 */
"use strict";

const                                     // ECMAScript Harmony 'const' keyword.
  fs = require('fs'),                     // require() pulls in a Node module.
  spawn = require('child_process').spawn,
  filename = process.argv[2];             // 3rd argument contains file name.

if (!filename) {
  throw Error("A file to watch must be specified.");
}

// watch() polls the target file for changes and invokes the given callback.
fs.watch(filename, function() {
  // 'let' declares a variable which can be assigned a value more than once.
  // The object returned by spawn() is a ChildProcess.
  let
    ls = spawn('ls', ['-lh', filename]),
    output = '';

  // Listen for data events from the ls child process' stdout Stream.
  // data events pass along a Buffer object, Node's way of representing binary data.
  ls.stdout.on('data', function(chunk) {
    output += chunk.toString();
  });

  // When a child process has exited and all its streams have been flushed,
  // it emits a close event.
  ls.on('close', function() {
    let parts = output.split(/\s+/);
    console.dir([parts[0], parts[4], parts[8]]);
  });
});

console.log("Now watching '" + filename + "' for changes...");

