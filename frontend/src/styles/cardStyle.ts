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

export const HashTagCard: Pick<CardProps, 'cssObject' | 'hasActiveAnimation'> = {
	cssObject: {
		width: '100%',
		height: 'fit-content',
		justifyContent: 'flex-start',
		flexWrap: 'wrap',
		flexDirection: 'row',
		padding: theme.size.SIZE_004,
	},
	hasActiveAnimation: false,
};
