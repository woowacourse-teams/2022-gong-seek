const path = require('path');
const Dotenv = require('dotenv-webpack');

module.exports = ({ config }) => {
	config.resolve.alias = {
		'@': path.resolve(__dirname, '../src'),
	};

	config.module.rules.push({
		test: /\.(ts|tsx)$/,
		loader: require.resolve('babel-loader'),
		options: {
			plugins: [require.resolve('@emotion/babel-plugin')],
		},
	});

	config.resolve.extensions.push('.ts', '.tsx');

	config.plugins.push(
		new Dotenv({
			path: path.join(__dirname, '../webpack/.env.development'),
		}),
	);

	return config;
};
