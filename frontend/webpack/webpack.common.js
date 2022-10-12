const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
	entry: './src/index.tsx',
	output: {
		path: path.join(__dirname, '../dist'),
		filename: '[name].[contenthash].js',
		publicPath: '/',
		clean: true,
		pathinfo: false,
	},
	resolve: {
		alias: {
			'@': path.resolve(__dirname, '/src'),
		},
		modules: ['node_modules'],
		extensions: ['.js', '.jsx', '.ts', '.tsx'],
		symlinks: false,
	},
	devServer: {
		historyApiFallback: true,
		port: 3000,
		hot: true,
	},
	module: {
		rules: [
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
		runtimeChunk: true,
	},
};
