const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');
const webpack = require('webpack');
const dotenv = require('dotenv');
const WebpackBundleAnalyzer = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

dotenv.config({
	path: path.join(__dirname, './.env.development'),
});

module.exports = merge(common, {
	mode: 'development',
	devtool: 'source-map',
	plugins: [
		new webpack.DefinePlugin({
			'process.env': JSON.stringify(process.env),
		}),
		// new WebpackBundleAnalyzer({
		// 	analyzerMode: 'static',
		// 	openAnalyzer: false,
		// 	generateStatsFile: true,
		// 	statsFilename: 'bundle-report.json',
		// }),
	],
	optimization: {
		minimize: true,
	},
});
