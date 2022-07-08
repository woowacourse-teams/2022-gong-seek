import { theme } from '@/styles/Theme';
import { css } from '@emotion/react';

export const mobileTitlePrimary = css({
	color: '#000000',
	fontWeight: 600,
	fontSize: `${theme.size.SIZE_022}`,
});

export const mobileTitleSecondary = css({
	color: '#000000',
	fontWeight: 700,
	fontSize: `${theme.size.SIZE_020}`,
});

export const desktopTitlePrimary = css({
	color: '#000000',
	fontWeight: 600,
	fontSize: `${theme.size.SIZE_024}`,
});

export const desktopTitleSecondary = css({
	color: '#000000',
	fontWeight: 500,
	fontSize: `${theme.size.SIZE_022}`,
});
