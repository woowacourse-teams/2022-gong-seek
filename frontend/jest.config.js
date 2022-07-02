module.exports = {
	transform: {
		'^.+\\.tsx?$': 'ts-jest',
		'^.+\\.js$': 'babel-jest',
	},
	collectCoverage: true,
	collectCoverageFrom: ['src/**/*.{js,jsx,ts,tsx}'],
	coverageDirectory: 'coverage',
	testEnvironment: 'jsdom',
	setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
	moduleNameMapper: {
		'^@/(.*)$': '<rootDir>/src/$1',
	},
	moduleDirectories: ['node_modules', 'src'],
};
