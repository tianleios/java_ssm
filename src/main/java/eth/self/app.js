
var keythereum = require("keythereum");
var datadir = "/Users/tianlei/Desktop";

// Synchronous
var keyObject = keythereum.importFromFile("dc5a40c19daa00be31db4f2beb29e65e27c19fe0", datadir);
var privateKey = keythereum.recover(password, keyObject);
console.log(privateKey);