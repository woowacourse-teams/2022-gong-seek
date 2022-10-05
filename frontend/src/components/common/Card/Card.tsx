import { PropsWithStrictChildren } from 'gongseek-types';

import * as S from '@/components/common/Card/Card.styles';
import { css } from '@emotion/react';

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
	};
	media?: {
		minWidth: string;
		width?: string;
		height?: string;
	};
	hasActiveAnimation: true | false;
	onClick?: () => void;
}

const Card = ({
	cssObject,
	media,
	hasActiveAnimation,
	onClick,
	children,
}: PropsWithStrictChildren<CardProps>) => (
	<S.Container
		css={css`
			width: ${cssObject.width};
			height: ${cssObject.height};
			padding: ${cssObject.padding ? cssObject.padding : 0};
			max-width: ${cssObject.maxWidth ? cssObject.maxWidth : cssObject.width};
			justify-content: ${cssObject.justifyContent ? cssObject.justifyContent : 'normal'};
			align-items: ${cssObject.alignItems ? cssObject.alignItems : 'normal'};
			gap: ${cssObject.gap ? cssObject.gap : 0};
			flex-direction: ${cssObject.flexDirection ? cssObject.flexDirection : 'column'};
			flex-wrap: ${cssObject.flexWrap ? cssObject.flexWrap : 'nowrap'};
		`}
		media={media ? media : false}
		hasActiveAnimation={hasActiveAnimation}
		onClick={onClick}
	>
		{children}
	</S.Container>
);
export default Card;
