import { PropsWithOptionalChildren } from 'gongseek-types';
import { useRef } from 'react';
import { useEffect } from 'react';

import * as S from '@/components/@common/Card/Card.styles';
import { CardProps } from '@/types/card';
import { css } from '@emotion/react';

const Card = ({
	cssObject,
	media,
	hasActiveAnimation,
	onClick,
	children,
	isActive,
	role,
}: PropsWithOptionalChildren<CardProps>) => {
	const carouselRef = useRef<HTMLElement | null>(null);

	useEffect(() => {
		if (carouselRef.current && isActive !== undefined) {
			carouselRef.current.inert = !isActive;
		}
	}, [carouselRef, isActive]);

	return (
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
				margin: ${cssObject.margin ? cssObject.margin : 0};
				flex-wrap: ${cssObject.flexWrap ? cssObject.flexWrap : 'nowrap'};
				flex-shrink: ${cssObject.flexShrink};
				scroll-snap-align: ${cssObject.scrollSnapAlign};
				scroll-snap-stop: ${cssObject.scrollSnapStop};
			`}
			media={media ? media : ''}
			hasActiveAnimation={hasActiveAnimation}
			isActive={isActive}
			onClick={onClick}
			tabIndex={0}
			role={role}
			ref={carouselRef}
		>
			{children}
		</S.Container>
	);
};

export default Card;
