const path = require('path');
const Dotenv = require('dotenv-webpack');

const envPath =
	process.env.NODE_ENV === 'development'
		? path.join(__dirname, '../webpack/.env.development')
		: path.join(__dirname, '../webpack/.env.production');

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
			path: envPath,
		}),
	);

	return config;
};
