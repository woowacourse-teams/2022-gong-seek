import { theme } from '@/styles/Theme';
import { css } from '@emotion/react';

export const mobileTitlePrimary = css({
	color: '#000000',
	fontWeight: 600,
	fontSize: `${theme.fonts.SIZE_022}`,
});

export const mobileTitleSecondary = css({
	color: '#000000',
	fontWeight: 700,
	fontSize: `${theme.fonts.SIZE_020}`,
});

export const desktopTitlePrimary = css({
	color: '#000000',
	fontWeight: 600,
	fontSize: `${theme.fonts.SIZE_024}`,
});

export const desktopTitleSecondary = css({
	color: '#000000',
	fontWeight: 500,
	fontSize: `${theme.fonts.SIZE_022}`,
});
