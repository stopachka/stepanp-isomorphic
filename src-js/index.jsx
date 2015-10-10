var React = require('react');
var ReactRouter = require('react-router');
var history = require('history');

var x = typeof window === 'undefined' ? global : window;

x.React = React;
x.ReactRouter = ReactRouter;
x.history = history;
