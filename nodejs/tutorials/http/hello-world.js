"use strict";

const http = require('http');
const serverPort = 8124;
const serverHostname = "127.0.0.1";

http.createServer(function(req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello Word!\n');
}).listen(serverPort, serverHostname);

// Using ES6 string interpolation.
console.log(`Server running at http://${serverHostname}:${serverPort}/`);
