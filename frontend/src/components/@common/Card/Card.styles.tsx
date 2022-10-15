import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const scaleAnimation = keyframes`
	100% {
		transform: scale(1.01);
	}
`;

export const Container = styled.section<{
	media: { minWidth: string; width?: string; height?: string } | '';
	hasActiveAnimation: boolean;
	isActive?: boolean;
}>`
	display: flex;
	flex-direction: column;
	transition: all 0.4s cubic-bezier(0.26, 0.71, 1, 0.46);

	${({ theme }) => css`
		// min-width: ${theme.size.SIZE_200};
		border-radius: ${theme.size.SIZE_010};
		box-shadow: 0 ${theme.size.SIZE_006} ${theme.size.SIZE_014} ${theme.boxShadows.secondary};
	`}

	${({ hasActiveAnimation }) =>
		hasActiveAnimation &&
		css`
			&:hover,
			&:active {
				animation: ${scaleAnimation} 0.3s ease-in;
				animation-fill-mode: forwards;
				cursor: pointer;
			}
		`}
	${({ media }) =>
		media !== '' &&
		css`
			@media (min-width: ${media.minWidth}) {
				width: ${media.width};
				height: ${media.height};
			}
		`}

		${({ isActive }) =>
		isActive === false &&
		css`
			transform: scale(0.85);
			pointer-events: none;
			filter: blur(2px);
		`}
`;
