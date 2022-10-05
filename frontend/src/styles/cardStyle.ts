import { theme } from './Theme';

import { CardProps } from '@/components/common/Card/Card';

export const ArticleItemCard: CardProps = {
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

export const ArticleContentCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '90%',
		height: 'fit-content',
		padding: theme.size.SIZE_011,
	},
	hasActiveAnimation: false,
};

export const CategorySelectorCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '80%',
		height: theme.size.SIZE_256,
		maxWidth: theme.size.SIZE_400,
		justifyContent: 'space-around',
		alignItems: 'center',
	},
	hasActiveAnimation: false,
};

export const InquireCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
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

export const LoginCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
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

export const WritingTitleCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
	},
	hasActiveAnimation: false,
};

export const WritingCategoryCard: CardProps = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
	},
	hasActiveAnimation: false,
	media: {
		minWidth: theme.breakpoints.DESKTOP_LARGE,
		width: '10%',
		height: 'fit-content',
	},
};

export const WritingHashTagCard: CardProps = {
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
		width: '95%',
		height: 'fit-content',
	},
};
