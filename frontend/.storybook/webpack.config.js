const path = require('path');

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
	return config;
};
