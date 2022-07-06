const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
	entry: './src/index.tsx',
	output: {
		path: path.join(__dirname, '/dist'),
		filename: '[name].bundle.js',
		publicPath: '/',
	},
	devServer: {
		historyApiFallback: true,
	},
	mode: 'development',
	resolve: {
		alias: {
			'@': path.resolve(__dirname, '/src'),
		},
		modules: ['node_modules'],
		extensions: ['.js', '.jsx', '.ts', '.tsx'],
	},
	devtool: 'inline-source-map',
	module: {
		rules: [
			{
				test: /\.(js|jsx|ts|tsx)$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
				},
			},
			{
				test: /\.css$/,
				use: ['style-loader', 'css-loader'],
			},
			{
				test: /\.(png|svg|jpeg|jpg)$/,
				exclude: /node_modules/,
				use: 'file-loader',
			},
		],
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: './public/index.html',
		}),
		new CleanWebpackPlugin(),
	],
};
