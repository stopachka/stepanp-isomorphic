var path = require('path');

module.exports = {
  entry: ['./src-js'],
  resolve: {
    modulesDirectories: ['node_modules'],
    extensions: ['', '.js', '.jsx']
  },
  output: {
    path: './resources/js',
    filename: 'js-bundle.js'
  },
  module: {
    loaders: [{test: /\.jsx?$/, exclude: /node_modules/, loaders: ['babel']}]
  },
};
