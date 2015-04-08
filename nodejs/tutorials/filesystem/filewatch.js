/*
 * Adapted from Chapter 2 of "Node.js the Right Way".
 * Usage: node --harmony filewatch.js
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
  console.log("File '" + filename + "' just changed.");

  // 'let' declares a variable which can be assigned a value more than once.
  // The object returned by spawn() is a ChildProcess.
  let ls = spawn('ls', ['-lh', filename]);
  ls.stdout.pipe(process.stdout);
});

console.log("Now watching '" + filename + "' for changes...");

