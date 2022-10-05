import { PropsWithStrictChildren } from 'gongseek-types';

import * as S from '@/components/common/Card/Card.styles';
import { css, keyframes } from '@emotion/react';

export interface CardProps {
	cssObject: {
		width: string;
		maxWidth?: string;
		height: string;
		justifyContent?: string;
		alignItems?: string;
		gap?: string;
		padding?: string;
	};
	media?: {
		minWidth: string;
		width?: string;
		height?: string;
	};
	hasActiveAnimation: true | false;
}

const scaleAnimation = keyframes`
	0%{

	}

	100% {
		transform: scale(1.01);
	}
`;

const Card = ({
	cssObject,
	media,
	hasActiveAnimation,
	children,
}: PropsWithStrictChildren<CardProps>) => (
	<S.Container
		css={css`
			width: ${cssObject.width};
			height: ${cssObject.height};
			padding: ${cssObject.padding ? cssObject.padding : 0};
			max-width: ${cssObject.maxWidth ? cssObject.maxWidth : cssObject.width};
			justify-content: ${cssObject.justifyContent ? cssObject.justifyContent : 'center'};
			align-items: ${cssObject.alignItems ? cssObject.alignItems : 'center'};
			gap: ${cssObject.gap ? cssObject.gap : 0};

			@media (min-width: ${media ? media.minWidth : ''}) {
				width: ${media ? media.width : cssObject.width};
				height: ${media ? media.height : cssObject.height};
			}

			&:hover,
			&:active {
				cursor: pointer;

				animation: ${hasActiveAnimation ? `${scaleAnimation} 0.3 ease-in` : ''};
				animation-fill-mode: forwards;
			}
		`}
	>
		{children}
	</S.Container>
);
export default Card;
