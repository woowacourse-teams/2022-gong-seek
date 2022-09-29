const colors = {
	PURPLE_100: '#EBB3FF',
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
	GRAY_100: '#f5f5f5',
	GRAY_500: 'rgba(0, 0, 0, 0.11)',
	GREEN_500: '#C4DFAA',
	ORANGE_500: '#FFB562',
	WHITE: '#ffffff',
};

export const voteGradientColors = {
	VOTE_001: 'linear-gradient(-45deg,#9055FF, #13E2DA)',
	VOTE_002: 'linear-gradient(-45deg,#D6FF7F, #00B3CC)',
	VOTE_003: 'linear-gradient(-45deg,#ED7B84,#9055FF)',
	VOTE_004: 'linear-gradient(-45deg,#000066, #6699FF)',
	VOTE_005: 'linear-gradient(-45deg, #F06966,#FAD6A6)',
};

export const articleColors = {
	ARTICLE_001: '#9055FF',
	ARTICLE_002: '#13E2DA',
	ARTICLE_003: '#D6FF7F',
	ARTICLE_004: '#00B3CC',
	ARTICLE_005: '#ED7B84',
	ARTICLE_006: '#FAD6A6',
	ARTICLE_007: '#6699FF',
	ARTICLE_008: '#FBB454',
	ARTICLE_009: '#FFCAC9',
	ARTICLE_010: '#B2FFDA',
};

const breakpoints = {
	MOBIL: '320px',
	DESKTOP_SMALL: '700px',
	DESKTOP_MIDDLE: '1000px',
	DESKTOP_LARGE: '1280px',
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
	SIZE_008: '0.5rem',
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
	SIZE_035: '2.1875rem',
	SIZE_040: '2.5rem',
	SIZE_050: '3.125rem',
	SIZE_056: '3.5rem',
	SIZE_060: '3.75rem',
	SIZE_080: '5rem',
	SIZE_090: '5.625rem',
	SIZE_100: '6.25rem',
	SIZE_110: '6.875rem',
	SIZE_126: '7.875rem',
	SIZE_160: '10rem',
	SIZE_200: '12.5rem',
	SIZE_220: '13.75rem',
	SIZE_240: '15rem',
	SIZE_260: '16.25rem',
	SIZE_300: '18.75rem',
	SIZE_700: '43.75rem',
};

const zIndex = {
	HEADER: 100,
	ARTICLE_POPULAR_CONTENT: 102,
	ARTICLE_BACKGROUND_CONTENT: 101,
	ARTICLE_ARROW_BUTTON: 103,
	MENU_SLIDER_BACKGROUND: 104,
	MENU_SLIDER_CONTENT: 105,
	POPULAR_ARTICLES: 1,
	SNACK_BAR_BACKGROUND: 112,
	SNACK_BAR: 200,
	COMMON_INPUT_MODAL_BACKGROUND: 110,
	COMMON_INPUT_MODAL: 111,
	ARTICLE_ITEM_LIST: 50,
	CATEGORY_TITLE_CONTAINER: 90,
};

export const theme = {
	colors,
	breakpoints,
	boxShadows,
	size,
	zIndex,
	voteGradientColors,
	articleColors,
} as const;
