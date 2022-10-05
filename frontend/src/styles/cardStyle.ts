import { theme } from './Theme';

import { CardProps } from '@/components/common/Card/Card';

export const ArticleItemCard: Pick<CardProps, 'cssObject' | 'media'> = {
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
};
