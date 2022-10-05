import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const scaleAnimation = keyframes`
	100% {
		transform: scale(1.01);
	}
`;

export const Container = styled.section`
	display: flex;
	flex-direction: column;
	flex-wrap: wrap;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};
		box-shadow: 0 ${theme.size.SIZE_008} ${theme.size.SIZE_024} ${theme.boxShadows.secondary};
	`}

	&:hover,
	&:active {
		animation: ${scaleAnimation} 0.3s ease-in;
		animation-fill-mode: forwards;
		cursor: pointer;
	}
`;
