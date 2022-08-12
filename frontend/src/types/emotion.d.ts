import { theme } from '@/styles/theme';

type ThemeConfig = typeof theme;
declare module '@emotion/react' {
	export interface Theme extends ThemeConfig {}

	export interface Size {
		width?: string;
		height?: string;
		padding?: string;
		display?: string;
		flexDirection?: string;
		flexWrap?: string;
		alignItems?: string;
		gap?: string;
		justifyContent?: string;
		maxWidth?: string;
	}
}
