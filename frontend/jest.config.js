module.exports = {
	transform: {
		'^.+\\.tsx?$': 'ts-jest',
		'^.+\\.js$': 'babel-jest',
		'.+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$': 'jest-transform-stub',
	},
	collectCoverage: false,
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
	moduleFileExtensions: ['tsx', 'js', 'json', 'node', 'ts', 'svg'],
	collectCoverageFrom: ['src/**/*.{js,jsx,ts,tsx}'],
	coverageDirectory: 'coverage',
	testEnvironment: 'jsdom',
	setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
	moduleNameMapper: {
		'^@/(.*)$': '<rootDir>/src/$1',
	},
	moduleDirectories: ['node_modules', 'src'],
};
