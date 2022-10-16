import { theme } from '@/styles/Theme';
import { CardProps } from '@/types/card';

export const PopularArticleItemCardStyle = {
	cssObject: {
		width: '85%',
		height: 'fit-content',
		flexShrink: '0',
		scrollSnapAlign: 'center',
		scrollSnapStop: 'always',
	},
	media: {
		minWidth: theme.breakpoints.DESKTOP_LARGE,
		width: '50%',
		height: 'fit-content',
	},

	hasActiveAnimation: true,
};

export const ArticleItemCardStyle: CardProps = {
	cssObject: {
		width: '80%',
		height: theme.size.SIZE_160,
		padding: theme.size.SIZE_016,
	},
	media: {
		minWidth: theme.breakpoints.DESKTOP_LARGE,
		width: theme.size.SIZE_300,
		height: theme.size.SIZE_220,
	},
	hasActiveAnimation: true,
};

export const ArticleContentCardStyle: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '90%',
		height: 'fit-content',
		padding: theme.size.SIZE_011,
	},
	hasActiveAnimation: false,
};

export const CategorySelectorCardStyle: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '80%',
		height: theme.size.SIZE_256,
		maxWidth: theme.size.SIZE_400,
		justifyContent: 'space-around',
		alignItems: 'center',
	},
	hasActiveAnimation: false,
};

export const InquireCardStyle: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '80%',
		height: theme.size.SIZE_280,
		maxWidth: theme.size.SIZE_400,
		justifyContent: 'space-around',
		padding: theme.size.SIZE_016,
		alignItems: 'center',
	},
	hasActiveAnimation: false,
};

export const LoginCardStyle: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '80%',
		maxWidth: theme.size.SIZE_400,
		height: theme.size.SIZE_240,
		justifyContent: 'space-around',
		padding: theme.size.SIZE_010,
		alignItems: 'center',
	},
	hasActiveAnimation: false,
};

export const WritingTitleCardStyle: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
	},
	hasActiveAnimation: false,
};

export const WritingCategoryCardStyle: CardProps = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
		margin: `${theme.size.SIZE_006} 0 0 0`,
	},
	hasActiveAnimation: false,
	media: {
		minWidth: theme.breakpoints.DESKTOP_LARGE,
		width: '25%',
		height: 'fit-content',
	},
};

export const WritingHashTagCardStyle: CardProps = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
		justifyContent: 'flex-start',
		flexWrap: 'wrap',
		flexDirection: 'row',
		padding: theme.size.SIZE_004,
	},
	hasActiveAnimation: false,
	media: {
		minWidth: theme.breakpoints.DESKTOP_LARGE,
		width: '97%',
		height: 'fit-content',
	},
};
