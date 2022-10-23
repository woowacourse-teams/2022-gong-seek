const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');
const webpack = require('webpack');
const dotenv = require('dotenv');

dotenv.config({
	path: path.join(__dirname, './.env.development'),
});

module.exports = merge(common, {
	mode: 'production',
	devtool: false,
	cache: {
		type: 'filesystem',
	},
	module: {
		rules: [
			{
				test: /\.(js|jsx|ts|tsx)?$/,
				loader: 'babel-loader',
				exclude: /node_modules/,
				options: {
					cacheCompression: false,
					cacheDirectory: true,
				},
			},
		],
	},
	plugins: [
		new webpack.DefinePlugin({
			'process.env': JSON.stringify(process.env),
		}),
	],
	optimization: {
		minimize: false,
	},
});
