/*
 * Adapted from Chapter 2 of "Node.js the Right Way".
 * Usage: node --harmony readstream.js
 */
"use strict";

const
  fs = require('fs'),
  stream = fs.createReadStream(process.argv[2]);

// Use process.stdout instead of console.log, because incoming data
// chunks already contain newline chars from input file.
stream.on('data', function(chunk) {
  process.stdout.write(chunk);
});

// When working with an EventEmitter like Stream, the way to handle
// errors is to listen for error events.
stream.on('error', function(err) {
  process.stderr.write("ERROR: " + err.message + "\n");
});
