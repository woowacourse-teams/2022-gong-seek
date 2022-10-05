const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
	entry: './src/index.tsx',
	output: {
		path: path.join(__dirname, '../dist'),
		filename: '[name].bundle.js',
		publicPath: '/',
		clean: true,
	},
	resolve: {
		alias: {
			'@': path.resolve(__dirname, '/src'),
		},
		modules: ['node_modules'],
		extensions: ['.js', '.jsx', '.ts', '.tsx'],
	},
	devServer: {
		historyApiFallback: true,
		port: 3000,
		hot: true,
	},
	devtool: 'source-map',
	module: {
		rules: [
			{
				test: /\.(js|jsx|ts|tsx)$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
				},
				generator: {
					filename: '[name].[contenthash].js',
				},
			},
			{
				test: /\.css$/,
				use: ['style-loader', 'css-loader'],
				generator: {
					filename: '[name].[contenthash].css',
				},
			},
			{
				test: /\.(png|svg|jpeg|jpg)$/,
				exclude: /node_modules/,
				type: 'asset/resource',
			},
		],
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: './public/index.html',
			favicon: './public/favicon-32x32.png',
		}),
	],
	optimization: {
		minimize: true,
	},
};
