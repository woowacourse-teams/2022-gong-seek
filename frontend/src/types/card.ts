export interface CardProps {
	cssObject: {
		width: string;
		maxWidth?: string;
		height: string;
		justifyContent?: string;
		alignItems?: string;
		gap?: string;
		padding?: string;
		flexWrap?: string;
		flexDirection?: string;
		margin?: string;
		flexShrink?: string;
		scrollSnapAlign?: string;
		scrollSnapStop?: string;
	};
	media?: {
		minWidth: string;
		width?: string;
		height?: string;
	};
	hasActiveAnimation: true | false;
	onClick?: () => void;
	isActive?: boolean;
}
