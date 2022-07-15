const colors = {
	PURPLE_500: '#5E0080',
	PURPLE_400: 'rgba(94, 0, 128, 0.75)',
	RED_600: '#FF0063',
	RED_500: '#F87474',
	RED_400: ' rgba(248, 116, 116, 0.75)',
	BLUE_500: '#3AB0FF',
	BLUE_400: 'rgba(58, 176, 255, 0.75)',
	BLACK_200: '#C2C2C2',
	BLACK_600: '#363636',
	BLACK_500: '#141619',
	BLACK_400: 'rgba(20, 22, 25, 0.75)',
	GRAY_500: 'rgba(0, 0, 0, 0.11)',
	GREEN_500: '#C4DFAA',
	WHITE: '#ffffff',
};

export const voteGradientColors = {
	VOTE_001: 'linear-gradient(-45deg,#9055FF, #13E2DA)',
	VOTE_002: 'linear-gradient(-45deg,#D6FF7F, #00B3CC)',
	VOTE_003: 'linear-gradient(-45deg,#ED7B84,#9055FF)',
	VOTE_004: 'linear-gradient(-45deg,#000066, #6699FF)',
	VOTE_005: 'linear-gradient(-45deg, #F06966,#FAD6A6)',
};

const breakpoints = {
	MOBIL: 320,
	DESKTOP: 1280,
};

const boxShadows = {
	primary: 'rgba(0, 0, 0, 0.25)',
	secondary: 'rgba(0, 0, 0, 0.15)',
};

const size = {
	SIZE_001: '0.063rem',
	SIZE_002: '0.125rem',
	SIZE_004: '0.25rem',
	SIZE_006: '0.375rem',
	SIZE_010: '0.625rem',
	SIZE_012: '0.75rem',
	SIZE_014: '0.875rem',
	SIZE_016: '1rem',
	SIZE_018: '1.125rem',
	SIZE_020: '1.25rem',
	SIZE_022: '1.375rem',
	SIZE_024: '1.5rem',
	SIZE_026: '1.625rem',
	SIZE_028: '1.75rem',
	SIZE_030: '1.875rem',
	SIZE_032: '2rem',
	SIZE_050: '3.125rem',
	SIZE_080: '5rem',
	SIZE_090: '5.625rem',
};

const zIndex = {
	HEADER: 100,
};

export const theme = {
	colors,
	breakpoints,
	boxShadows,
	size,
	zIndex,
	voteGradientColors,
} as const;
