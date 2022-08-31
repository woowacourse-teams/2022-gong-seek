const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');
const webpack = require('webpack');
const dotenv = require('dotenv');

dotenv.config({
	path: path.join(__dirname, './.env.development'),
});

module.exports = merge(common, {
	mode: 'development',
	devtool: 'inline-source-map',
	devServer: {
		historyApiFallback: true,
		port: 3000,
		hot: true,
	},
	plugins: [
		new webpack.DefinePlugin({
			'process.env': JSON.stringify(process.env),
		}),
	],
});
